package org.junit.rules;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public interface TestRule {
  Statement apply(Statement paramStatement, Description paramDescription);
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\rules\TestRule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */