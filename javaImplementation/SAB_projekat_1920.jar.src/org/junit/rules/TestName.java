/*    */ package org.junit.rules;
/*    */ 
/*    */ import org.junit.runner.Description;
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
/*    */ public class TestName
/*    */   extends TestWatcher
/*    */ {
/*    */   private String name;
/*    */   
/*    */   protected void starting(Description d) {
/* 32 */     this.name = d.getMethodName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMethodName() {
/* 39 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\rules\TestName.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */