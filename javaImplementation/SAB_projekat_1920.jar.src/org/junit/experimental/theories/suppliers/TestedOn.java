package org.junit.experimental.theories.suppliers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.experimental.theories.ParametersSuppliedBy;

@ParametersSuppliedBy(TestedOnSupplier.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface TestedOn {
  int[] ints();
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\experimental\theories\suppliers\TestedOn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */