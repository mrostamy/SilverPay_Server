package com.mydomomain.silverpay.controller.site.panel;

import com.mydomomain.silverpay.Routes.Routes;
import com.mydomomain.silverpay.configuration.cloudinary.CloudinaryConfigs;
import com.mydomomain.silverpay.dto.site.panel.photo.PhotoProfileDto;
import com.mydomomain.silverpay.model.Photo;
import com.mydomomain.silverpay.repository.main.IPhotoRepository;
import com.mydomomain.silverpay.repository.main.ISettingRepository;
import com.mydomomain.silverpay.service.userService.upload.UploadService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

@RestController
@RequestMapping(Routes.Photo.url)
public class PhotoController {

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

    @PostMapping("/upload")
    public ResponseEntity<?> changeUserPhoto(
            @PathVariable String userId
            , @ModelAttribute PhotoProfileDto photoUserProfile
            , HttpServletRequest request
            , Principal principal) throws URISyntaxException {

//        if (!principal.getName().equals(userId)) {
//
//            return new ResponseEntity<>("UnAuthorized Access Detected", HttpStatus.UNAUTHORIZED);
//        }
//
//        var file = photoUserProfile.getFile();
//
//        var webRoot = String.format("%s://%s:%s/", request.getScheme(), request.getServerName(), request.getServerPort());
//        var uploadResult = uploadService.profilePicUpload(file, userId, webRoot);
//
//        if (uploadResult.isStatus()) {
//
//            if (uploadResult.isLocalUpLoaded())
//                photoUserProfile.setPublicId("1");
//            else
//                photoUserProfile.setPublicId(uploadResult.getPublicId());
//
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
        //delete from local and upload to cloudinary

//        var oldPhoto = photoRepository.findById(userId);

//        var oldPhoto = photoRepository.findByUser_IdAndIsMain(userId, true).orElse(null);
//
//        if (oldPhoto != null && !Objects.equals(oldPhoto.getPublicId(), "0") &&
//                !Objects.equals(oldPhoto.getPublicId(), "1")) {
//
//            uploadService.removeFromCloudinary(oldPhoto.getPublicId());
//        }
//
//
//        if (oldPhoto.getPublicId().equals("1")) {
//
//            uploadService.removeFileFromLocal(oldPhoto.getPhotoUrl(), webRoot, webRoot);
//        }


        var res = uploadService.uploadProfilePicToCloudinary(photoUserProfile.getFile(), userId);

        return ResponseEntity
                .created(new URI("/get"))
                .body(res);
    }
}