package krystianrytel.sklepaukcyjny.validators.annotations;

import krystianrytel.sklepaukcyjny.validators.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {
    String message() default "Użytkownik o podaej nazwie już istnieje!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

