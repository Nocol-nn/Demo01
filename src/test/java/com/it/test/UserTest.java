package com.it.test;

import cn.smbms.pojo.User;
import cn.smbms.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-21 9:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dao.xml", "classpath:applicationContext-service.xml",
        "classpath:spring-mvc.xml"})
public class UserTest {

    @Autowired
    private IUserService userService;

    @Test
    public void login() {
        User user = new User();
        user.setUserCode("admin");
        user.setUserPassword("1234567");
        User dbUser = userService.finUserByUsernameAndPassword(user);
        System.out.println(dbUser);
    }

    @Test
    public void getUserById() {

        User dbUser = userService.getUserById(1);
        System.out.println(dbUser);
    }

    @Test
    public void exist() {

        boolean admin = userService.exist("admin12");
        System.out.println(admin);
    }

    @Test
    public void findAll() {

        List<User> query = userService.search(null,null,null);
        for (User user : query) {
            System.out.println(user);
        }
    }

}
