/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import dao.TopicDAOBeanLocal;
import dao.TweetDAOBeanLocal;
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
import models.Tweet;
import models.TweetsBySentiment;

/**
 *
 * @author sergio
 */
@Stateless
public class FacadeBean implements FacadeBeanLocal {
    
    
    @Resource(mappedName = "jms/QueueReceptor")
    private Queue queueReceptor;
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    @EJB
    private TopicDAOBeanLocal topicDAOBean;
    @EJB
    private TweetDAOBeanLocal tweetDAOBean;

    @Override
    public void analyzeTopic(String text) {
        Topic topic = new Topic(text);
        //save topic
        topicDAOBean.persist(topic);
        // enqueue message
        ObjectMessage message = 
                  context.createObjectMessage(topic);
        context.createProducer().send(queueReceptor, message);
    }

    @Override
    public List<Topic> getTopics() {
        return topicDAOBean.all();
    }

    @Override
    public List<TweetsBySentiment> groupedBySentiment(final String topic) {
        return tweetDAOBean.groupedBySentiment(topic);
    }
    
    
}
