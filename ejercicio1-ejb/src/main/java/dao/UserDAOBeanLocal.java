/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Local;
import models.User;

/**
 *
 * @author sergio
 */
@Local
public interface UserDAOBeanLocal {

    void persist(final User user);
    List<User> all();
    void remove(final User user);
    User find(final String username);
    
}
