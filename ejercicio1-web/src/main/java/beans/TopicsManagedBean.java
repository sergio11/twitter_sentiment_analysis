/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import models.Topic;
import models.User;
import services.TopicsServiceBeanLocal;

/**
 *
 * @author sergio
 */
@ManagedBean(name = "topicsManagedBean")
@ViewScoped
public class TopicsManagedBean {
    private final static Integer MAX_RECENT_TOPICS_TO_SHOW = 5;
    @EJB
    private TopicsServiceBeanLocal topicsServiceBean;
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;
    private User currentUser;
    private List<Topic> topics;
    private List<Topic> recentTopics;
    private Integer totalTopics;
    private Topic topicToDelete;
   

    public ResourceBundle getI18n() {
        return i18n;
    }

    public void setI18n(ResourceBundle i18n) {
        this.i18n = i18n;
    }

    public List<Topic> getTopics() {
         // load topics for this user
        topics = topicsServiceBean.getTopicsByUser(currentUser.getUserName());
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Topic> getRecentTopics() {
        recentTopics = topicsServiceBean.getRecentTopics(MAX_RECENT_TOPICS_TO_SHOW);
        return recentTopics;
    }
    
    public Integer getTotalTopics() {
        totalTopics = topicsServiceBean.getTopicsCount();
        return totalTopics;
    }
   
    public Topic getTopicToDelete() {
        return topicToDelete;
    }

    public void setTopicToDelete(Topic topicToDelete) {
        this.topicToDelete = topicToDelete;
    }
    
    @PostConstruct
    protected void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        currentUser = (User) externalContext.getSessionMap().get("user");
    }
    
    public void confirmDelete(Topic t){
        topicToDelete = t;
    }
    
    public void remove(){
        topicsServiceBean.remove(topicToDelete);
        // add confirmation message
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setDetail(i18n.getString("confirm.remove.topic"));
        FacesContext.getCurrentInstance().addMessage(null, message);
        topicToDelete = new Topic();
    }
}
