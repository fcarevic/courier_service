/*    */ package org.junit.internal.matchers;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ import org.hamcrest.Description;
/*    */ import org.hamcrest.Factory;
/*    */ import org.hamcrest.Matcher;
/*    */ import org.hamcrest.TypeSafeMatcher;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StacktracePrintingMatcher<T extends Throwable>
/*    */   extends TypeSafeMatcher<T>
/*    */ {
/*    */   private final Matcher<T> throwableMatcher;
/*    */   
/*    */   public StacktracePrintingMatcher(Matcher<T> throwableMatcher) {
/* 20 */     this.throwableMatcher = throwableMatcher;
/*    */   }
/*    */   
/*    */   public void describeTo(Description description) {
/* 24 */     this.throwableMatcher.describeTo(description);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean matchesSafely(T item) {
/* 29 */     return this.throwableMatcher.matches(item);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void describeMismatchSafely(T item, Description description) {
/* 34 */     this.throwableMatcher.describeMismatch(item, description);
/* 35 */     description.appendText("\nStacktrace was: ");
/* 36 */     description.appendText(readStacktrace((Throwable)item));
/*    */   }
/*    */   
/*    */   private String readStacktrace(Throwable throwable) {
/* 40 */     StringWriter stringWriter = new StringWriter();
/* 41 */     throwable.printStackTrace(new PrintWriter(stringWriter));
/* 42 */     return stringWriter.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   @Factory
/*    */   public static <T extends Throwable> Matcher<T> isThrowable(Matcher<T> throwableMatcher) {
/* 48 */     return (Matcher<T>)new StacktracePrintingMatcher<T>(throwableMatcher);
/*    */   }
/*    */ 
/*    */   
/*    */   @Factory
/*    */   public static <T extends Exception> Matcher<T> isException(Matcher<T> exceptionMatcher) {
/* 54 */     return (Matcher)new StacktracePrintingMatcher<Throwable>((Matcher)exceptionMatcher);
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\matchers\StacktracePrintingMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */