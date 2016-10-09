/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import models.Tweet;
import twitter4j.Status;

/**
 *
 * @author sergio
 */
public class TweetDataMapper implements IDataMapper<Tweet, Status>{

    /**
    * Transform a {@link Status} into an {@link Tweet}.
    *
    * @param status Object to be transformed.
    * @return {@link Tweet}.
    */
    @Override
    public Tweet transform(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        Tweet tweet = new Tweet();
        tweet.setCreateAt(status.getCreatedAt());
        tweet.setLang(status.getLang());
        if(status.getGeoLocation() != null){
            tweet.setLat(status.getGeoLocation().getLatitude());
            tweet.setLon(status.getGeoLocation().getLongitude());
        }
        tweet.setReTweetCount(status.getRetweetCount());
        tweet.setText(status.getText());
        return tweet;
        
    }

    /**
    * Transform a Collection of {@link status} into a Collection of {@link Tweet}.
    *
    * @param statusCollection Objects to be transformed.
    * @return List of {@link Tweet}.
    */
    @Override
    public Collection<Tweet> transform(Collection<Status> statusCollection) {
        Collection<Tweet> tweetsCollection;
        if (statusCollection != null && !statusCollection.isEmpty()) {
            tweetsCollection = new ArrayList<>();
            for (Status status : statusCollection) {
                tweetsCollection.add(transform(status));
            }
        } else {
            tweetsCollection = Collections.emptyList();
        }

        return tweetsCollection;
    }
    
}
