package com.mydomain.silverpay.service.userService.upload;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.mydomain.silverpay.data.dto.site.panel.upload.FileUploadedDto;
import com.mydomain.silverpay.data.model.mainDB.Photo;
import com.mydomain.silverpay.repository.MainDB.IPhotoRepository;
import com.mydomain.silverpay.repository.MainDB.ISettingRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadService implements IUploadService {

    final IPhotoRepository photoRepository;
    final ISettingRepository settingRepository;

    final Cloudinary cloudinary;

    public UploadService(IPhotoRepository photoRepository, ISettingRepository settingRepository, Cloudinary cloudinary) {
        this.photoRepository = photoRepository;
        this.settingRepository = settingRepository;
        this.cloudinary = cloudinary;
    }

    @Override
    public FileUploadedDto uploadProfilePicToCloudinary(MultipartFile file, String userId) {


        try {

            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

            String fullName = userId + "." + extension;

            Path path = Paths.get(ResourceUtils.getFile("classpath:static/content/pic").toString(), fullName);

            Files.deleteIfExists(path);
            Path f = Files.createFile(path);

            file.transferTo(f);

            var result = cloudinary.uploader().upload(path.toFile(), ObjectUtils.asMap(
                    "transformation", new Transformation<>().width(150).height(150).crop("fill").gravity("face")
            ));

            return new FileUploadedDto(
                    true,
                    false,
                    (String) result.get("url"),
                    (String) result.get("public_id"),
                    "file uploaded successfully"
            );

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public FileUploadedDto uploadProfilePicToLocal(MultipartFile file, String webRootPath, String userId, String url) throws FileNotFoundException {

        if (url == null || url.isEmpty() || url.isBlank()) {
            url = ResourceUtils.getFile("classpath:static/content/pic/").getAbsolutePath();
        }

        try {

            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = userId + "." + extension;

            String fullName = Path.of(url, fileName).toString();

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
    public FileUploadedDto profilePicUpload(MultipartFile file, String userId, String webRootPath) throws FileNotFoundException {

        var settings = settingRepository.findById(1).orElse(null);

        if (settings == null || settings.isUploadLocal()) {
            return uploadProfilePicToLocal(file, webRootPath, userId, "");
        } else {
            return uploadProfilePicToCloudinary(file, userId);
        }
    }

    @Override
    public FileUploadedDto removeFromCloudinary(String publicId) {

        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return new FileUploadedDto(true, false, "", publicId, "image removed successfully");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new FileUploadedDto(false, false, "", publicId, "error in remove image");
        }
    }

    @Override
    public FileUploadedDto removeFileFromLocal(String photoName, String webRootPath, String path) {


        try {
            Photo photo = photoRepository.findByPublicId(photoName).orElse(null);

            if (photo != null) {
                String photoUrl = photo.getPhotoUrl();
                Files.deleteIfExists(Path.of(ResourceUtils.getFile("classpath:static/content/pic").toString(), photoName));
                return new FileUploadedDto(true, false, "", photoName, "image removed successfully");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new FileUploadedDto(false, false, "", photoName, "error in remove image");
        }


        return null;
    }

    @Override
    public FileUploadedDto createDirectory(String webRootPath, String url) {

        var path = Paths.get(webRootPath, url);

        if (!Files.exists(path)) {
            try {

                Files.createDirectory(path);

                return new FileUploadedDto(true, true, "", "", "");

            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        }
        return new FileUploadedDto(false, false, "", "", "");


    }
}
