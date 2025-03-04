/*    */ package org.junit.internal.runners;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.List;
/*    */ import org.junit.After;
/*    */ import org.junit.Before;
/*    */ import org.junit.Ignore;
/*    */ import org.junit.Test;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class TestMethod
/*    */ {
/*    */   private final Method method;
/*    */   private TestClass testClass;
/*    */   
/*    */   public TestMethod(Method method, TestClass testClass) {
/* 25 */     this.method = method;
/* 26 */     this.testClass = testClass;
/*    */   }
/*    */   
/*    */   public boolean isIgnored() {
/* 30 */     return (this.method.getAnnotation(Ignore.class) != null);
/*    */   }
/*    */   
/*    */   public long getTimeout() {
/* 34 */     Test annotation = this.method.<Test>getAnnotation(Test.class);
/* 35 */     if (annotation == null) {
/* 36 */       return 0L;
/*    */     }
/* 38 */     long timeout = annotation.timeout();
/* 39 */     return timeout;
/*    */   }
/*    */   
/*    */   protected Class<? extends Throwable> getExpectedException() {
/* 43 */     Test annotation = this.method.<Test>getAnnotation(Test.class);
/* 44 */     if (annotation == null || annotation.expected() == Test.None.class) {
/* 45 */       return null;
/*    */     }
/* 47 */     return annotation.expected();
/*    */   }
/*    */ 
/*    */   
/*    */   boolean isUnexpected(Throwable exception) {
/* 52 */     return !getExpectedException().isAssignableFrom(exception.getClass());
/*    */   }
/*    */   
/*    */   boolean expectsException() {
/* 56 */     return (getExpectedException() != null);
/*    */   }
/*    */   
/*    */   List<Method> getBefores() {
/* 60 */     return this.testClass.getAnnotatedMethods((Class)Before.class);
/*    */   }
/*    */   
/*    */   List<Method> getAfters() {
/* 64 */     return this.testClass.getAnnotatedMethods((Class)After.class);
/*    */   }
/*    */   
/*    */   public void invoke(Object test) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
/* 68 */     this.method.invoke(test, new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\runners\TestMethod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */