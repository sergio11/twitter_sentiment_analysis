/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author sergio
 */
public class TweetsBySentiment implements Serializable {
    
    private Sentiment sentiment;
    private Integer tweets;

    public TweetsBySentiment(Integer sentiment, Integer tweets) {
        this.sentiment = Sentiment.getFromValue(sentiment);
        this.tweets = tweets;
    }

    public TweetsBySentiment() {
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Integer sentiment) {
        this.sentiment = Sentiment.getFromValue(sentiment);
    }
    
    public Integer getTweets() {
        return tweets;
    }

    public void setTweets(Integer tweets) {
        this.tweets = tweets;
    }
    
    
    
}
