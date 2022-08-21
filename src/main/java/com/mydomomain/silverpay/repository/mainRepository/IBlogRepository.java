package com.mydomomain.silverpay.repository.mainRepository;

import com.mydomomain.silverpay.model.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogRepository extends JpaRepository<Blog,String> {
}
