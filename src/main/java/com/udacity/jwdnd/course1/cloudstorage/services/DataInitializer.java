package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.User;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    public void initDatabase(){

        User user = new User();
        user.setFirstName("Joao");
        user.setLastName("Chagas");
        user.setPassword("teste");
        user.setUsername("teste");

        userService.createUser(user);
        System.out.println("Usuario criado com sucesso");
    }
}
