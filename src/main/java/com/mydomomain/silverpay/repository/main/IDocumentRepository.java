package com.mydomomain.silverpay.repository.main;

import com.mydomomain.silverpay.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDocumentRepository extends JpaRepository<Document, String> {

    int countByUser_Id(String usetId);

    Optional<Document> findByUser_Id(String user_id);
}
