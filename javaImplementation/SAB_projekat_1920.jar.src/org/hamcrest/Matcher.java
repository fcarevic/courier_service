package org.hamcrest;

public interface Matcher<T> extends SelfDescribing {
  boolean matches(Object paramObject);
  
  void describeMismatch(Object paramObject, Description paramDescription);
  
  @Deprecated
  void _dont_implement_Matcher___instead_extend_BaseMatcher_();
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\hamcrest\Matcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */