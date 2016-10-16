/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.List;
import javax.ejb.Local;
import models.Topic;
import models.Tweet;
import models.TweetsBySentiment;

/**
 *
 * @author sergio
 */
@Local
public interface FacadeBeanLocal {

    void analyzeTopic(String topic);
    List<Topic> getTopics();
    List<TweetsBySentiment> groupedBySentiment(final String topic);
    
}
