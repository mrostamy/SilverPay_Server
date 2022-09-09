package com.mydomain.silverpay.controller.app.V1.panel.user;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.configuration.cloudinary.CloudinaryConfigs;
import com.mydomain.silverpay.data.dto.app.panel.photo.PhotoProfileDto;
import com.mydomain.silverpay.data.model.mainDB.Photo;
import com.mydomain.silverpay.data.model.ReturnMessage;
import com.mydomain.silverpay.repository.MainDB.IPhotoRepository;
import com.mydomain.silverpay.repository.MainDB.ISettingRepository;
import com.mydomain.silverpay.service.userService.upload.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Objects;

@RestController
public class PhotoController {

    Logger logger = LoggerFactory.getLogger(PhotoController.class);

    final CloudinaryConfigs cloudinaryConfigs;
    final IPhotoRepository photoRepository;
    final UploadService uploadService;
    final ISettingRepository settingRepository;

    PhotoController(CloudinaryConfigs cloudinaryConfigs, IPhotoRepository photoRepository, UploadService uploadService, ISettingRepository settingRepository) {

        this.cloudinaryConfigs = cloudinaryConfigs;
        this.photoRepository = photoRepository;
        this.uploadService = uploadService;
        this.settingRepository = settingRepository;
    }

    @PostMapping(Routes.Photo.upload)
    public ResponseEntity<?> changeUserPhoto(
            @PathVariable String user_id
            , @ModelAttribute PhotoProfileDto photoUserProfile
            , HttpServletRequest request
            , Authentication principal) throws URISyntaxException, FileNotFoundException {


        if (!principal.getName().equals(user_id) || !request.isUserInRole("Admin")) {

            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorized Access Detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);

        } else {

            var file = photoUserProfile.getFile();

            var webRoot = String.format("%s://%s:%s/", request.getScheme(), request.getServerName(), request.getServerPort());
            var uploadResult = uploadService.profilePicUpload(file, user_id, webRoot);

            if (uploadResult.isStatus()) {

                if (uploadResult.isLocalUpLoaded())
                    photoUserProfile.setPublicId("1");
                else
                    photoUserProfile.setPublicId(uploadResult.getPublicId());

            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
//            delete from local and upload to cloudinary

//            var oldPhoto = photoRepository.findById(user_id);

            var oldPhoto = photoRepository.findByUser_IdAndIsMain(user_id, true).orElse(null);

            if (oldPhoto != null && !Objects.equals(oldPhoto.getPublicId(), "0") &&
                    !Objects.equals(oldPhoto.getPublicId(), "1")) {

                uploadService.removeFromCloudinary(oldPhoto.getPublicId());
            }


            if (oldPhoto.getPublicId().equals("1")) {

                uploadService.removeFileFromLocal(oldPhoto.getPhotoUrl(), webRoot, webRoot);
            }


            var res = uploadService.uploadProfilePicToCloudinary(photoUserProfile.getFile(), user_id);

            return ResponseEntity
                    .created(new URI("/get"))
                    .body(res);
        }
    }

    @GetMapping(Routes.Photo.photo)
    public ResponseEntity<?> getPhoto(@PathVariable String user_id,
                                      @PathVariable String id,
                                      Principal principal,
                                      HttpServletRequest request) {

        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        } else {


            Photo photo = photoRepository.findById(id).orElse(null);

            return new ResponseEntity<>(photo, HttpStatus.OK);

        }


    }
}