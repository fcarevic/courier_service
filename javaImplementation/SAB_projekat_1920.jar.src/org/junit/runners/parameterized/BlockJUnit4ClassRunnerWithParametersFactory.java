/*    */ package org.junit.runners.parameterized;
/*    */ 
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runners.model.InitializationError;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockJUnit4ClassRunnerWithParametersFactory
/*    */   implements ParametersRunnerFactory
/*    */ {
/*    */   public Runner createRunnerForTestWithParameters(TestWithParameters test) throws InitializationError {
/* 16 */     return (Runner)new BlockJUnit4ClassRunnerWithParameters(test);
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\runners\parameterized\BlockJUnit4ClassRunnerWithParametersFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */