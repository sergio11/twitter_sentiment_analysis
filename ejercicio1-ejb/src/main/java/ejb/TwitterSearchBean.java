/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
    
    @Resource(name="TWITTER_OAUTH_CONSUMER_KEY")
    private String consumerKey;
    @Resource(name="TWITTER_OAUTH_CONSUMER_SECRET")
    private String consumerSecret;
    @Resource(name="TWITTER_OAUTH_ACCESS_TOKEN")
    private String consumerAccessToken;
    @Resource(name="TWITTER_OAUTH_ACCESS_TOKEN_SECRET")
    private String consumerAccessTokenSecret;
    private Twitter twitter;
    private final TweetDataMapper mapper = new TweetDataMapper();
    
    @PostConstruct
    public void init() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(consumerAccessToken)
                .setOAuthAccessTokenSecret(consumerAccessTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    @Override
    public List<Tweet> search(final String keyword) {
        Query query = new Query(keyword + " -filter:retweets -filter:links -filter:replies -filter:images");
        query.setCount(20);
        query.setLocale("es");
        query.setLang("es");
        try {
            QueryResult queryResult = twitter.search(query);
            List<Status> tweets = queryResult.getTweets();
            return (List<Tweet>) mapper.transform(tweets);
        } catch (TwitterException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    
}