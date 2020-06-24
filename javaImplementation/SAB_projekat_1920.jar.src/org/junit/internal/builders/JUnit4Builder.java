/*    */ package org.junit.internal.builders;
/*    */ 
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runners.BlockJUnit4ClassRunner;
/*    */ import org.junit.runners.model.RunnerBuilder;
/*    */ 
/*    */ public class JUnit4Builder
/*    */   extends RunnerBuilder {
/*    */   public Runner runnerForClass(Class<?> testClass) throws Throwable {
/* 10 */     return (Runner)new BlockJUnit4ClassRunner(testClass);
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\builders\JUnit4Builder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */