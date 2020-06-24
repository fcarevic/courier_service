package org.junit.rules;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public interface MethodRule {
  Statement apply(Statement paramStatement, FrameworkMethod paramFrameworkMethod, Object paramObject);
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\rules\MethodRule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */