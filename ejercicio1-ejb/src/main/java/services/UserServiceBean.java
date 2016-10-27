/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.GroupDAOBeanLocal;
import dao.UserDAOBeanLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import models.Group;
import models.User;

/**
 *
 * @author sergio
 */
@Stateless
public class UserServiceBean implements UserServiceBeanLocal {
    @EJB
    private UserDAOBeanLocal userDAOBean;
    @EJB
    private GroupDAOBeanLocal groupDAOBean;
    
    @Override
    public List<User> getAll() {
        return userDAOBean.all();
    }

    @Override
    public void persist(final User user) {
        userDAOBean.persist(user);
    }

    @Override
    public void remove(final User user) {
        userDAOBean.remove(user);
    }

    @Override
    public User find(final String username) {
        return userDAOBean.find(username);
    }

    @Override
    public Boolean exists(final String username) {
        return userDAOBean.exists(username);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupDAOBean.all();
    }

    @Override
    public Group getGroupById(final Long id) {
        return groupDAOBean.byId(id);
    }
}
