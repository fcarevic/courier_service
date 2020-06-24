package org.junit.experimental.categories;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.junit.validator.ValidateWith;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ValidateWith(CategoryValidator.class)
public @interface Category {
  Class<?>[] value();
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\experimental\categories\Category.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */