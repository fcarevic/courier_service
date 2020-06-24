/*    */ package org.junit.runners.model;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
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
/*    */ public class InitializationError
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final List<Throwable> fErrors;
/*    */   
/*    */   public InitializationError(List<Throwable> errors) {
/* 26 */     this.fErrors = errors;
/*    */   }
/*    */   
/*    */   public InitializationError(Throwable error) {
/* 30 */     this(Arrays.asList(new Throwable[] { error }));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InitializationError(String string) {
/* 38 */     this(new Exception(string));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Throwable> getCauses() {
/* 45 */     return this.fErrors;
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\runners\model\InitializationError.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */