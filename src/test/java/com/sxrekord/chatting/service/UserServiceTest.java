package com.sxrekord.chatting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;

/**
 * @author Rekord
 * @date 2022/9/12 13:21
 */
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void login() {
        System.out.println(userService.loginUser("member001", "001", null));
        System.out.println(userService.loginUser("test1", "test1", null));
        System.out.println(userService.loginUser("test2", "test1", null));
        System.out.println(userService.loginUser("not_exists", "not_exists", null));
    }

    @Test
    public void register() {
        System.out.println(userService.registerUser("test1", "test1"));
        System.out.println(userService.registerUser("test2", "test2"));
        System.out.println(userService.registerUser("test3", "test3"));
        System.out.println(userService.registerUser("test2", "test3"));
    }

    @Test
    public void logout() {
        System.out.println(userService.logoutUser(null));
    }

    @Test
    public void update() {
        register();
        HttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("userId", 506L);
        System.out.println(userService.updateUser("test_test", "test_test", "avatar/default_user_avatar.jpg", httpSession));
    }

    @Test void search() {
        register();
        HttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("userId", 506L);
        System.out.println(userService.searchUser("test"));
        System.out.println(userService.searchUser("3"));
    }
}
