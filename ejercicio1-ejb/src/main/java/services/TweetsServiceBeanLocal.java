/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import javax.ejb.Local;
import models.TweetsBySentiment;

/**
 *
 * @author sergio
 */
@Local
public interface TweetsServiceBeanLocal {

    List<TweetsBySentiment> groupedBySentiment(final String topic);
    
}
