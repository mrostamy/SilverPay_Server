package com.mydomain.silverpay.repository.MainDB;

import com.mydomain.silverpay.data.model.mainDB.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDocumentRepository extends JpaRepository<Document, String> {

    int countByUser_Id(String usetId);

    Optional<Document> findByUser_Id(String user_id);

    int countByApprove(short approved);
}
