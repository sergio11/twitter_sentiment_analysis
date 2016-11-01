/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdb;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

/**
 *
 * @author sergio
 */
@Stateless
public class TweetsProcessedBean implements TweetsProcessedBeanLocal {
    @Resource(mappedName = "jms/tweetsProcessedTopic")
    private Topic tweetsProcessedTopic;
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void sendMessage(final Serializable message) {
        Logger.getLogger(TweetsProcessedBean.class.getName()).log(Level.INFO, "PUBLICANDO MENSAJE ...");
        ObjectMessage objectMessage = context.createObjectMessage(message);
        context.createProducer().send(tweetsProcessedTopic, objectMessage);
    }
}
