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
        communication.saveUser(user);

        System.out.println(allUsers);

    }
}
