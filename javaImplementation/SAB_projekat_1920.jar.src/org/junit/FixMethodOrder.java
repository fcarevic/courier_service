package org.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.runners.MethodSorters;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface FixMethodOrder {
  MethodSorters value() default MethodSorters.DEFAULT;
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\FixMethodOrder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */