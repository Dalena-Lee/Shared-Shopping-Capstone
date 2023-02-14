package com.techelevator.dao;

import com.techelevator.controller.AuthenticationController;
import com.techelevator.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;



public class ControlllerTests {
    private AuthenticationController authenticationController;

    @Autowired
    JdbcUserDao jdbcUserDao;

    @Test
    public void find_ID_by_username_works_correctly(){

        String username = "user";

        int expectedResult = 1;
        int actualResult = jdbcUserDao.findIDByUsername(username);

        //Assert.assertEquals(expectedResult, actualResult);
        System.out.println(actualResult);
    }


}
