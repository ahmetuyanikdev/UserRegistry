package com.usermanagement.test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.usermanagement.imp.ConcreteCommand;
import com.usermanagement.imp.Controller;
import com.usermanagement.model.User;
import java.util.List;
import org.junit.*;
import org.mockito.Mockito;

/**
 *
 * @author Ahmet Uyanik
 * This is Test Class, contains Mock and Real instance of Command Classes.Some CRUD operations will be handled
 * by Mock instance not to affect DB .
 */

public class Test {
    
    Controller controller; 
    ConcreteCommand command;
    ConcreteCommand mockedCommand;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
       
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        
        controller  = new Controller();
        command = new ConcreteCommand();
        mockedCommand = Mockito.mock(ConcreteCommand.class);
    }
    
    public User initializeUser(){
        User u =  new User();
        u.setName("TempName");
        u.setSurname("TempSurname");
        u.setTelefone("44444444");
        return u;
    }
    
    public User getUserDetail(String id,List<User> userList){
        for(User u : userList){
            if(u.getId().toString().equals(id)){
               
                return u;
            }
        }
        return null;
    }
    
    @After
    public void tearDown() {
    }
      
    @org.junit.Test 
    public void insert_Test(){
        //Mockito.doNothing().when(controller).deleteUser();
                           /*Getting user instance for test purposes 
        
        */
        Controller.log.info("--------------------- Test Started--------------------");
        User u = initializeUser(); /*initializing new user instance for testing purposes*/
        
        controller.getAllUsers();    
        int existingUserNumber = controller.getUserList().size();
        controller.setCmd(command);
        controller.getAllUsers();  
        Assert.assertSame(existingUserNumber,controller.getUserList().size()); /*make sure new instance of Concrete command working*/   
       
        controller.setUser(u); /*setting user to be inserted database*/
        controller.setCmd(mockedCommand); /*Setting mock command*/
        controller.saveUser(); /*Save operation using mocked instance of command*/
        
        User u2 = initializeUser();
        controller.setCmd(command);
        controller.getAllUsers();
        Assert.assertSame(existingUserNumber,controller.getUserList().size()); /*Ensuring that mocked instance will 
                                                                         not be able to do crud operations.So,saveUser
                                                                         operation will no effect on database                                                                 */
        controller.setUser(u2);  
        controller.saveUser();      /* inserting new user (u2) object */
        u2.setTelefone("555-555-5555");
        controller.setSelectedUser(u2); /* setting user to update with new Telf. number Db*/
        controller.updateUser(); /*updating user with new telf. number 555-555-5555*/
        controller.getAllUsers();
       
        Assert.assertEquals(u2.getTelefone(),getUserDetail(u2.getId(),controller.getUserList()).getTelefone().toString());
        controller.deleteUser();    /*deleting user (u2)*/                                
        controller.getAllUsers(); /* Getting all users from Db*/
        
        Assert.assertSame(existingUserNumber,controller.getUserList().size()); /*Enduring that user(u2) has been deleted from Db */       
        Controller.log.info("--------------------- Test Ended--------------------");
    }

}
   