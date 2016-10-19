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
import models.Country;

/**
 *
 * @author sergio
 */
@Stateless
public class CountryDAOBean implements CountryDAOBeanLocal {
    @PersistenceContext(unitName = "sentiment_PU")
    private EntityManager em;
    
    @Override
    public List<Country> all() {
        try{
            return em.createNamedQuery("Country.getAll").getResultList();
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
}
