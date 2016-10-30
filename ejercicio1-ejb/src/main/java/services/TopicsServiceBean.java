/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.TopicDAOBeanLocal;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import models.Topic;

/**
 *
 * @author sergio
 */
@Stateless
public class TopicsServiceBean implements TopicsServiceBeanLocal {
    
    @Resource(mappedName = "jms/topicsQueue")
    private Queue topicsQueue;
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    
    @EJB
    private TopicDAOBeanLocal topicDAOBean;
    
    @Override
    public List<Topic> getTopics() {
        return topicDAOBean.all();
    }

    @Override
    public List<Topic> getTopicsByUser(final String username) {
        return topicDAOBean.byUser(username);
    }

    @Override
    public void remove(final Topic topic) {
        topicDAOBean.remove(topic);
    }

    @Override
    public void analyzeTopic(final Topic topic) {
        //save topic
        topicDAOBean.persist(topic);
        // enqueue message
        ObjectMessage message = 
                  context.createObjectMessage(topic);
        context.createProducer().send(topicsQueue, message);
    }

    @Override
    public Integer getTopicsCount() {
        return topicDAOBean.count();
    }
}
