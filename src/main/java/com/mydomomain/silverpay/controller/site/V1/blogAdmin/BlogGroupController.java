package com.mydomomain.silverpay.controller.site.V1.blogAdmin;

import com.mydomomain.silverpay.Routes.V1.Routes;
import com.mydomomain.silverpay.configuration.model_mapper.BlogGroupMapper;
import com.mydomomain.silverpay.dto.site.panel.blogGroup.BlogGroupCreateUpdateDto;
import com.mydomomain.silverpay.dto.site.panel.blogGroup.BlogGroupReturnDto;
import com.mydomomain.silverpay.model.blog.BlogGroup;
import com.mydomomain.silverpay.repository.main.IBlogGroupRepository;
import com.mydomomain.silverpay.repository.main.IGateRepository;
import com.mydomomain.silverpay.repository.main.IWalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class BlogGroupController {

    Logger logger = LoggerFactory.getLogger(BlogGroupController.class);

    final IBlogGroupRepository blogGroupRepository;


    @Autowired
    IWalletRepository walletRepository;


    @Autowired
    IGateRepository gateRepository;


    BlogGroupController(IBlogGroupRepository blogGroupRepository) {

        this.blogGroupRepository = blogGroupRepository;
    }


    @GetMapping(Routes.BlogGroup.get_blogGroups)
    public ResponseEntity<?> getBlogGroups(@PathVariable String user_id,
                                           Principal principal,
                                           HttpServletRequest request) {


        List<BlogGroup> blogGroups = blogGroupRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(BlogGroup::getModifiedAt))
                .collect(Collectors.toList());


//        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
//            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
//            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
//        }


        List<BlogGroupReturnDto> returnBlogGroups = BlogGroupMapper.instance.BlogGroupMapper(blogGroups);


        return new ResponseEntity<>(returnBlogGroups, HttpStatus.OK);

    }


    @GetMapping(Routes.BlogGroup.get_blogGroup)
    public ResponseEntity<?> getBlogGroup(@PathVariable String user_id,
                                          @PathVariable String id,
                                          Principal principal,
                                          HttpServletRequest request) {

//        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
//            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
//            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
//        }
//        else {

        BlogGroup blogGroup = blogGroupRepository.findById(id).orElse(null);

        if (blogGroup != null) {
            BlogGroupReturnDto ReturnDto = BlogGroupMapper.instance.blogGroupToReturnDtoMapper(blogGroup);

            return new ResponseEntity<>(ReturnDto, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("blogGroup found", HttpStatus.NOT_FOUND);

        }
//        }


    }


    @PostMapping(Routes.BlogGroup.insert_blogGroup)
    public ResponseEntity<?> changeUserBlogGroup(
            @RequestBody BlogGroupCreateUpdateDto createDto
            , HttpServletRequest request,
            @PathVariable String user_id) throws URISyntaxException {


        List<BlogGroup> blogGroupsRepo = blogGroupRepository.findAll().
                stream().filter(e -> Objects.equals(e.getName(), createDto.getName()))
                .collect(Collectors.toList());

        if (blogGroupsRepo != null)
            return new ResponseEntity<>("the blogGroup registered before", HttpStatus.BAD_REQUEST);


        BlogGroup blogGroup = new BlogGroup();

        blogGroup = BlogGroupMapper.instance.blogGroupMapper(createDto);

        blogGroupRepository.save(blogGroup);


        return ResponseEntity.created(new URI("?id=" + user_id)).body(blogGroup);

    }

    @PutMapping(Routes.BlogGroup.update_blogGroup)
    public ResponseEntity<?> changeUserBlogGroup(
            @PathVariable String id,
            BlogGroupCreateUpdateDto updateDto
            , HttpServletRequest request
            , Authentication principal
            , @PathVariable String user_id) throws URISyntaxException {

        List<BlogGroup> blogGroupsRepo = blogGroupRepository.findAll().
                stream().
                filter(b -> Objects.equals(b.getName(), updateDto.getName()) && b.getId().equals(id)
                )
                .collect(Collectors.toList());


        if (blogGroupsRepo == null) {
            BlogGroup blogGroup = blogGroupRepository.findById(id).orElse(null);

            if (blogGroup != null) {

//                if (!principal.getName().equals(blogGroup.getUser().getId())) {
//
//                    ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorized Access Detected", "error");
//                    return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
//
//                }

                BlogGroup updateBlogGroup = BlogGroupMapper.instance.blogGroupMapper(updateDto);
                updateBlogGroup.setModifiedAt(LocalDateTime.now());


                blogGroupRepository.save(updateBlogGroup);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);


            }

        } else {

            return new ResponseEntity<>("no blogGroup found", HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<>("this blog group is registered before", HttpStatus.CONFLICT);
    }

    @DeleteMapping(Routes.BlogGroup.delete_blogGroup)
    public ResponseEntity<?> changeUserBlogGroup(
            @PathVariable String id
            , HttpServletRequest request
            , Authentication principal) throws URISyntaxException {


        BlogGroup blogGroup = blogGroupRepository.findById(id).orElse(null);

        if (blogGroup != null) {
//
//            if (!principal.getName().equals(blogGroup.getUser().getId())) {
//
//                ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorized Access Detected", "error");
//                return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
//
//            }
//            else {
                blogGroupRepository.delete(blogGroup);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

//            }

        } else
        {
            return new ResponseEntity<>("no blogGroup found", HttpStatus.NOT_FOUND);

        }
    }

}
