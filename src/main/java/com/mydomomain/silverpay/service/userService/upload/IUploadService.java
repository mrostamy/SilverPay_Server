package com.mydomomain.silverpay.service.userService.upload;

import com.mydomomain.silverpay.dto.site.panel.upload.FileUploadedDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface IUploadService {

    FileUploadedDto uploadProfilePicToCloudinary(MultipartFile file,String userId);

    FileUploadedDto uploadProfilePicToLocal(MultipartFile file, String webRootPath, String userId,String url) throws FileNotFoundException;

    FileUploadedDto profilePicUpload(MultipartFile file,String userId,String webPath) throws FileNotFoundException;

    FileUploadedDto removeFromCloudinary(String publicId);

    FileUploadedDto removeFileFromLocal(String photoName, String webRootPath, String path);

    FileUploadedDto createDirectory(String webRootPath, String url);
}
