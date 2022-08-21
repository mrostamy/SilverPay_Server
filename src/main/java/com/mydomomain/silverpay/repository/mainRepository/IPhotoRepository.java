package com.mydomomain.silverpay.repository.mainRepository;

import com.mydomomain.silverpay.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPhotoRepository extends JpaRepository<Photo, String> {


    Optional<Photo> findByPublicId(String publicId);

    Optional<Photo> findByUser_IdAndIsMain(String userId,boolean isMain);


}
