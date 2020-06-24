/*    */ package org.junit.runner;
/*    */ 
/*    */ import org.junit.runner.manipulation.Filter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface FilterFactory
/*    */ {
/*    */   Filter createFilter(FilterFactoryParams paramFilterFactoryParams) throws FilterNotCreatedException;
/*    */   
/*    */   public static class FilterNotCreatedException
/*    */     extends Exception
/*    */   {
/*    */     public FilterNotCreatedException(Exception exception) {
/* 21 */       super(exception.getMessage(), exception);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\runner\FilterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */