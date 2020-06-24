/*    */ package org.junit.internal;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
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
/*    */ public class ArrayComparisonFailure
/*    */   extends AssertionError
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 22 */   private final List<Integer> fIndices = new ArrayList<Integer>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final String fMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArrayComparisonFailure(String message, AssertionError cause, int index) {
/* 34 */     this.fMessage = message;
/* 35 */     initCause(cause);
/* 36 */     addDimension(index);
/*    */   }
/*    */   
/*    */   public void addDimension(int index) {
/* 40 */     this.fIndices.add(0, Integer.valueOf(index));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 45 */     StringBuilder sb = new StringBuilder();
/* 46 */     if (this.fMessage != null) {
/* 47 */       sb.append(this.fMessage);
/*    */     }
/* 49 */     sb.append("arrays first differed at element ");
/* 50 */     for (Iterator<Integer> i$ = this.fIndices.iterator(); i$.hasNext(); ) { int each = ((Integer)i$.next()).intValue();
/* 51 */       sb.append("[");
/* 52 */       sb.append(each);
/* 53 */       sb.append("]"); }
/*    */     
/* 55 */     sb.append("; ");
/* 56 */     sb.append(getCause().getMessage());
/* 57 */     return sb.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return getMessage();
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\ArrayComparisonFailure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */