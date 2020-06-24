/*     */ package org.junit.experimental.theories;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.junit.Assert;
/*     */ import org.junit.Assume;
/*     */ import org.junit.experimental.theories.internal.Assignments;
/*     */ import org.junit.experimental.theories.internal.ParameterizedAssertionError;
/*     */ import org.junit.internal.AssumptionViolatedException;
/*     */ import org.junit.runners.BlockJUnit4ClassRunner;
/*     */ import org.junit.runners.model.FrameworkMethod;
/*     */ import org.junit.runners.model.InitializationError;
/*     */ import org.junit.runners.model.Statement;
/*     */ import org.junit.runners.model.TestClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Theories
/*     */   extends BlockJUnit4ClassRunner
/*     */ {
/*     */   public Theories(Class<?> klass) throws InitializationError {
/*  73 */     super(klass);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void collectInitializationErrors(List<Throwable> errors) {
/*  78 */     super.collectInitializationErrors(errors);
/*  79 */     validateDataPointFields(errors);
/*  80 */     validateDataPointMethods(errors);
/*     */   }
/*     */   
/*     */   private void validateDataPointFields(List<Throwable> errors) {
/*  84 */     Field[] fields = getTestClass().getJavaClass().getDeclaredFields();
/*     */     
/*  86 */     for (Field field : fields) {
/*  87 */       if (field.getAnnotation(DataPoint.class) != null || field.getAnnotation(DataPoints.class) != null) {
/*     */ 
/*     */         
/*  90 */         if (!Modifier.isStatic(field.getModifiers())) {
/*  91 */           errors.add(new Error("DataPoint field " + field.getName() + " must be static"));
/*     */         }
/*  93 */         if (!Modifier.isPublic(field.getModifiers()))
/*  94 */           errors.add(new Error("DataPoint field " + field.getName() + " must be public")); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void validateDataPointMethods(List<Throwable> errors) {
/* 100 */     Method[] methods = getTestClass().getJavaClass().getDeclaredMethods();
/*     */     
/* 102 */     for (Method method : methods) {
/* 103 */       if (method.getAnnotation(DataPoint.class) != null || method.getAnnotation(DataPoints.class) != null) {
/*     */ 
/*     */         
/* 106 */         if (!Modifier.isStatic(method.getModifiers())) {
/* 107 */           errors.add(new Error("DataPoint method " + method.getName() + " must be static"));
/*     */         }
/* 109 */         if (!Modifier.isPublic(method.getModifiers())) {
/* 110 */           errors.add(new Error("DataPoint method " + method.getName() + " must be public"));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void validateConstructor(List<Throwable> errors) {
/* 117 */     validateOnlyOneConstructor(errors);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void validateTestMethods(List<Throwable> errors) {
/* 122 */     for (FrameworkMethod each : computeTestMethods()) {
/* 123 */       if (each.getAnnotation(Theory.class) != null) {
/* 124 */         each.validatePublicVoid(false, errors);
/* 125 */         each.validateNoTypeParametersOnArgs(errors);
/*     */       } else {
/* 127 */         each.validatePublicVoidNoArg(false, errors);
/*     */       } 
/*     */       
/* 130 */       for (ParameterSignature signature : ParameterSignature.signatures(each.getMethod())) {
/* 131 */         ParametersSuppliedBy annotation = signature.<ParametersSuppliedBy>findDeepAnnotation(ParametersSuppliedBy.class);
/* 132 */         if (annotation != null) {
/* 133 */           validateParameterSupplier(annotation.value(), errors);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void validateParameterSupplier(Class<? extends ParameterSupplier> supplierClass, List<Throwable> errors) {
/* 140 */     Constructor[] arrayOfConstructor = (Constructor[])supplierClass.getConstructors();
/*     */     
/* 142 */     if (arrayOfConstructor.length != 1) {
/* 143 */       errors.add(new Error("ParameterSupplier " + supplierClass.getName() + " must have only one constructor (either empty or taking only a TestClass)"));
/*     */     } else {
/*     */       
/* 146 */       Class<?>[] paramTypes = arrayOfConstructor[0].getParameterTypes();
/* 147 */       if (paramTypes.length != 0 && !paramTypes[0].equals(TestClass.class)) {
/* 148 */         errors.add(new Error("ParameterSupplier " + supplierClass.getName() + " constructor must take either nothing or a single TestClass instance"));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<FrameworkMethod> computeTestMethods() {
/* 156 */     List<FrameworkMethod> testMethods = new ArrayList<FrameworkMethod>(super.computeTestMethods());
/* 157 */     List<FrameworkMethod> theoryMethods = getTestClass().getAnnotatedMethods(Theory.class);
/* 158 */     testMethods.removeAll(theoryMethods);
/* 159 */     testMethods.addAll(theoryMethods);
/* 160 */     return testMethods;
/*     */   }
/*     */ 
/*     */   
/*     */   public Statement methodBlock(FrameworkMethod method) {
/* 165 */     return new TheoryAnchor(method, getTestClass());
/*     */   }
/*     */   
/*     */   public static class TheoryAnchor extends Statement {
/* 169 */     private int successes = 0;
/*     */     
/*     */     private final FrameworkMethod testMethod;
/*     */     
/*     */     private final TestClass testClass;
/* 174 */     private List<AssumptionViolatedException> fInvalidParameters = new ArrayList<AssumptionViolatedException>();
/*     */     
/*     */     public TheoryAnchor(FrameworkMethod testMethod, TestClass testClass) {
/* 177 */       this.testMethod = testMethod;
/* 178 */       this.testClass = testClass;
/*     */     }
/*     */     
/*     */     private TestClass getTestClass() {
/* 182 */       return this.testClass;
/*     */     }
/*     */ 
/*     */     
/*     */     public void evaluate() throws Throwable {
/* 187 */       runWithAssignment(Assignments.allUnassigned(this.testMethod.getMethod(), getTestClass()));
/*     */ 
/*     */ 
/*     */       
/* 191 */       boolean hasTheoryAnnotation = (this.testMethod.getAnnotation(Theory.class) != null);
/* 192 */       if (this.successes == 0 && hasTheoryAnnotation) {
/* 193 */         Assert.fail("Never found parameters that satisfied method assumptions.  Violated assumptions: " + this.fInvalidParameters);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void runWithAssignment(Assignments parameterAssignment) throws Throwable {
/* 201 */       if (!parameterAssignment.isComplete()) {
/* 202 */         runWithIncompleteAssignment(parameterAssignment);
/*     */       } else {
/* 204 */         runWithCompleteAssignment(parameterAssignment);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void runWithIncompleteAssignment(Assignments incomplete) throws Throwable {
/* 211 */       for (PotentialAssignment source : incomplete.potentialsForNextUnassigned()) {
/* 212 */         runWithAssignment(incomplete.assignNext(source));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected void runWithCompleteAssignment(final Assignments complete) throws Throwable {
/* 218 */       (new BlockJUnit4ClassRunner(getTestClass().getJavaClass())
/*     */         {
/*     */           protected void collectInitializationErrors(List<Throwable> errors) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public Statement methodBlock(FrameworkMethod method) {
/* 227 */             final Statement statement = super.methodBlock(method);
/* 228 */             return new Statement()
/*     */               {
/*     */                 public void evaluate() throws Throwable {
/*     */                   try {
/* 232 */                     statement.evaluate();
/* 233 */                     Theories.TheoryAnchor.this.handleDataPointSuccess();
/* 234 */                   } catch (AssumptionViolatedException e) {
/* 235 */                     Theories.TheoryAnchor.this.handleAssumptionViolation(e);
/* 236 */                   } catch (Throwable e) {
/* 237 */                     Theories.TheoryAnchor.this.reportParameterizedError(e, complete.getArgumentStrings(Theories.TheoryAnchor.this.nullsOk()));
/*     */                   } 
/*     */                 }
/*     */               };
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           protected Statement methodInvoker(FrameworkMethod method, Object test) {
/* 247 */             return Theories.TheoryAnchor.this.methodCompletesWithParameters(method, complete, test);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object createTest() throws Exception {
/* 252 */             Object[] params = complete.getConstructorArguments();
/*     */             
/* 254 */             if (!Theories.TheoryAnchor.this.nullsOk()) {
/* 255 */               Assume.assumeNotNull(params);
/*     */             }
/*     */             
/* 258 */             return getTestClass().getOnlyConstructor().newInstance(params);
/*     */           }
/*     */         }).methodBlock(this.testMethod).evaluate();
/*     */     }
/*     */ 
/*     */     
/*     */     private Statement methodCompletesWithParameters(final FrameworkMethod method, final Assignments complete, final Object freshInstance) {
/* 265 */       return new Statement()
/*     */         {
/*     */           public void evaluate() throws Throwable {
/* 268 */             Object[] values = complete.getMethodArguments();
/*     */             
/* 270 */             if (!Theories.TheoryAnchor.this.nullsOk()) {
/* 271 */               Assume.assumeNotNull(values);
/*     */             }
/*     */             
/* 274 */             method.invokeExplosively(freshInstance, values);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     protected void handleAssumptionViolation(AssumptionViolatedException e) {
/* 280 */       this.fInvalidParameters.add(e);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void reportParameterizedError(Throwable e, Object... params) throws Throwable {
/* 285 */       if (params.length == 0) {
/* 286 */         throw e;
/*     */       }
/* 288 */       throw new ParameterizedAssertionError(e, this.testMethod.getName(), params);
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean nullsOk() {
/* 293 */       Theory annotation = this.testMethod.getMethod().<Theory>getAnnotation(Theory.class);
/*     */       
/* 295 */       if (annotation == null) {
/* 296 */         return false;
/*     */       }
/* 298 */       return annotation.nullsAccepted();
/*     */     }
/*     */     
/*     */     protected void handleDataPointSuccess() {
/* 302 */       this.successes++;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\experimental\theories\Theories.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */