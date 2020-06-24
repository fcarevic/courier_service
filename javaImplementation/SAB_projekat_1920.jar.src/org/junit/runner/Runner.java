/*    */ package org.junit.runner;
/*    */ 
/*    */ import org.junit.runner.notification.RunNotifier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Runner
/*    */   implements Describable
/*    */ {
/*    */   public abstract Description getDescription();
/*    */   
/*    */   public abstract void run(RunNotifier paramRunNotifier);
/*    */   
/*    */   public int testCount() {
/* 41 */     return getDescription().testCount();
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\runner\Runner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */