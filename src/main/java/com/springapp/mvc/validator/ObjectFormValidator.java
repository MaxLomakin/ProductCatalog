package com.springapp.mvc.validator;

import com.springapp.mvc.domain.DataObject;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Max on 12.01.2017.
 */
public class ObjectFormValidator implements Validator {



    @Override
    public boolean supports(Class<?> clazz) {
        return DataObject.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.objectForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "objectType", "NotEmpty.objectForm.objectType");
    }
}
