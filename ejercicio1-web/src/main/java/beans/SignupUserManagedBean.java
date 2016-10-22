/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import facade.FacadeBeanLocal;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import models.Role;
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
    
    public SelectItem[] getRoleValues() {
        SelectItem[] items = new SelectItem[Role.ROLE.values().length];
        int i = 0;
        for(Role.ROLE r: Role.ROLE.values()) {
          items[i++] = new SelectItem(r, r.name());
        }
        return items;
    }
    
    public void save(){
        facadeBean.persistUser(user);
        FacesMessage facesMessage = new FacesMessage(i18n.getString("user.saved"));
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        user = new User();
    }
 
}
