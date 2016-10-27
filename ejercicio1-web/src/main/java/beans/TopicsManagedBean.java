/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import facade.FacadeBeanLocal;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author sergio
 */
@ManagedBean(name = "topicsManagedBean")
@ViewScoped
public class TopicsManagedBean {
    
    @EJB
    private FacadeBeanLocal facadeBean;
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;
    private List<Topic> topics;
    private Topic topicToDelete;
   

    public ResourceBundle getI18n() {
        return i18n;
    }

    public void setI18n(ResourceBundle i18n) {
        this.i18n = i18n;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
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
        User currentUser = (User) externalContext.getSessionMap().get("user");
        // load topics for this user
        topics = facadeBean.topicsByUser(currentUser.getUserName());
    }
    
    public void confirmDelete(Topic t){
        topicToDelete = t;
    }
    
    public void remove(){
        facadeBean.removeTopic(topicToDelete);
        // add confirmation message
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setDetail(i18n.getString("confirm.remove.topic"));
        FacesContext.getCurrentInstance().addMessage(null, message);
        topicToDelete = new Topic();
    }
}
