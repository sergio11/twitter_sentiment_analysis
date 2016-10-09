/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import ejb.TwitterSearchBeanLocal;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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
    private TwitterSearchBeanLocal twitterSearchBean;

    private String text;
    private List<Tweet> result;

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
            result = twitterSearchBean.search(text);
            // add confirmation message
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            message.setDetail("La consulta ha obtenido " + result.size() + " tweets");
            this.addMessage(message);
        } catch(EJBException e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
        }
    }
}
