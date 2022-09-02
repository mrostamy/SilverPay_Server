package com.mydomain.silverpay.service.userService.seedService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mydomain.silverpay.data.model.mainDB.Role;
import com.mydomain.silverpay.data.model.mainDB.User;
import com.mydomain.silverpay.repository.MainDB.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeedService {

    final IUserRepository userRepository;

    public SeedService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void seedUsers() throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        List<User> users =
                objectMapper.readValue(ResourceUtils.getFile("classpath:seed/userSeedData.json")
                        , objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

        List<Role> userRoles = new ArrayList<>();

        userRoles.add(new Role("ROLE_User"));
//        roles.add(new Role("Admin"));
//        roles.add(new Role("Blog"));
//        roles.add(new Role("Accountant"));

//        for (User user : users) {
//            user.setPassword(new BCryptPasswordEncoder().encode("1234"));
//            user.setLastActive(Date.from(Instant.now()).toString());
//            user.addRole(roles.get(0));
//            System.out.println("****************");
//        }
        for (var user : users) {
            user.setPassword(new BCryptPasswordEncoder().encode("1234"));
            user.setLastActive(Date.from(Instant.now()).toString());
            user.setRoles(userRoles);
            userRepository.save(user);
            System.out.println("****************");
        }

//        userRepository.saveAll(users);

        User userBlogger = new User();
        userBlogger.setPassword(new BCryptPasswordEncoder().encode("1234"));
        userBlogger.setUsername("user_blogger");
        userBlogger.setPhoneNumber("");
        userBlogger.setAddress("");
        userBlogger.setName("");
        userBlogger.setRoles(userRoles);
        userBlogger.setLastActive("");
        userRepository.save(userBlogger);


        User userAdmin = new User();
        userAdmin.setPassword(new BCryptPasswordEncoder().encode("1234"));
        userAdmin.setUsername("user_admin");
        userAdmin.addRole(new Role("ROLE_Admin"));
        userAdmin.setPhoneNumber("");
        userAdmin.setAddress("");
        userAdmin.setName("");
        userAdmin.setLastActive("");
        userBlogger.setRoles(userRoles);
        userRepository.save(userAdmin);


        User userUser = new User();
        userUser.setPassword(new BCryptPasswordEncoder().encode("1234"));
        userUser.setUsername("user_user");
        userUser.addRole(new Role("ROLE_User"));
        userUser.setPhoneNumber("");
        userUser.setAddress("");
        userUser.setName("");
        userUser.setLastActive("");
        userRepository.save(userUser);


        User userAccountant = new User();
        userAccountant.setPassword(new BCryptPasswordEncoder().encode("1234"));
        userAccountant.setUsername("user_accountant");
        userAccountant.addRole(new Role("ROLE_Accountant"));
        userAccountant.setPhoneNumber("");
        userAccountant.setAddress("");
        userAccountant.setName("");
        userAccountant.setLastActive("");
        userRepository.save(userAccountant);


    }


}
