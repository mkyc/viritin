package org.vaadin.viritin.fields;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.BeanValidator;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 *
 * @author Matti Tahvonen
 */
public class IntegerSliderField extends IntegerField {

    private Integer max;
    private Integer min;
    private Integer step;

    public IntegerSliderField() {
        setHtmlFieldType("range");
    }

    @Override
    public void addValidator(Validator validator) {
        super.addValidator(validator);
        if (validator instanceof BeanValidator) {
            BeanValidator beanValidator = (BeanValidator) validator;
            // If there is a bean validator and Max/Min values, uses them
            // automatically on the client side as well
            try {
                // Don't ask why I did this like this...
                Field propertyNameField = BeanValidator.class.getDeclaredField("propertyName");
                propertyNameField.setAccessible(true);
                String fieldName = propertyNameField.get(beanValidator).toString();
                Field beanClass = BeanValidator.class.getDeclaredField("beanClass");
                beanClass.setAccessible(true);
                Class beantype = (Class) beanClass.get(beanValidator);
                
                Field field = beantype.getDeclaredField(fieldName);
                field.setAccessible(true);
                Max max = field.getAnnotation(Max.class);
                if(max != null) {
                    setMax((int) max.value());
                }
                Min min = field.getAnnotation(Min.class);
                if(min != null) {
                    setMin((int) min.value());
                }
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(IntegerSliderField.class.getName()).
                        log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(IntegerSliderField.class.getName()).
                        log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(IntegerSliderField.class.getName()).
                        log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(IntegerSliderField.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public IntegerSliderField withMax(Integer min) {
        setMax(min);
        return this;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }
    
    public IntegerSliderField withMin(Integer min) {
        setMin(min);
        return this;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
    
    public IntegerSliderField withStep(Integer step) {
        setStep(step);
        return this;
    }

    @Override
    protected void configureHtmlElement() {
        super.configureHtmlElement();
        if(max != null) {
            s.setProperty("max", max.toString());
        }
        if(min != null) {
            s.setProperty("min", min.toString());
        }
        if(step != null) {
            s.setProperty("step", step.toString());
        }
    }

    @Override
    public IntegerSliderField withCaption(String caption) {
        return (IntegerSliderField) super.withCaption(caption);
    }
    
}
