/*    */ package org.junit.internal.runners;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.List;
/*    */ import org.junit.internal.AssumptionViolatedException;
/*    */ import org.junit.runner.Description;
/*    */ import org.junit.runner.notification.Failure;
/*    */ import org.junit.runner.notification.RunNotifier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class ClassRoadie
/*    */ {
/*    */   private RunNotifier notifier;
/*    */   private TestClass testClass;
/*    */   private Description description;
/*    */   private final Runnable runnable;
/*    */   
/*    */   public ClassRoadie(RunNotifier notifier, TestClass testClass, Description description, Runnable runnable) {
/* 27 */     this.notifier = notifier;
/* 28 */     this.testClass = testClass;
/* 29 */     this.description = description;
/* 30 */     this.runnable = runnable;
/*    */   }
/*    */   
/*    */   protected void runUnprotected() {
/* 34 */     this.runnable.run();
/*    */   }
/*    */   
/*    */   protected void addFailure(Throwable targetException) {
/* 38 */     this.notifier.fireTestFailure(new Failure(this.description, targetException));
/*    */   }
/*    */   
/*    */   public void runProtected() {
/*    */     
/* 43 */     try { runBefores();
/* 44 */       runUnprotected(); }
/* 45 */     catch (FailedBefore e) {  }
/*    */     finally
/* 47 */     { runAfters(); }
/*    */   
/*    */   }
/*    */   
/*    */   private void runBefores() throws FailedBefore {
/*    */     try {
/*    */       try {
/* 54 */         List<Method> befores = this.testClass.getBefores();
/* 55 */         for (Method before : befores) {
/* 56 */           before.invoke(null, new Object[0]);
/*    */         }
/* 58 */       } catch (InvocationTargetException e) {
/* 59 */         throw e.getTargetException();
/*    */       } 
/* 61 */     } catch (AssumptionViolatedException e) {
/* 62 */       throw new FailedBefore();
/* 63 */     } catch (Throwable e) {
/* 64 */       addFailure(e);
/* 65 */       throw new FailedBefore();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void runAfters() {
/* 70 */     List<Method> afters = this.testClass.getAfters();
/* 71 */     for (Method after : afters) {
/*    */       try {
/* 73 */         after.invoke(null, new Object[0]);
/* 74 */       } catch (InvocationTargetException e) {
/* 75 */         addFailure(e.getTargetException());
/* 76 */       } catch (Throwable e) {
/* 77 */         addFailure(e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\runners\ClassRoadie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */