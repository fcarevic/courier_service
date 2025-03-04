/*    */ package junit.extensions;
/*    */ 
/*    */ import junit.framework.Assert;
/*    */ import junit.framework.Test;
/*    */ import junit.framework.TestResult;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TestDecorator
/*    */   extends Assert
/*    */   implements Test
/*    */ {
/*    */   protected Test fTest;
/*    */   
/*    */   public TestDecorator(Test test) {
/* 16 */     this.fTest = test;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void basicRun(TestResult result) {
/* 23 */     this.fTest.run(result);
/*    */   }
/*    */   
/*    */   public int countTestCases() {
/* 27 */     return this.fTest.countTestCases();
/*    */   }
/*    */   
/*    */   public void run(TestResult result) {
/* 31 */     basicRun(result);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 36 */     return this.fTest.toString();
/*    */   }
/*    */   
/*    */   public Test getTest() {
/* 40 */     return this.fTest;
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\junit\extensions\TestDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */