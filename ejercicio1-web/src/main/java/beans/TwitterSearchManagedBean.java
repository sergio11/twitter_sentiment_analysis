/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import models.Topic;
import models.Tweet;
import models.User;
import services.TopicsServiceBeanLocal;

/**
 *
 * @author sergio
 */
@ManagedBean(name = "twitterSearch")
@ViewScoped
public class TwitterSearchManagedBean implements Serializable {
    @EJB
    private TopicsServiceBeanLocal topicsServiceBean;
    private String text;
    private List<Tweet> result;
    private User currentUser;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    
    public List<Tweet> getResult() {
        return result;
    }
    
    private void addMessage(FacesMessage message){
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    @PostConstruct
    protected void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        currentUser = (User)externalContext.getSessionMap().get("user");
    }

    public void search(){
        try {
            Topic topic = new Topic();
            topic.setName(text);
            topic.setUser(currentUser);
            // analyze topic
            topicsServiceBean.analyzeTopic(topic);
            // add confirmation message
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            message.setDetail("Analizando tema:" + text);
            this.addMessage(message);
        } catch(EJBException e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
        }
    }
}
