/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagement.intfc;
import java.util.List;
 
/**
 *
 * @author Ahmet Uyanik.
 * Command class declares the operations 
 */
public interface Command {
    public abstract void delete(Object obj);
    public abstract void insert(Object obj);
    public abstract void update(Object obj);
    public abstract List get(Class cls);
}
