/*    */ package org.junit.runners.model;
/*    */ 
/*    */ import java.util.concurrent.TimeUnit;
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
/*    */ public class TestTimedOutException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 31935685163547539L;
/*    */   private final TimeUnit timeUnit;
/*    */   private final long timeout;
/*    */   
/*    */   public TestTimedOutException(long timeout, TimeUnit timeUnit) {
/* 25 */     super(String.format("test timed out after %d %s", new Object[] { Long.valueOf(timeout), timeUnit.name().toLowerCase() }));
/*    */     
/* 27 */     this.timeUnit = timeUnit;
/* 28 */     this.timeout = timeout;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getTimeout() {
/* 35 */     return this.timeout;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TimeUnit getTimeUnit() {
/* 42 */     return this.timeUnit;
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\runners\model\TestTimedOutException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */