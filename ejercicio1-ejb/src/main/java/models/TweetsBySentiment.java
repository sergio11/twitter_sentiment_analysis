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

    public TweetsBySentiment(Sentiment sentiment, Integer tweets) {
        this.sentiment = sentiment;
        this.tweets = tweets;
    }

    public TweetsBySentiment() {
    }
  

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public Integer getTweets() {
        return tweets;
    }

    public void setTweets(Integer tweets) {
        this.tweets = tweets;
    }
    
    
    
}
