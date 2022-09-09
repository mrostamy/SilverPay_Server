package com.mydomain.silverpay.controller.app.V1.panel.admin;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.configuration.model_mapper.DocumentMapper;
import com.mydomain.silverpay.data.dto.app.panel.document.DocumentCreateDto;
import com.mydomain.silverpay.data.dto.app.panel.document.DocumentReturnDto;
import com.mydomain.silverpay.data.dto.app.panel.document.DocumentUpdateDto;
import com.mydomain.silverpay.data.dto.app.panel.upload.FileUploadedDto;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.BankCardUpdateDto;
import com.mydomain.silverpay.data.model.ReturnMessage;
import com.mydomain.silverpay.data.model.mainDB.Document;
import com.mydomain.silverpay.repository.MainDB.IDocumentRepository;
import com.mydomain.silverpay.service.userService.upload.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.noContent;

@RestController("admin_documentController")
public class DocumentController {

    Logger logger = LoggerFactory.getLogger(com.mydomain.silverpay.controller.app.V1.panel.user.DocumentController.class);

    final IDocumentRepository documentRepository;

    final UploadService uploadService;


    DocumentController(IDocumentRepository documentRepository, UploadService uploadService) {

        this.documentRepository = documentRepository;
        this.uploadService = uploadService;
    }


    @PostMapping(Routes.Document.insert_document + "/admin")
    public ResponseEntity<?> insertDocument(@PathVariable String user_id,
                                            DocumentCreateDto createDto,
                                            HttpServletRequest request) throws URISyntaxException, FileNotFoundException {


        Document approved = documentRepository.findAll().stream()
                .filter(d -> d.getApprove() == 1 && Objects.equals(d.getUser().getId(), user_id))
                .findFirst().orElse(null);

        if (approved == null) {

            Document inChecking = documentRepository.findAll().stream()
                    .filter(d -> d.getApprove() == 0).findFirst().orElse(null);


            if (inChecking == null) {

                FileUploadedDto uploadedDto = uploadService
                        .uploadProfilePicToLocal(createDto.getFile(), "", user_id, request.getContextPath());

                if (uploadedDto.isStatus()) {


                    Document document = new Document();
                    document.setApprove((short) 0);
                    document.getUser().setId(user_id);
                    document.setPhotoUrl(uploadedDto.getUrl());
                    document = DocumentMapper.instance.dtoToDocument(createDto);

                    Document doc = documentRepository.save(document);


                    if (doc == null) {

                        return new ResponseEntity<>("error in register document", HttpStatus.BAD_REQUEST);

                    } else {

                        DocumentReturnDto returnDto = DocumentMapper.instance.docToReturnDto(document);
                        return ResponseEntity.created(new URI("id=" + user_id)).body(returnDto);

                    }

                } else {
                    return new ResponseEntity<>("error in document photo upload", HttpStatus.BAD_REQUEST);
                }

            } else {
                return new ResponseEntity<>("document is in check status", HttpStatus.BAD_REQUEST);
            }

        } else {

            return new ResponseEntity<>("document registered before", HttpStatus.BAD_REQUEST);

        }
    }


    @GetMapping(Routes.Document.get_documents + "/admin")
    public ResponseEntity<?> getAdminDocuments(
            @RequestBody BankCardUpdateDto updateDto
            , HttpServletRequest request,
            Principal principal,
            @PathVariable String user_id) {

        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        }
        List<Document> documents = documentRepository.findAll();


        return new ResponseEntity<>(documents, HttpStatus.OK);

    }


    @GetMapping(Routes.Document.get_document + "/admin")
    public ResponseEntity<?> getDocument(
            HttpServletRequest request,
            Principal principal,
            @PathVariable String user_id,
            @PathVariable String document_id) {


        Document documentsRepo = documentRepository.findById(document_id).orElse(null);

        if (documentsRepo != null)

//            DocumentReturnDto returnDto=DocumentMapper.instance.docToReturnDto(documentsRepo);

            return new ResponseEntity<>(documentsRepo, HttpStatus.OK);

        return new ResponseEntity<>("document not found", HttpStatus.NOT_FOUND);

    }

    @PutMapping(Routes.Document.update_document + "/admin")
    public ResponseEntity<?> updateDocument(
            HttpServletRequest request,
            DocumentUpdateDto updateDto,
            @PathVariable String document_id) {


        Document documentRepo = documentRepository.findById(document_id).orElse(null);

        if (documentRepo != null){

            documentRepo.setApprove(updateDto.getApprove());
            documentRepo.setMessage(updateDto.getMessage());

            documentRepository.save(documentRepo);

            return noContent().build();

        }

        return new ResponseEntity<>("document not found", HttpStatus.NOT_FOUND);

    }


}
