/*    */ package junit.framework;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
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
/*    */ public class TestFailure
/*    */ {
/*    */   protected Test fFailedTest;
/*    */   protected Throwable fThrownException;
/*    */   
/*    */   public TestFailure(Test failedTest, Throwable thrownException) {
/* 21 */     this.fFailedTest = failedTest;
/* 22 */     this.fThrownException = thrownException;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Test failedTest() {
/* 29 */     return this.fFailedTest;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable thrownException() {
/* 36 */     return this.fThrownException;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 44 */     return this.fFailedTest + ": " + this.fThrownException.getMessage();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String trace() {
/* 52 */     StringWriter stringWriter = new StringWriter();
/* 53 */     PrintWriter writer = new PrintWriter(stringWriter);
/* 54 */     thrownException().printStackTrace(writer);
/* 55 */     return stringWriter.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String exceptionMessage() {
/* 62 */     return thrownException().getMessage();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFailure() {
/* 71 */     return thrownException() instanceof AssertionFailedError;
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\junit\framework\TestFailure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */