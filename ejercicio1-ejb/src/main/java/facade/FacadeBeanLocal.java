/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.List;
import javax.ejb.Local;
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
@Local
public interface FacadeBeanLocal {

    void analyzeTopic(final Topic topic);
    List<Topic> getTopics();
    List<TweetsBySentiment> groupedBySentiment(final String topic);
    List<Country> getCountries();
    List<Province> getProvinces();
    List<Province> getProvincesByCountry(final Long country);
    void persistUser(final User user);
    List<User> getAllUsers();
    void removeUser(final User user);
    User findUser(final String username);
    List<Group> getAllGroups();
    Boolean existsUser(final String username);
    List<Topic> topicsByUser(final String userName);
    Group getGroupById(final Long id);

    void removeTopic(final Topic topic);
    
}
