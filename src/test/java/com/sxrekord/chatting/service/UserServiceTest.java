package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.po.User;
import com.sxrekord.chatting.util.JwtTokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

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
        HttpServletResponse response = new MockHttpServletResponse();

        System.out.println(userService.loginUser(new User("member001", "001"), response));
        System.out.println(userService.loginUser(new User("test1", "test1"), response));
        System.out.println(userService.loginUser(new User("test2", "test1"), response));
        System.out.println(userService.loginUser(new User("not_exists", "not_exists"), response));
    }

    @Test
    public void register() {
        System.out.println(userService.registerUser(new User("test1", "test1")));
        System.out.println(userService.registerUser(new User("test2", "test2")));
        System.out.println(userService.registerUser(new User("test3", "test3")));
        System.out.println(userService.registerUser(new User("test2", "test3")));
    }

    @Test
    public void logout() {
        String token = JwtTokenUtils.generateAccessToken(new User(1001L, "forLogoutName",
                "forLogoutPassword", "forLogoutAvatarPath"));
        System.out.println(userService.logoutUser(token));
    }

    @Test
    public void update() {
        register();
        System.out.println(userService.updateUser(new User("test_test", "test_test", "avatar/default_user_avatar.jpg"), 506L));
    }

    @Test
    public void search() {
        register();
        System.out.println(userService.searchUser("test", 506L));
        System.out.println(userService.searchUser("3", 506L));
        System.out.println(userService.searchUser("", 506L));
    }
}
