/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Local;
import models.Tweet;
import models.TweetsBySentiment;

/**
 *
 * @author sergio
 */
@Local
public interface TweetDAOBeanLocal {

    void persist(final Tweet tweet);
    void persist(List<Tweet> tweets);
    List<TweetsBySentiment> groupedBySentiment(final String topic);
    List<Tweet> byTopic(final String topic);

    List<Tweet> byTopic(final String topic, final Integer count);
}
