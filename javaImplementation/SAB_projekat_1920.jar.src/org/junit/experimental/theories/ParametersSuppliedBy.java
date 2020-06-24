package org.junit.experimental.theories;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
public @interface ParametersSuppliedBy {
  Class<? extends ParameterSupplier> value();
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\experimental\theories\ParametersSuppliedBy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */