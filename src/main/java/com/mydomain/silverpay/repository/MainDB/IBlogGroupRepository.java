package com.mydomain.silverpay.repository.MainDB;

import com.mydomain.silverpay.data.model.mainDB.blog.BlogGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogGroupRepository extends JpaRepository<BlogGroup,String> {
}
