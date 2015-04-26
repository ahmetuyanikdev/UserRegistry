/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagement.imp;
   
import com.usermanagement.model.User;
import com.mongodb.MongoException;
import java.io.Serializable;
import java.util.ArrayList; 
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


/**
 *
 * @author Ahmet Uyanik 
 * This is the Controller Class to bind methods with the page
 */
@ManagedBean
@ViewScoped 
public class Controller implements Serializable{
    
    private User user; // new user to insert
    List<User> userList;
    private User selectedUser;
    private ConcreteCommand cmd;
    ApplicationContext context;
    String webmasterEmail;
    public static Logger log;
    public Controller(){
            
            context = new ClassPathXmlApplicationContext("classpath:/spring/springbeans.xml");
            user = (User)context.getBean("UserBean");
            selectedUser = (User)context.getBean("UserBean");
            cmd=(ConcreteCommand)context.getBean("ConcreteCommandBean");
            
            userList=new ArrayList<User>();
            getAllUsers();
           
            
            DOMConfigurator.configure("src/main/java/log/log4j.xml");
     
            log=Logger.getLogger(Controller.class);
            log.debug("-----initialization----");
            log.info("Controller.java/---- Application is started at "+new Date());
       
    }
    

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public ConcreteCommand getCmd() {
        return cmd;
    }

    public void setCmd(ConcreteCommand cmd) {
        this.cmd = cmd;
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
                log.debug("---- attempt insert new user");
                cmd.insert(user);
                log.info("Controller.java/---- new user "+user.getName()+" "+user.getSurname()+" saved at "+new Date());
                getAllUsers();
                addMessage(FacesMessage.SEVERITY_INFO,"Info" ,"New User Added");
            }
            else{
                addMessage(FacesMessage.SEVERITY_ERROR,"Error!","Name and Surname are required.");
            }
        }
        catch(MongoException e){
            log.info("Controller.java/ Save failed for user "+user.getName()+" at :"+new Date());
            log.error("Controller.java/---Exception occoured during save a user : "+e.getMessage().toString());   
            addMessage(FacesMessage.SEVERITY_ERROR,"Error!" ,e.getMessage());
        }
    }
    public void deleteUser(){
        try{
            log.debug("---- attempt to delete user");
            cmd.delete(selectedUser);
            log.info("Controller.java/---- user "+selectedUser.getName()+" "+selectedUser.getSurname()+" deleted at "+new Date());
            getAllUsers();
            addMessage(FacesMessage.SEVERITY_INFO,"Info" ,"User Deleted");
        }
        catch(MongoException e){
            log.error("Controller.java/---Exception occoured during delete a user : "+e.getMessage().toString());   
            addMessage(FacesMessage.SEVERITY_ERROR,"Error!" ,e.getMessage());
        }
    }
    public void updateUser(){
        try{
            if(selectedUser.dataValidation()){
                log.debug("---- attempt to update user");
                cmd.update(selectedUser);
                log.info("Controller.java/---- user "+selectedUser.getName()+" "+selectedUser.getSurname()+" deleted at "+new Date());
                getAllUsers();
                addMessage(FacesMessage.SEVERITY_INFO,"Info" ,"User Updated");
            }
            else{
                addMessage(FacesMessage.SEVERITY_ERROR,"Error!","Name and Surname are required");
            }  
        }
        catch(MongoException e){
             log.error("Controller.java/---Exception occoured during update a user : "+e.getMessage().toString());   
             addMessage(FacesMessage.SEVERITY_ERROR,"Error!" ,e.getMessage());
        }
    }
    
    public void getAllUsers(){
        userList.clear();
        userList=cmd.get(User.class);
    }
   
}
