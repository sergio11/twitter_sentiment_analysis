/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import models.User;

/**
 *
 * @author sergio
 */
@Stateless
public class UserDAOBean implements UserDAOBeanLocal {
    @PersistenceContext(unitName = "sentiment_PU")
    private EntityManager em;
   
    @Override
    public void persist(final User user) {
        try{
            em.merge(user);
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> all() {
        try{
            return em.createNamedQuery("User.all").getResultList();
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void remove(final User user) {
        try {
            em.remove(em.merge(user));
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public User find(final String username) {
        try {
            return em.createNamedQuery("User.find", User.class).setParameter("username", username).getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
}
