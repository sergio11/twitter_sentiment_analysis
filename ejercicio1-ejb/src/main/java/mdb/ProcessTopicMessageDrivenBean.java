/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdb;

import search.TwitterSearchBeanLocal;
import analyzer.SentimentAnalyzerBeanLocal;
import dao.TopicDAOBeanLocal;
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
import models.messages.FinishTopicAnalysisMessage;
import models.messages.StartTopicAnalysisMessage;
import models.messages.TweetProcessedMessage;
import models.messages.TweetsNotFoundForTopic;
import search.exceptions.TweetsNotFound;
import twitter4j.TwitterException;

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
    private TopicDAOBeanLocal topicDAOBean;
    @EJB
    private TweetsProcessedBeanLocal tweetsProcessedBean;
    
    @Override
    public void onMessage(Message message) {
        
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Topic topic = (Topic) objectMessage.getObject();
            // send start analysis
            StartTopicAnalysisMessage startTopicAnalysisMessage = new StartTopicAnalysisMessage(topic);
            tweetsProcessedBean.sendMessage(startTopicAnalysisMessage);
            // search tweets for topic
            List<Tweet> tweets = twitterSearchBean.search(topic.getName());
            // get sentiments for tweets
            for (Tweet tweet : tweets) {
                Sentiment sentiment = sentimentAnalyzerBean.findSentiment(tweet.getText());
                tweet.setSentiment(sentiment);
                tweet.setTopic(topic);
                // send tweet processed
                TweetProcessedMessage tweetProcessed = new TweetProcessedMessage(topic.getName(), sentiment.name());
                tweetsProcessedBean.sendMessage(tweetProcessed);
                // Retrasamos 2 segundos cada iteración para porder visualizar como cambian las gráficas en tiempo real
                // En un caso real, serán tantos los tweets que no será necesario es retraso
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProcessTopicMessageDrivenBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            topicDAOBean.persist(topic);
            tweetDAOBean.persist(tweets);
            FinishTopicAnalysisMessage ftam = new FinishTopicAnalysisMessage(topic.getName(), tweets.size());
            tweetsProcessedBean.sendMessage(ftam);
        } catch (JMSException ex) {
           mdctx.setRollbackOnly();
        } catch(TweetsNotFound ex){
            tweetsProcessedBean.sendMessage(new TweetsNotFoundForTopic());
        }catch(Exception ex){
        
        }
    }
    
}
