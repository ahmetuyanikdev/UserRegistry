package com.usermanagement.test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.usermanagement.imp.Controller;
import com.usermanagement.model.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ahmet Uyanik
 * This is Test Class, contains Mock and Real instance of Command Classes.Some CRUD operations will be handled
 * by Mock instance not to affect DB .
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class , loader = AnnotationConfigContextLoader.class)
public class Test {

    @Autowired
    Controller controller; 

    @BeforeClass
    public static void setUpClass() throws Exception {
       
    }

    @AfterClass
    public static void tearDownClass() throws Exception {

    }
    
    @Before
    public void setUp() {
        User user = getNewUser();
        User user2 = getNewUser();
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        userList.add(user2);
        Mockito.when(controller.getConcreteCommand().mongoOperation.findAll(User.class)).thenReturn(userList);
        /*Mockito.doNothing().when(controller.getConcreteCommand().mongoOperation).insert(user);*/
    }
    
    @After
    public void tearDown() {

    }

    private User getNewUser(){
        User user = new User();
        user.setName("John");
        user.setSurname("Smith");
        user.setTelefone("237-383-3873");
        return user;
    }
      
    @org.junit.Test 
    public void insertTest(){
        User user = getNewUser();
        controller.setUser(user);
        controller.saveUser();
        Mockito.verify(controller.getConcreteCommand().mongoOperation,Mockito.times(1)).insert(user);
    }

    @org.junit.Test
    public void updateTest(){
        User selectedUser = getNewUser();
        controller.setSelectedUser(selectedUser);
        controller.updateUser();
        Mockito.verify(controller.getConcreteCommand().mongoOperation,Mockito.times(1)).save(selectedUser);
    }

    @org.junit.Test
    public void deleteTest(){
        User selectedUser = getNewUser();
        controller.setSelectedUser(selectedUser);
        controller.deleteUser();
        Mockito.verify(controller.getConcreteCommand().mongoOperation,Mockito.times(1)).remove(selectedUser);
    }

    @org.junit.Test
    public void getAllUSerTest(){
        controller.getAllUsers();
        assert controller.getUserList().size()==2;
        assert controller.getUserList().get(0).getName().equals("John");
    }



}
   