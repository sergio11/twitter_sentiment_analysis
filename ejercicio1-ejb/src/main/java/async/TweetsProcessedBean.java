/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package async;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import models.Tweet;

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
    public void enqueueTweet(final Tweet tweet) {
        ObjectMessage message = 
                  context.createObjectMessage(tweet);
        context.createProducer().send(tweetsProcessedTopic, message);
    }
}
