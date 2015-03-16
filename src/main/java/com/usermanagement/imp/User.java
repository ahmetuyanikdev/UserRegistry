/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagement.imp;

import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 *
 * @author Hp
 */
@Document
public class User implements Serializable{
    @Id            
    private String Id;
    
    private String Name;
    private String Surname;
    private String Telefone;
    public User(){
        this.Id=UUID.randomUUID().toString();
    }
    public String getId() {
        return Id;
    }
    public void setId(String Id) {
        this.Id = Id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String Telefone) {
        this.Telefone = Telefone;
    }
    
    public Boolean dataValidation(){
        
        if(this.getName()!=null&&this.getName()!="" &&this.getSurname()!=null&&this.getSurname()!="" && this.getId()!=null&&this.getId()!=""){
            return true; // object is ready to insert
        }
        else{
            return false;
        }
        
    }
    
    
    
    
    
}
