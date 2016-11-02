/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import models.Topic;
import models.User;
import org.apache.commons.lang3.StringUtils;
import services.TopicsServiceBeanLocal;

/**
 *
 * @author sergio
 */
@ManagedBean(name = "topicsManagedBean")
@SessionScoped
public class TopicsManagedBean {
    private final static Integer MAX_RECENT_TOPICS_TO_SHOW = 5;
    @EJB
    private TopicsServiceBeanLocal topicsServiceBean;
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;
    private String text;
    private User currentUser;
    // Todos los temas analizados por el usuario
    private List<Topic> topicsByUser;
    // Temas recientes en el sistema
    private List<Topic> recentTopics;
    // Todos los temas
    private List<Topic> topics;
    // Todos los temas en formato CSV para los input tags
    private String topicsCSV;
    // Temas seleccionados por el usuario
    private List<String> topicsSelected;
    private Integer totalTopics;
    // Tema que se va borrar
    private Topic topicToDelete;
    // Tema para ver en detalle
    private Topic topicToShow;
   

    public ResourceBundle getI18n() {
        return i18n;
    }

    public void setI18n(ResourceBundle i18n) {
        this.i18n = i18n;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public List<Topic> getTopicsByUser() {
         // load topicsByUser for this user
        topicsByUser = topicsServiceBean.getTopicsByUser(currentUser.getUserName());
        return topicsByUser;
    }
    
    public List<Topic> getRecentTopics() {
        recentTopics = topicsServiceBean.getRecentTopics(MAX_RECENT_TOPICS_TO_SHOW);
        return recentTopics;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public String getTopicsCSV() {
        return topicsCSV;
    }

    public void setTopicsCSV(String topicsCSV) {
        this.topicsCSV = topicsCSV;
    }

    public List<String> getTopicsSelected() {
        return topicsSelected;
    }

    public void setTopicsSelected(List<String> topicsSelected) {
        this.topicsSelected = topicsSelected;
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

    public Topic getTopicToShow() {
        return topicToShow;
    }

    public void setTopicToShow(Topic topicToShow) {
        this.topicToShow = topicToShow;
    }
    
    @PostConstruct
    protected void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        currentUser = (User) externalContext.getSessionMap().get("user");
        topics = topicsServiceBean.getTopics();
        List<String> topicNames = Lists.transform(topics, new Function<Topic, String>(){
            @Override
            public String apply(Topic topic) {
                return topic.getName();
            }
        });
        topicsCSV = StringUtils.join(topicNames, ",");
    }
    
    public void confirmDelete(Topic t){
        topicToDelete = t;
    }
    
    public void showTopic(Topic t){
        topicToShow = t;
    }
    
    public void remove(){
        topicsServiceBean.remove(topicToDelete);
        // add confirmation message
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setDetail(i18n.getString("confirm.remove.topic"));
        FacesContext.getCurrentInstance().addMessage(null, message);
        topicToDelete = null;
    }
    // Permite analizar un temas
    public void analyze(){
         try {
             FacesMessage message = new FacesMessage();
            if(!topicsServiceBean.exists(text)){
                // analyze topic
                Topic topic = new Topic();
                topic.setName(text);
                topic.setUser(currentUser);
                topicsServiceBean.analyzeTopic(topic);
                // add confirmation message
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                message.setDetail("Analizando tema:" + text);
            } else {
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setDetail("Ya existe el tema:" + text);
                text = null;
            }
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch(EJBException e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
        }
    }
}
