/*    */ package org.junit.experimental.theories.internal;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.junit.experimental.theories.ParameterSignature;
/*    */ import org.junit.experimental.theories.ParameterSupplier;
/*    */ import org.junit.experimental.theories.PotentialAssignment;
/*    */ 
/*    */ public class EnumSupplier
/*    */   extends ParameterSupplier
/*    */ {
/*    */   private Class<?> enumType;
/*    */   
/*    */   public EnumSupplier(Class<?> enumType) {
/* 15 */     this.enumType = enumType;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<PotentialAssignment> getValueSources(ParameterSignature sig) {
/* 20 */     Object[] enumValues = this.enumType.getEnumConstants();
/*    */     
/* 22 */     List<PotentialAssignment> assignments = new ArrayList<PotentialAssignment>();
/* 23 */     for (Object value : enumValues) {
/* 24 */       assignments.add(PotentialAssignment.forValue(value.toString(), value));
/*    */     }
/*    */     
/* 27 */     return assignments;
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\experimental\theories\internal\EnumSupplier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */