/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import services.UserServiceBeanLocal;

/**
 *
 * @author sergio
 */
@ManagedBean(name = "userNameMustBeUniqueValidator")
@RequestScoped
public class UserNameMustBeUniqueValidator implements Validator{
    @EJB
    private UserServiceBeanLocal userServiceBean;
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;

    public ResourceBundle getI18n() {
        return i18n;
    }

    public void setI18n(ResourceBundle i18n) {
        this.i18n = i18n;
    }
   
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UIInput usernameComponent = (UIInput) component;
        String username = (String) usernameComponent.getSubmittedValue();
        if (userServiceBean.exists(username)) {
            FacesMessage facesMessage = new FacesMessage(i18n.getString("errors.useralredyexists"));
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMessage);
        }
    }
}
