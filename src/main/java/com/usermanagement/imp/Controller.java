/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagement.imp;
   
import com.usermanagement.model.User;
import com.mongodb.MongoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 *
 * @author Ahmet Uyanik 
 * This is the Controller Class to bind methods with the page
 */
@org.springframework.stereotype.Controller
@EnableWebMvc
@ManagedBean
@ViewScoped
public class Controller implements Serializable{

    @Autowired
    private User user;

    @Autowired
    private User selectedUser; 

    @Autowired
    private ConcreteCommand concreteCommand;

    List<User> userList;

    public static Logger log;

    @PostConstruct
    private void initialize(){
        log=Logger.getLogger(Controller.class);
        log.info("----- Application starting ----");
        userList=new ArrayList<User>();
        getAllUsers();

    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public ConcreteCommand getConcreteCommand() {
        return concreteCommand;
    }

    public void setConcreteCommand(ConcreteCommand concreteCommand) {
        this.concreteCommand = concreteCommand;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    
    
    
    void addMessage(Severity se,String type,String msg){
       
        FacesContext fc = FacesContext.getCurrentInstance();
        if(fc!=null){
            fc.addMessage(null,new FacesMessage(se,type,msg));
        }
    }
    
    public void saveUser(){
        try{
            if(user.dataValidation()){
                log.info("#--- try to save new User "+user);
                concreteCommand.insert(user);
                getAllUsers();
                addMessage(FacesMessage.SEVERITY_INFO,"Info" ,"New User Added");
            }
            else{
                addMessage(FacesMessage.SEVERITY_ERROR,"Error!","Name and Surname are required.");
            }
        }
        catch(MongoException e){
            log.error("#!! Error occurred during save a user " + e.getMessage());
            addMessage(FacesMessage.SEVERITY_ERROR,"Error!" ,e.getMessage());
        }
    }
    public void deleteUser(){
        try{
            log.info("#--- try to delete User "+selectedUser);
            concreteCommand.delete(selectedUser);
            getAllUsers();
            addMessage(FacesMessage.SEVERITY_INFO,"Info" ,"User Deleted");
        }
        catch(MongoException e){
            log.error("#!! Error occurred during delete user  " + e.getMessage());
            addMessage(FacesMessage.SEVERITY_ERROR,"Error!" ,e.getMessage());
        }
    }
    public void updateUser(){
        try{
            if(selectedUser.dataValidation()){
                log.info("#--- try to update User "+selectedUser);
                concreteCommand.update(selectedUser);
                getAllUsers();
                addMessage(FacesMessage.SEVERITY_INFO,"Info" ,"User Updated");
            }
            else{
                addMessage(FacesMessage.SEVERITY_ERROR,"Error!","Name and Surname are required");
            }  
        }
        catch(MongoException e){
            log.error("#!! Error occurred during update user " + e.getMessage());
            addMessage(FacesMessage.SEVERITY_ERROR,"Error!" ,e.getMessage());
        }
    }
    
    public void getAllUsers(){
        userList.clear();
        userList=concreteCommand.get(User.class);
    }
   
}
