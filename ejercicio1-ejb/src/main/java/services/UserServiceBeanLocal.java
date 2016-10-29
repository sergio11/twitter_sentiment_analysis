/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import javax.ejb.Local;
import models.Group;
import models.User;

/**
 *
 * @author sergio
 */
@Local
public interface UserServiceBeanLocal {

    List<User> getAll();
    void persist(final User user);
    void remove(final User user);
    User find(final String username);
    Boolean exists(final String username);
    List<Group> getAllGroups();
    Group getGroupByName(final String name);
    
}
