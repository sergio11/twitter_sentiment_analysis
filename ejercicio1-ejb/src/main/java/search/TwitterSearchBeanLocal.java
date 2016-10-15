/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.List;
import javax.ejb.Local;
import models.Tweet;

/**
 *
 * @author sergio
 */
@Local
public interface TwitterSearchBeanLocal {

    List<Tweet> search(final String topic);
    
}
