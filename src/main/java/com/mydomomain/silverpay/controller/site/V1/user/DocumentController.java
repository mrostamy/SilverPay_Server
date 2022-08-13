package com.mydomomain.silverpay.controller.site.V1.user;


import com.mydomomain.silverpay.Routes.V1.Routes;
import com.mydomomain.silverpay.configuration.model_mapper.DocumentMapper;
import com.mydomomain.silverpay.dto.site.panel.document.DocumentCreateDto;
import com.mydomomain.silverpay.dto.site.panel.document.DocumentReturnDto;
import com.mydomomain.silverpay.dto.site.panel.upload.FileUploadedDto;
import com.mydomomain.silverpay.dto.site.panel.users.BankCardUpdateDto;
import com.mydomomain.silverpay.model.Document;
import com.mydomomain.silverpay.model.ReturnMessage;
import com.mydomomain.silverpay.repository.main.IDocumentRepository;
import com.mydomomain.silverpay.service.userService.upload.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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

@RestController
public class DocumentController {

    Logger logger = LoggerFactory.getLogger(DocumentController.class);

    final IDocumentRepository documentRepository;

    final UploadService uploadService;


    DocumentController(IDocumentRepository documentRepository, UploadService uploadService) {

        this.documentRepository = documentRepository;
        this.uploadService = uploadService;
    }


    @PostMapping(Routes.Document.insert_document)
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
                        .uploadProfilePicToLocal(createDto.getFile(),"", user_id, request.getContextPath());

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


    @GetMapping(Routes.Document.get_documents)
    public ResponseEntity<?> getAllDocuments(
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


    @GetMapping(Routes.Document.get_document)
    public ResponseEntity<?> getDocument(
            HttpServletRequest request,
            Principal principal,
            @PathVariable String user_id,
            @PathVariable String id) {

        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        }


        Document documents = documentRepository.findByUser_Id(user_id).orElse(null);

        if (documents != null)
            return new ResponseEntity<>(documents, HttpStatus.OK);

        return new ResponseEntity<>("document not found", HttpStatus.NOT_FOUND);

    }

}
