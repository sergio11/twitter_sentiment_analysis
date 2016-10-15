/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.annotation.Resource;
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
public class FacadeBean implements FacadeBeanLocal {
    @Resource(mappedName = "jms/QueueReceptor")
    private Queue queueReceptor;
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    @Override
    public void analyzeTopic(String text) {
        Topic topic = new Topic(text);
        ObjectMessage message = 
                  context.createObjectMessage(topic);
        context.createProducer().send(queueReceptor, message);
    }
    
}
