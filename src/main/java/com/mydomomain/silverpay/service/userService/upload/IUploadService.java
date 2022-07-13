package com.mydomomain.silverpay.service.userService.upload;

import com.mydomomain.silverpay.dto.site.panel.upload.FileUploadedDto;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {

    FileUploadedDto uploadProfilePicToCloudinary(MultipartFile file,String userId);

    FileUploadedDto uploadProfilePicToLocal(MultipartFile file, String webRootPath, String userId);

    FileUploadedDto profilePicUpload(MultipartFile file,String userId,String webPath);

    FileUploadedDto removeFromCloudinary(String publicId);

    FileUploadedDto removeFileFromLocal(String photoName, String webRootPath, String path);



}
