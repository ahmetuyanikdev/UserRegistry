/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.usermanagement.imp.ConcreteCommand;
import com.usermanagement.imp.Controller;
import com.usermanagement.imp.User;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Ahmet Uyanik
 */
public class Test {
    
    Controller controller; 
    ConcreteCommand concrete;
    public Test() {
         
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
       
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        
        controller = new Controller();
        concrete = new ConcreteCommand();
    }
    
    @After
    public void tearDown() {
    }
    
    @org.junit.Test
    public void insert_Test(){
        
        User user2 = new User();
        user2.setName("Specific");
        user2.setSurname("Test");
        user2.setTelefone("323-332-4433");
        controller.setUser(user2);
        controller.setSelectedUser(user2);
        
        
        controller.getAllUsers();
        int s1 = controller.getUserList().size();
        System.out.print(controller.getUserList().size());
        controller.getUserList();
        controller.setSelectedUser(controller.getUserList().get(0));
        
        controller.getAllUsers();
        System.out.print(controller.getUserList().size());
        
        
        
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
   