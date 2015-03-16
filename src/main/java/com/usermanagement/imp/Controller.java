/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagement.imp;
 
import com.mongodb.MongoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
/**
 *
 * @author Hp
 */
@ManagedBean
@ViewScoped 
public class Controller implements Serializable{
    
    private User user; // new user to insert
    List<User> userList;
    
    private User selectedUser;
    ConcreteCommand cmd;
    public Boolean openForUpdate;
    public Controller(){
        try{
            user=new User();
            openForUpdate=false;
            selectedUser=new User();
            userList=new ArrayList<User>();
            cmd=new ConcreteCommand();
            getAllUsers();
        }  
        catch(Exception e){
            addMessage(FacesMessage.SEVERITY_ERROR,"Error!",e.getMessage());
        }
    }
    
    public void openForEditing(){
        if(openForUpdate){
            openForUpdate=false;
        }
        else{
            openForUpdate=true; 
        }
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Boolean getOpenForUpdate() {
        return openForUpdate;
    }

    public void setOpenForUpdate(Boolean openForUpdate) {
        this.openForUpdate = openForUpdate;
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
        fc.addMessage(null,new FacesMessage(se,type,msg));
    }
    
    public void saveUser(){
        try{
            if(user.dataValidation()){    
                cmd.insert(user);
                getAllUsers();
                user.setId(null);
                user.setName(null);
                user.setSurname(null);
                user.setTelefone(null);
                addMessage(FacesMessage.SEVERITY_INFO,"Info" ,"New User Added");
            }
            else{
                addMessage(FacesMessage.SEVERITY_ERROR,"Error!","Name and Surname are required.");
            }
        }
        catch(MongoException e){
            addMessage(FacesMessage.SEVERITY_ERROR,"Error!" ,e.getMessage());
        }
    }
    public void deleteUser(){
        try{
            cmd.delete(selectedUser);
            getAllUsers();
            addMessage(FacesMessage.SEVERITY_INFO,"Info" ,"User Deleted");
        }
        catch(MongoException e){
            addMessage(FacesMessage.SEVERITY_ERROR,"Error!" ,e.getMessage());
        }
    }
    public void updateUser(){
        try{
            if(selectedUser.dataValidation()){
                cmd.update(selectedUser);
                getAllUsers();
                addMessage(FacesMessage.SEVERITY_INFO,"Info" ,"User Updated");
            }
            else{
                addMessage(FacesMessage.SEVERITY_ERROR,"Error!","Name and Surname are required");
            }  
        }
        catch(MongoException e){
             addMessage(FacesMessage.SEVERITY_ERROR,"Error!" ,e.getMessage());
        }
    }
    
    public void getAllUsers(){
        userList.clear();
        userList=cmd.get(User.class);
    }
   
}
