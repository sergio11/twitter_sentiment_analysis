/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import facade.FacadeBeanLocal;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import models.Topic;
import models.User;

/**
 *
 * @author sergio
 */
@ManagedBean(name = "topicsManagedBean")
@RequestScoped
public class TopicsManagedBean {
    @EJB
    private FacadeBeanLocal facadeBean;
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;
    private List<Topic> topics;

    public ResourceBundle getI18n() {
        return i18n;
    }

    public void setI18n(ResourceBundle i18n) {
        this.i18n = i18n;
    }
    
    @PostConstruct
    protected void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        User currentUser = (User) externalContext.getSessionMap().get("user");
        // load topics for this user
        topics = facadeBean.topicsByUser(currentUser.getUserName());
    }
    
    
    
    
}
