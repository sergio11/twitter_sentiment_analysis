/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdb;

import search.TwitterSearchBeanLocal;
import analyzer.SentimentAnalyzerBeanLocal;
import dao.TweetDAOBeanLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/topicsQueue"),
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
    @EJB
    private TweetsProcessedBeanLocal tweetsProcessedBean;
    
    @Override
    public void onMessage(Message message) {
        
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Topic topic = (Topic) objectMessage.getObject();
            // send start analysis
            tweetsProcessedBean.startTopicAnalysis(topic);
            Logger.getLogger(ProcessTopicMessageDrivenBean.class.getName()).log(Level.INFO, "Analizando término: " + topic.getName());
            // search tweets for topic
            List<Tweet> tweets = twitterSearchBean.search(topic.getName());
            // get sentiments for tweets
            for (Tweet tweet : tweets) {
                Sentiment sentiment = sentimentAnalyzerBean.findSentiment(tweet.getText());
                tweet.setSentiment(sentiment);
                tweet.setTopic(topic);
                // send tweet processed
                tweetsProcessedBean.addProcessedTweet(tweet);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProcessTopicMessageDrivenBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            tweetDAOBean.persist(tweets);
            tweetsProcessedBean.finishTopicAnalysis(topic);
        } catch (JMSException ex) {
           mdctx.setRollbackOnly();
        }   
    }
    
}
