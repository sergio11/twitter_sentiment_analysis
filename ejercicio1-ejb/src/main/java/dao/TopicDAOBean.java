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
import models.Topic;

/**
 *
 * @author sergio
 */
@Stateless
public class TopicDAOBean implements TopicDAOBeanLocal {
    
    @PersistenceContext(unitName = "sentiment_PU")
    private EntityManager em;
    
    @Override
    public void persist(final Topic topic) {
        try {
            em.persist(topic);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Topic> all() {
        try {
            return em.createNamedQuery("Topic.all").getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Topic> byUser(final String username) {
        try {
            return em.createNamedQuery("TopicsByUser").setParameter("userName", username).getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(final Topic topic) {
        try {
            em.remove(em.merge(topic));
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer count() {
        try {
            return ((Number)em.createNamedQuery("Topic.count").getSingleResult()).intValue();
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Topic> recent(final Integer count) {
        try {
            return em.createNamedQuery("Topic.recent").getResultList();
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    
}
