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
import models.Tweet;
import models.messages.TweetProcessedMessage;
import models.messages.FinishTopicAnalysisMessage;
import models.messages.StartTopicAnalysisMessage;
import models.messages.TopicAnalysisErrorMessage;

/**
 *
 * @author sergio
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TweetsProcessedBean implements TweetsProcessedBeanLocal {
    @Resource(mappedName = "jms/tweetsProcessedTopic")
    private Topic tweetsProcessedTopic;
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    
    private void sendMessage(final Serializable data){
        Logger.getLogger(TweetsProcessedBean.class.getName()).log(Level.INFO, "PUBLICANDO MENSAJE ...");
        ObjectMessage message = context.createObjectMessage(data);
        context.createProducer().send(tweetsProcessedTopic, message);
    }
    
    @Override
    public void startTopicAnalysis(final models.Topic topic) {
        StartTopicAnalysisMessage startTopicAnalysisMessage = new StartTopicAnalysisMessage(topic);
        sendMessage(startTopicAnalysisMessage);
    }

    @Override
    public void addProcessedTweet(final Tweet tweet) {
        TweetProcessedMessage tweetProcessed = new TweetProcessedMessage(tweet.getTopic().getName(), tweet.getSentiment().name());
        sendMessage(tweetProcessed);
    }

    @Override
    public void finishTopicAnalysis(final models.Topic topic) {
        FinishTopicAnalysisMessage ftam = new FinishTopicAnalysisMessage(topic.getName(), topic.getTweets().size());
        sendMessage(ftam);
    }

    @Override
    public void topicAnalysisError(final models.Topic topic) {
        TopicAnalysisErrorMessage taem = new TopicAnalysisErrorMessage(topic.getName());
        sendMessage(taem);
    }
}
