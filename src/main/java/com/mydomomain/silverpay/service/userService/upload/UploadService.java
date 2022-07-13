package com.mydomomain.silverpay.service.userService.upload;

import com.mydomomain.silverpay.dto.site.panel.upload.FileUploadedDto;
import com.mydomomain.silverpay.model.Photo;
import com.mydomomain.silverpay.repository.main.IPhotoRepository;
import com.mydomomain.silverpay.repository.main.ISettingRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

@Service
public class UploadService implements IUploadService {

    final IPhotoRepository photoRepository;
    final ISettingRepository settingRepository;

    public UploadService(IPhotoRepository photoRepository, ISettingRepository settingRepository) {
        this.photoRepository = photoRepository;
        this.settingRepository = settingRepository;
    }

    @Override
    public FileUploadedDto uploadProfilePicToCloudinary(MultipartFile file, String userId) {


        return null;
    }

    @Override
    public FileUploadedDto uploadProfilePicToLocal(MultipartFile file, String webRootPath, String userId) {

        try {

            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = userId + "." + extension;
            String path = ResourceUtils.getFile("classpath:static/content/pic/").getAbsolutePath();

            String fullName = Path.of(path, fileName).toString();

            if (file.getSize() > 0) {

                file.transferTo(new File(fullName));

                return new FileUploadedDto(
                        true,
                        true,
                        webRootPath + "content/pic/" + fileName,
                        "0",
                        "file uploaded successfully"
                );

            } else {
                return new FileUploadedDto(false, false, null, null, "file not found for upload");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public FileUploadedDto profilePicUpload(MultipartFile file, String userId, String webRootPath) {

        var settings = settingRepository.findById(1).orElse(null);

        if (settings == null || settings.isUploadLocal()) {
            return uploadProfilePicToLocal(file, webRootPath, userId);
        } else {
            return uploadProfilePicToCloudinary(file, userId);
        }
    }

    @Override
    public FileUploadedDto removeFromCloudinary(String publicId) {
        return null;
    }

    @Override
    public FileUploadedDto removeFileFromLocal(String photoName,String webRootPath,String path) {

//        photoRepository.findByPublicId(publicId).ifPresent(photoRepository::delete);


        return null;
    }
}
