package org.junit.experimental.theories;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.experimental.theories.internal.SpecificDataPointsSupplier;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@ParametersSuppliedBy(SpecificDataPointsSupplier.class)
public @interface FromDataPoints {
  String value();
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\experimental\theories\FromDataPoints.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */