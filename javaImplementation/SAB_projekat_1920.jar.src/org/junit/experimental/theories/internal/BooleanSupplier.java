/*    */ package org.junit.experimental.theories.internal;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.junit.experimental.theories.ParameterSignature;
/*    */ import org.junit.experimental.theories.ParameterSupplier;
/*    */ import org.junit.experimental.theories.PotentialAssignment;
/*    */ 
/*    */ 
/*    */ public class BooleanSupplier
/*    */   extends ParameterSupplier
/*    */ {
/*    */   public List<PotentialAssignment> getValueSources(ParameterSignature sig) {
/* 14 */     return Arrays.asList(new PotentialAssignment[] { PotentialAssignment.forValue("true", Boolean.valueOf(true)), PotentialAssignment.forValue("false", Boolean.valueOf(false)) });
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\experimental\theories\internal\BooleanSupplier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */