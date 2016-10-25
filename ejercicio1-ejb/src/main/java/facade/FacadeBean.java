/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import dao.CountryDAOBeanLocal;
import dao.GroupDAOBeanLocal;
import dao.ProvincesDAOBeanLocal;
import dao.TopicDAOBeanLocal;
import dao.TweetDAOBeanLocal;
import dao.UserDAOBeanLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import models.Country;
import models.Group;
import models.Province;
import models.Topic;
import models.TweetsBySentiment;
import models.User;

/**
 *
 * @author sergio
 */
@Stateless
public class FacadeBean implements FacadeBeanLocal {
    
    @Resource(mappedName = "jms/QueueReceptor")
    private Queue queueReceptor;
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    @EJB
    private TopicDAOBeanLocal topicDAOBean;
    @EJB
    private TweetDAOBeanLocal tweetDAOBean;
    @EJB
    private ProvincesDAOBeanLocal provincesDAOBean;
    @EJB
    private CountryDAOBeanLocal countryDAOBean;
    @EJB
    private UserDAOBeanLocal userDAOBean;
    @EJB
    private GroupDAOBeanLocal groupDAOBean;
    

    @Override
    public void analyzeTopic(String text) {
        Logger.getLogger(FacadeBean.class.getName()).log(Level.INFO, "Topic to analyze: " + text);
        Topic topic = new Topic(text.toLowerCase());
        //save topic
        topicDAOBean.persist(topic);
        // enqueue message
        ObjectMessage message = 
                  context.createObjectMessage(topic);
        context.createProducer().send(queueReceptor, message);
    }

    @Override
    public List<Topic> getTopics() {
        return topicDAOBean.all();
    }

    @Override
    public List<TweetsBySentiment> groupedBySentiment(final String topic) {
        return tweetDAOBean.groupedBySentiment(topic);
    }

    @Override
    public List<Country> getCountries() {
        return countryDAOBean.all();
    }

    @Override
    public List<Province> getProvinces() {
        return provincesDAOBean.all();
    }

    @Override
    public List<Province> getProvincesByCountry(final Long country) {
        return provincesDAOBean.getProvincesByCountry(country);
    }

    @Override
    public void persistUser(final User user) {
        userDAOBean.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAOBean.all();
    }

    @Override
    public void removeUser(final User user) {
        userDAOBean.remove(user);
    }

    @Override
    public User findUser(final String username) {
        return userDAOBean.find(username);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupDAOBean.all();
    }

    @Override
    public Boolean existsUser(final String username) {
        return userDAOBean.exists(username);
    }

    @Override
    public List<Topic> topicsByUser(final String userName) {
        return topicDAOBean.byUser(userName);
    }

    @Override
    public Group getGroupById(final Long id) {
        return groupDAOBean.byId(id);
    }
}
