/*    */ package org.junit.internal.builders;
/*    */ 
/*    */ import junit.framework.TestCase;
/*    */ import org.junit.internal.runners.JUnit38ClassRunner;
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runners.model.RunnerBuilder;
/*    */ 
/*    */ public class JUnit3Builder extends RunnerBuilder {
/*    */   public Runner runnerForClass(Class<?> testClass) throws Throwable {
/* 10 */     if (isPre4Test(testClass)) {
/* 11 */       return (Runner)new JUnit38ClassRunner(testClass);
/*    */     }
/* 13 */     return null;
/*    */   }
/*    */   
/*    */   boolean isPre4Test(Class<?> testClass) {
/* 17 */     return TestCase.class.isAssignableFrom(testClass);
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\builders\JUnit3Builder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */