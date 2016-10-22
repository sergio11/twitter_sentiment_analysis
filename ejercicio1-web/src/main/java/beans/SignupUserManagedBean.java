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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import models.Group;
import models.User;


/**
 *
 * @author sergio
 */
@ManagedBean(name = "userBean")
@RequestScoped
public class SignupUserManagedBean {
    @EJB
    private FacadeBeanLocal facadeBean;
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;
    private User user;
    private List<Group> groups;
    
    @PostConstruct
    public void init(){
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ResourceBundle getI18n() {
        return i18n;
    }

    public void setI18n(ResourceBundle i18n) {
        this.i18n = i18n;
    }

    public FacadeBeanLocal getFacadeBean() {
        return facadeBean;
    }

    public void setFacadeBean(FacadeBeanLocal facadeBean) {
        this.facadeBean = facadeBean;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    
    public void save(){
        facadeBean.persistUser(user);
        FacesMessage facesMessage = new FacesMessage(i18n.getString("user.saved"));
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        user = new User();
    }
 
}
