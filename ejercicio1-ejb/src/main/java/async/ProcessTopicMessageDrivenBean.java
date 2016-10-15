/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package async;

import search.TwitterSearchBeanLocal;
import analyzer.SentimentAnalyzerBeanLocal;
import dao.TweetDAOBeanLocal;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import models.Sentiment;
import models.Topic;
import models.Tweet;

/**
 *
 * @author sergio
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/QueueReceptor"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ProcessTopicMessageDrivenBean implements MessageListener {
    
    @Resource
    private MessageDrivenContext mdctx;
    @EJB
    private TwitterSearchBeanLocal twitterSearchBean;
    @EJB
    private SentimentAnalyzerBeanLocal sentimentAnalyzerBean;
    @EJB
    private TweetDAOBeanLocal tweetDAOBean;
    
    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Topic topic = (Topic) objectMessage.getObject();
            // search tweets for topic
            List<Tweet> tweets = twitterSearchBean.search(topic.getName());
            // get sentiments for tweets
            for (Tweet tweet : tweets) {
                Sentiment sentiment = sentimentAnalyzerBean.findSentiment(tweet.getText());
                tweet.setSentiment(sentiment);
                tweet.setTopic(topic);
            }
            tweetDAOBean.persist(tweets);
        } catch (JMSException ex) {
           mdctx.setRollbackOnly();
        }   
    }
    
}