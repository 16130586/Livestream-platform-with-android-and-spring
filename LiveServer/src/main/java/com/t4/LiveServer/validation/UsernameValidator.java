package com.t4.LiveServer.validation;

import com.t4.LiveServer.business.interfaze.UserBusiness;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<NotExistedUsername, String> {
    @Autowired
    UserBusiness userBusiness;

    @Override
    public void initialize(NotExistedUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userBusiness == null) {
            System.out.println("null exception");
            return true;
        }
        return !userBusiness.checkExistsUsername(value);
    }
}
