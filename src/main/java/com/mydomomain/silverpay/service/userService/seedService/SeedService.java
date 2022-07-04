package com.mydomomain.silverpay.service.userService.seedService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mydomomain.silverpay.model.BankCard;
import com.mydomomain.silverpay.model.Photo;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.repository.main.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
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

        for (User user : users) {
            user.setPassword(new BCryptPasswordEncoder().encode("1234"));
            user.setLastActive(Date.from(Instant.now()).toString());
            System.out.println("****************");
        }

        userRepository.saveAll(users);



    }


}
