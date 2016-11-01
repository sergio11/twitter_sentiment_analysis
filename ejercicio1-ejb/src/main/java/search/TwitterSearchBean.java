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
import javax.annotation.Resource;
import javax.ejb.Stateless;
import mapper.TweetDataMapper;
import models.Tweet;
import search.exceptions.TweetsNotFound;
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
    
   
    @Resource(name = "TWITTER_OAUTH_CONSUMER_KEY")
    private String oauthConsumerKey;
    @Resource(name = "TWITTER_OAUTH_CONSUMER_SECRET")
    private String oauthConsumerSecret;
    @Resource(name = "TWITTER_OAUTH_ACCESS_TOKEN")
    private String oauthAccessToken;
    @Resource(name = "TWITTER_OAUTH_ACCESS_TOKEN_SECRET")
    private String oauthAccessTokenSecret;
    private Twitter twitter;
    private final TweetDataMapper mapper = new TweetDataMapper();
   
    @PostConstruct
    public void init() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("tyyEHDfVKxsBqQWTeWgPkRhZd")
                .setOAuthConsumerSecret("94MPLfuaVzrWDo6RKc06MnuiJMz1yMkLvcO3wyqd6WtxIpSte0")
                .setOAuthAccessToken("169980309-qZqlDKT3XSdoki1WBmQFLHANx7QEyGxxm5tEN7gu")
                .setOAuthAccessTokenSecret("NjVrRzShxNkzyvUD5kn3KcvNvfQrelJXpzfljnSQeq9Ef");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    @Override
    public List<Tweet> search(final String keyword) throws TweetsNotFound, TwitterException {
        Query query = new Query(keyword + " -filter:retweets -filter:links -filter:replies -filter:images");
        query.setCount(20);
        query.setLocale("en");
        query.setLang("en");
        QueryResult queryResult = twitter.search(query);
        List<Status> tweetsStatus = queryResult.getTweets();
        if (tweetsStatus.isEmpty())
            throw new TweetsNotFound();
        Collection<Tweet> tweets = mapper.transform(tweetsStatus);
        return (List<Tweet>) tweets;
    }
}
