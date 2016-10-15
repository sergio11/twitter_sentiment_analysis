/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.ejb.Local;
import models.Topic;

/**
 *
 * @author sergio
 */
@Local
public interface TopicDAOBeanLocal {

    void persist(final Topic topic);
    
}
