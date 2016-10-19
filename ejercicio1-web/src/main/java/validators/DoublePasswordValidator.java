/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author sergio
 */
@FacesValidator("doublePasswordValidator")
public class DoublePasswordValidator implements Validator {
    
    private final static String REPEAT_PARAM = "repeatValue";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Map<String, Object> attrs = component.getAttributes();
        if(attrs.containsKey(REPEAT_PARAM)){
            String repeatComponentId = (String) attrs.get(REPEAT_PARAM);
            UIInput repeatComponent = (UIInput) component.getParent().findComponent(repeatComponentId);
            String repeatValue = (String) repeatComponent.getSubmittedValue();
            if(repeatValue == null || repeatValue.length() == 0 || !repeatValue.equals(value)){
                String message = "Los dos campos no son iguales";
                FacesMessage facesMessage = new FacesMessage(message);
                facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(facesMessage);
            }
        }
    }
    
}
