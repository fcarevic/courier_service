/*    */ package org.junit.internal.matchers;
/*    */ 
/*    */ import org.hamcrest.Description;
/*    */ import org.hamcrest.Factory;
/*    */ import org.hamcrest.Matcher;
/*    */ import org.hamcrest.SelfDescribing;
/*    */ import org.hamcrest.TypeSafeMatcher;
/*    */ 
/*    */ public class ThrowableMessageMatcher<T extends Throwable>
/*    */   extends TypeSafeMatcher<T> {
/*    */   private final Matcher<String> matcher;
/*    */   
/*    */   public ThrowableMessageMatcher(Matcher<String> matcher) {
/* 14 */     this.matcher = matcher;
/*    */   }
/*    */   
/*    */   public void describeTo(Description description) {
/* 18 */     description.appendText("exception with message ");
/* 19 */     description.appendDescriptionOf((SelfDescribing)this.matcher);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean matchesSafely(T item) {
/* 24 */     return this.matcher.matches(item.getMessage());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void describeMismatchSafely(T item, Description description) {
/* 29 */     description.appendText("message ");
/* 30 */     this.matcher.describeMismatch(item.getMessage(), description);
/*    */   }
/*    */   
/*    */   @Factory
/*    */   public static <T extends Throwable> Matcher<T> hasMessage(Matcher<String> matcher) {
/* 35 */     return (Matcher)new ThrowableMessageMatcher<Throwable>(matcher);
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\matchers\ThrowableMessageMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */