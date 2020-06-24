/*    */ package org.junit.internal;
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
/*    */ public class Classes
/*    */ {
/*    */   public static Class<?> getClass(String className) throws ClassNotFoundException {
/* 16 */     return Class.forName(className, true, Thread.currentThread().getContextClassLoader());
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\Classes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */