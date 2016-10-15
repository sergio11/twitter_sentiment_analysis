/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import mapper.TweetDataMapper;
import models.Tweet;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author sergio
 */
@Stateless
public class TwitterSearchBean implements TwitterSearchBeanLocal {
    
   
    private Twitter twitter;
    private final TweetDataMapper mapper = new TweetDataMapper();
   
    @PostConstruct
    public void init() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("please enter key here")
                .setOAuthConsumerSecret("please enter key here")
                .setOAuthAccessToken("please enter key here")
                .setOAuthAccessTokenSecret("please enter key here");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    @Override
    public List<Tweet> search(final String keyword) {
        Query query = new Query(keyword + " -filter:retweets -filter:links -filter:replies -filter:images");
        query.setCount(20);
        query.setLocale("en");
        query.setLang("en");
        try {
            QueryResult queryResult = twitter.search(query);
            List<Status> tweetsStatus = queryResult.getTweets();
            Collection<Tweet> tweets = mapper.transform(tweetsStatus);
            return (List<Tweet>) tweets;
        } catch (TwitterException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    
}
