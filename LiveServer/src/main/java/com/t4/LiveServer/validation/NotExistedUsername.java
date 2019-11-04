package com.t4.LiveServer.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotExistedUsername {
    String message() default "Username already used!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
