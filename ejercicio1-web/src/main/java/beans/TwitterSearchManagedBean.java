/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import facade.FacadeBeanLocal;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import models.Tweet;

/**
 *
 * @author sergio
 */
@ManagedBean(name = "twitterSearch")
@SessionScoped
public class TwitterSearchManagedBean implements Serializable {
    
    @EJB
    private FacadeBeanLocal facadeBean;
    @ManagedProperty("#{liveSentimentChartBean}")
    private LiveSentimentChartManagedBean liveSentimentChartBean;
    private String text;
    private List<Tweet> result;

    public LiveSentimentChartManagedBean getLiveSentimentChartBean() {
        return liveSentimentChartBean;
    }

    public void setLiveSentimentChartBean(LiveSentimentChartManagedBean liveSentimentChartBean) {
        this.liveSentimentChartBean = liveSentimentChartBean;
    }

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

    public void search(){
        try {
            // analyze topic
            facadeBean.analyzeTopic(text);
            // create live chart for topic
            liveSentimentChartBean.createChart(text);
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
