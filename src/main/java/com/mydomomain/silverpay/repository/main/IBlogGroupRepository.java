package com.mydomomain.silverpay.repository.main;

import com.mydomomain.silverpay.model.blog.BlogGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogGroupRepository extends JpaRepository<BlogGroup,String> {
}
