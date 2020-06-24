/*    */ package org.junit.internal.requests;
/*    */ 
/*    */ import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
/*    */ import org.junit.runner.Request;
/*    */ import org.junit.runner.Runner;
/*    */ 
/*    */ public class ClassRequest extends Request {
/*  8 */   private final Object runnerLock = new Object();
/*    */ 
/*    */   
/*    */   private final Class<?> fTestClass;
/*    */ 
/*    */   
/*    */   private final boolean canUseSuiteMethod;
/*    */   
/*    */   private volatile Runner runner;
/*    */ 
/*    */   
/*    */   public ClassRequest(Class<?> testClass, boolean canUseSuiteMethod) {
/* 20 */     this.fTestClass = testClass;
/* 21 */     this.canUseSuiteMethod = canUseSuiteMethod;
/*    */   }
/*    */   
/*    */   public ClassRequest(Class<?> testClass) {
/* 25 */     this(testClass, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public Runner getRunner() {
/* 30 */     if (this.runner == null) {
/* 31 */       synchronized (this.runnerLock) {
/* 32 */         if (this.runner == null) {
/* 33 */           this.runner = (new AllDefaultPossibilitiesBuilder(this.canUseSuiteMethod)).safeRunnerForClass(this.fTestClass);
/*    */         }
/*    */       } 
/*    */     }
/* 37 */     return this.runner;
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\requests\ClassRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */