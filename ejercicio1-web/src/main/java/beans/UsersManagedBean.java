/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import facade.FacadeBeanLocal;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import models.Group;
import models.User;

/**
 *
 * @author sergio
 */
@ManagedBean(name = "usersBean")
@SessionScoped
public class UsersManagedBean implements Serializable {
    
    @EJB
    private FacadeBeanLocal facadeBean;
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;
    private List<User> users;
    private List<Group> groups;
    private User userToDelete;
    private User userToUpdate;
    private User userToCreate = new User();

    public ResourceBundle getI18n() {
        return i18n;
    }

    public void setI18n(ResourceBundle i18n) {
        this.i18n = i18n;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getUserToDelete() {
        return userToDelete;
    }

    public void setUserToDelete(User userToDelete) {
        this.userToDelete = userToDelete;
    }

    public User getUserToUpdate() {
        return userToUpdate;
    }

    public void setUserToUpdate(User userToUpdate) {
        this.userToUpdate = userToUpdate;
    }

    public User getUserToCreate() {
        return userToCreate;
    }

    public void setUserToCreate(User userToCreate) {
        this.userToCreate = userToCreate;
    }
    
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    
    private void addMessage(FacesMessage message){
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    @PostConstruct
    protected void init(){
        users = facadeBean.getAllUsers();
    }
    
    public void update(){
        // call to EJB for persist contact
        facadeBean.persistUser(userToUpdate);
        // add confirmation message
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setDetail(i18n.getString("user.updated"));
        this.addMessage(message);
        // reset user
        userToUpdate = new User();
    }
    
    public void create(){
        // call to EJB for persist contact
        facadeBean.persistUser(userToCreate);
        // add confirmation message
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setDetail(i18n.getString("user.saved"));
        this.addMessage(message);
        // reset user
        userToCreate = new User();
    }
    
    public void delete(){
        facadeBean.removeUser(userToDelete);
        // add confirmation message
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setDetail("Usuario borrado");
        this.addMessage(message);
        userToDelete = new User();
    }
    
    

}
