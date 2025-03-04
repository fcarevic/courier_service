/*     */ package org.junit.runners.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.junit.internal.runners.ErrorReportingRunner;
/*     */ import org.junit.runner.Runner;
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
/*     */ public abstract class RunnerBuilder
/*     */ {
/*  40 */   private final Set<Class<?>> parents = new HashSet<Class<?>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Runner runnerForClass(Class<?> paramClass) throws Throwable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Runner safeRunnerForClass(Class<?> testClass) {
/*     */     try {
/*  59 */       return runnerForClass(testClass);
/*  60 */     } catch (Throwable e) {
/*  61 */       return (Runner)new ErrorReportingRunner(testClass, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   Class<?> addParent(Class<?> parent) throws InitializationError {
/*  66 */     if (!this.parents.add(parent)) {
/*  67 */       throw new InitializationError(String.format("class '%s' (possibly indirectly) contains itself as a SuiteClass", new Object[] { parent.getName() }));
/*     */     }
/*  69 */     return parent;
/*     */   }
/*     */   
/*     */   void removeParent(Class<?> klass) {
/*  73 */     this.parents.remove(klass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Runner> runners(Class<?> parent, Class<?>[] children) throws InitializationError {
/*  84 */     addParent(parent);
/*     */     
/*     */     try {
/*  87 */       return runners(children);
/*     */     } finally {
/*  89 */       removeParent(parent);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Runner> runners(Class<?> parent, List<Class<?>> children) throws InitializationError {
/*  95 */     return runners(parent, (Class[])children.<Class<?>[]>toArray((Class<?>[][])new Class[0]));
/*     */   }
/*     */   
/*     */   private List<Runner> runners(Class<?>[] children) {
/*  99 */     ArrayList<Runner> runners = new ArrayList<Runner>();
/* 100 */     for (Class<?> each : children) {
/* 101 */       Runner childRunner = safeRunnerForClass(each);
/* 102 */       if (childRunner != null) {
/* 103 */         runners.add(childRunner);
/*     */       }
/*     */     } 
/* 106 */     return runners;
/*     */   }
/*     */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\runners\model\RunnerBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */