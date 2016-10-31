/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import models.Tweet;
import services.TweetsServiceBeanLocal;



/**
 *
 * @author sergio
 */
@ManagedBean(name = "tweetsManagedBean")
@ViewScoped
public class TweetsManagedBean {
    @EJB
    private TweetsServiceBeanLocal tweetsServiceBean;

    public List<Tweet> getTweetsByTopic(final String topic){
        return tweetsServiceBean.byTopic(topic);
    }
    
    public List<Tweet> getTweetsByTopic(final String topic, final Integer count){
        return tweetsServiceBean.byTopic(topic, count);
    }
}
