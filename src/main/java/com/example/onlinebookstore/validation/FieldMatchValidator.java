package com.example.onlinebookstore.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.val;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements
        ConstraintValidator<FieldMatch, Object> {
    private String first;
    private String second;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object object,
                           ConstraintValidatorContext constraintValidatorContext) {
        val firstFieldValue = new BeanWrapperImpl(object).getPropertyValue(first);
        val secondFieldValue = new BeanWrapperImpl(object).getPropertyValue(second);
        return firstFieldValue != null && firstFieldValue.equals(secondFieldValue);
    }
}
