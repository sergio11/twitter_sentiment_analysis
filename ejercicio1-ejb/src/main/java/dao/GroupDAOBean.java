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
import models.Group;

/**
 *
 * @author sergio
 */
@Stateless
public class GroupDAOBean implements GroupDAOBeanLocal {
    @PersistenceContext(unitName = "sentiment_PU")
    private EntityManager em;
   
    @Override
    public List<Group> all() {
        try {
            return em.createNamedQuery("Group.all").getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Group byId(final Long id) {
        try {
            return em.find(Group.class, id);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    
}
