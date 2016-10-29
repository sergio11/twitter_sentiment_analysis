/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import javax.ejb.Local;
import models.Topic;

/**
 *
 * @author sergio
 */
@Local
public interface TopicsServiceBeanLocal {

    List<Topic> getTopics();

    List<Topic> getTopicsByUser(final String username);

    void remove(final Topic topic);

    void analyzeTopic(final Topic topic);

    Integer getTopicsCount();
    
}
