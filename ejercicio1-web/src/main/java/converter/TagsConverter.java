/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author sergio
 */
@FacesConverter("tagsConverter")
public class TagsConverter implements Converter {
    
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        if (value == null) {
            FacesMessage message = new FacesMessage(i18n.getString("page.results.errors.tags"));
            throw new ConverterException(message);
        }
        
        List<String> topicsSelected;
        if(value.length() > 0){
            topicsSelected = new ArrayList();
            String[] terms = value.trim().split(",");
            for(int i = 0, len = terms.length; i < len; i++){
                topicsSelected.add(terms[i]);
            }
        }else{
            topicsSelected = Collections.emptyList();
        }
        return topicsSelected;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        List<String> topicSelected = (List<String>) value;
        return StringUtils.join(topicSelected, ",");
    }
    
}
