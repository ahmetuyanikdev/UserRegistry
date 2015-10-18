/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagement.imp;

import com.usermanagement.intfc.Command;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 *  
 * @author Ahmet Uyanik
 * This method is defined for User operations like,insert,delete,update
 */


public class ConcreteCommand implements Command{
    public MongoOperations mongoOperation;

    @Override
    public void delete(Object obj) {
       mongoOperation.remove(obj);
    }

    
    @Override
    public void insert(Object obj) {
        mongoOperation.insert(obj);
    }
 
    
    @Override
    public void update(Object obj) {
        mongoOperation.save(obj);
    }

    
    @Override
    public List get(Class cls) {
        return mongoOperation.findAll(cls);
        
    }
    
}
