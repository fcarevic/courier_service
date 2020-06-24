package org.junit.validator;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ValidateWith {
  Class<? extends AnnotationValidator> value();
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\validator\ValidateWith.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */