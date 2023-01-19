package org.example;

import org.example.config.RestTemplateConfig;
import org.example.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Application {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RestTemplateConfig.class);

        Communication communication = context.getBean("communication", Communication.class);

        List<User> allUsers = communication.getAllUsers();
        System.out.println(allUsers);

        User user = new User("James", "Brown", (byte) 24);
        user.setId(3L);
        String wordsCode1 = communication.saveUser(user);

        user.setName("Thomas");
        user.setLastName("Shelby");
        String wordsCode2 = communication.updateUser(3L, user);

        String wordsCode3 = communication.deleteUser(3L);

        System.out.println(wordsCode1 + wordsCode2 + wordsCode3);
    }
}
