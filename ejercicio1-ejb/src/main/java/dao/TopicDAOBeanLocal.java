/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Local;
import models.Topic;

/**
 *
 * @author sergio
 */
@Local
public interface TopicDAOBeanLocal {

    void persist(final Topic topic);

    List<Topic> all();

    List<Topic> byUser(final String username);

    void remove(final Topic topic);

    Integer count();

    List<Topic> recent(final Integer count);

    Boolean exists(final String topic);
    
}
