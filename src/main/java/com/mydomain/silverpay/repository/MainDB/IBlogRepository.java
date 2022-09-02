package com.mydomain.silverpay.repository.MainDB;

import com.mydomain.silverpay.data.model.mainDB.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IBlogRepository extends JpaRepository<Blog, String> {

    int countByStatus(boolean status);

    int countByModifiedAt(LocalDateTime dateTime);

    int countByStatusAndModifiedAt(boolean status, LocalDateTime dateTime);

    List<Blog> findTop7();

}
