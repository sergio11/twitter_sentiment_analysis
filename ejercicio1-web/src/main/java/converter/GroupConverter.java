/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import models.Group;
import services.UserServiceBeanLocal;

/**
 *
 * @author sergio
 */
@ManagedBean(name = "groupConverter")
@RequestScoped
@FacesConverter(forClass = Group.class)
public class GroupConverter implements Converter {
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
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try{
            return userServiceBean.getGroupByName(value);
        }catch(EJBException e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new ConverterException(new FacesMessage(i18n.getString("errors.converter.group")));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Group){
            return ((Group)value).getName();
        }
        return null;
    }
    
}
