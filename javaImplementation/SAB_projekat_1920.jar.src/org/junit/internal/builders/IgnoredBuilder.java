/*    */ package org.junit.internal.builders;
/*    */ 
/*    */ import org.junit.Ignore;
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runners.model.RunnerBuilder;
/*    */ 
/*    */ public class IgnoredBuilder
/*    */   extends RunnerBuilder {
/*    */   public Runner runnerForClass(Class<?> testClass) {
/* 10 */     if (testClass.getAnnotation(Ignore.class) != null) {
/* 11 */       return new IgnoredClassRunner(testClass);
/*    */     }
/* 13 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\builders\IgnoredBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */