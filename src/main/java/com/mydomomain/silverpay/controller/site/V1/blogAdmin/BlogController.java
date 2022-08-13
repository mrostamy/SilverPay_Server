package com.mydomomain.silverpay.controller.site.V1.blogAdmin;

import com.mydomomain.silverpay.Routes.V1.Routes;
import com.mydomomain.silverpay.configuration.model_mapper.BlogMapper;
import com.mydomomain.silverpay.dto.site.panel.blog.BlogApproveSelect;
import com.mydomomain.silverpay.dto.site.panel.blog.BlogCreateUpdateDto;
import com.mydomomain.silverpay.dto.site.panel.blog.BlogReturnDto;
import com.mydomomain.silverpay.model.blog.Blog;
import com.mydomomain.silverpay.repository.main.IBlogRepository;
import com.mydomomain.silverpay.repository.main.IGateRepository;
import com.mydomomain.silverpay.repository.main.IWalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class BlogController {

    Logger logger = LoggerFactory.getLogger(BlogController.class);

    final IBlogRepository blogRepository;


    @Autowired
    IWalletRepository walletRepository;


    @Autowired
    IGateRepository gateRepository;


    BlogController(IBlogRepository blogRepository) {

        this.blogRepository = blogRepository;
    }


    @GetMapping(Routes.Blog.get_blogs)
    public ResponseEntity<?> getBlogs(@PathVariable String user_id,
                                      Principal principal,
                                      HttpServletRequest request) {

        if (request.isUserInRole("Admin") || request.isUserInRole("AdminBlog")) {

            List<Blog> blogsRepo = blogRepository.findAll()
                    .stream()
                    .sorted(Comparator.comparing(Blog::getModifiedAt))
                    .collect(Collectors.toList());

            List<BlogReturnDto> blogs = BlogMapper.instance.blogsToReturnDtoes(blogsRepo);

            return ResponseEntity.ok(blogs);

        } else {
            List<Blog> blogsRepo = blogRepository.findAll()
                    .stream()
                    .filter(b -> b.getUser().getId().equals(user_id))
                    .sorted(Comparator.comparing(Blog::getModifiedAt))
                    .collect(Collectors.toList());

            List<BlogReturnDto> blogs = BlogMapper.instance.blogsToReturnDtoes(blogsRepo);

            return ResponseEntity.ok(blogs);
        }

    }


    @GetMapping(Routes.Blog.get_blog)
    public ResponseEntity<?> getBlog(@PathVariable String user_id,
                                     @PathVariable String id,
                                     Principal principal,
                                     HttpServletRequest request) {

        if (request.isUserInRole("Admin") || request.isUserInRole("AdminBlog")) {

            Blog blogRepo = blogRepository.findAll().stream().filter(b -> b.getId().equals(id))
                    .sorted(Comparator.comparing(Blog::getModifiedAt))
                    .findFirst().orElse(null);

            if (blogRepo != null) {

                BlogReturnDto blog = BlogMapper.instance.blogToReturnDto(blogRepo);

                return ResponseEntity.ok(blog);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("بلاگی وجود ندارد.");
            }

        } else {
            Blog blogRepo = blogRepository.findAll().stream().filter(b -> b.getId().equals(id))
                    .sorted(Comparator.comparing(Blog::getModifiedAt))
                    .findFirst().orElse(null);


            if (blogRepo != null) {

                if (principal.getName().equals(user_id)) {
                    BlogReturnDto blog = BlogMapper.instance.blogToReturnDto(blogRepo);
                    return ResponseEntity.ok(blog);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unAuthorize access detected.");
                }


            } else {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("بلاگ وجود ندارد.");

            }

        }


    }


    @PostMapping(Routes.Blog.insert_blog)
    public ResponseEntity<?> addBlog(
            @RequestBody BlogCreateUpdateDto createDto
            , HttpServletRequest request,
            @PathVariable String user_id) throws URISyntaxException {


        List<Blog> blogsRepo = blogRepository.findAll().
                stream()
                .filter(e -> Objects.equals(e.getTitle().trim(), createDto.getTitle().trim()))
                .collect(Collectors.toList());


        if (blogsRepo != null)
            return new ResponseEntity<>("the blog title exist before", HttpStatus.BAD_REQUEST);


        Blog blog = new Blog();
        blog.getUser().setId(user_id);
        blog.setSelected(false);
        blog.setStatus(false);

        blog = BlogMapper.instance.createDtoToBlog(createDto);

        blogRepository.save(blog);


        return ResponseEntity.created(new URI("?id=" + user_id)).body(blog);

    }

    @PutMapping(Routes.Blog.update_blog)
    public ResponseEntity<?> changeUserBlog(
            @PathVariable String id,
            BlogCreateUpdateDto updateDto
            , HttpServletRequest request
            , Authentication principal
            , @PathVariable String user_id) throws URISyntaxException {


        if (request.isUserInRole("Admin") || request.isUserInRole("AdminBlog")) {

            List<Blog> blogsRepo = blogRepository.findAll().
                    stream()
                    .filter(b -> Objects.equals(b.getTitle().trim(), updateDto.getTitle().trim())
                            && !Objects.equals(b.getId(), id))
                    .collect(Collectors.toList());

            if (blogsRepo == null) {

                Blog blogRepo = blogRepository.findById(id).orElse(null);

                if (blogRepo != null) {

                    Blog blog = BlogMapper.instance.updateDtoToBlog(updateDto);
                    blog.setModifiedAt(LocalDateTime.now());
                    blogRepository.save(blog);
                    return ResponseEntity.noContent().build();

                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("بلاگی وجود ندارد.");
                }
            } else {
                return ResponseEntity.badRequest().body("this blog registered before");

            }


        } else {

            List<Blog> blogsRepo = blogRepository.findAll().
                    stream()
                    .filter(b -> Objects.equals(b.getTitle().trim(), updateDto.getTitle().trim())
                            && !Objects.equals(b.getId(), id))
                    .collect(Collectors.toList());

            if (blogsRepo == null) {

                Blog blogRepo = blogRepository.findById(id).orElse(null);

                if (blogRepo != null) {

                    if (principal.getName().equals(user_id)) {
                        Blog blog = BlogMapper.instance.updateDtoToBlog(updateDto);
                        blog.setModifiedAt(LocalDateTime.now());
                        blogRepository.save(blog);
                        return ResponseEntity.noContent().build();
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UnAuthorized Access detected");

                    }


                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("بلاگی وجود ندارد.");
                }
            } else {
                return ResponseEntity.badRequest().body("this blog registered before");

            }


        }

    }

    @DeleteMapping(Routes.Blog.delete_blog)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> deleteBlog(
            @PathVariable String id
            , HttpServletRequest request
            , Authentication principal) throws URISyntaxException {


        Blog blog = blogRepository.findById(id).orElse(null);

        if (blog != null) {
            blogRepository.delete(blog);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("no blog found", HttpStatus.NOT_FOUND);

        }
    }

    @PutMapping(Routes.Blog.approve_blog)
    public ResponseEntity<?> approvBlog(
            @PathVariable String id,
            @PathVariable String user_id,
            BlogApproveSelect flag
            , HttpServletRequest request
            , Authentication principa
    ) throws URISyntaxException {


        Blog blog = blogRepository.findById(id).orElse(null);

        if (blog != null) {
            blog.setStatus(flag.isFlag());
            blogRepository.save(blog);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("no blog found", HttpStatus.NOT_FOUND);

        }
    }

    @PutMapping(Routes.Blog.select_blog)
    public ResponseEntity<?> selectBlog(
            @PathVariable String id
            , HttpServletRequest request
            , Authentication principal,
            @PathVariable String user_id,
            BlogApproveSelect flag

    )

            throws URISyntaxException {


        Blog blog = blogRepository.findById(id).orElse(null);

        if (blog != null) {
            blog.setSelected(flag.isFlag());
            blogRepository.save(blog);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("no blog found", HttpStatus.NOT_FOUND);

        }
    }
}
