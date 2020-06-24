/*     */ package org.junit.runner;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
/*     */ import org.junit.internal.requests.ClassRequest;
/*     */ import org.junit.internal.requests.FilterRequest;
/*     */ import org.junit.internal.requests.SortingRequest;
/*     */ import org.junit.internal.runners.ErrorReportingRunner;
/*     */ import org.junit.runner.manipulation.Filter;
/*     */ import org.junit.runners.model.InitializationError;
/*     */ import org.junit.runners.model.RunnerBuilder;
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
/*     */ public abstract class Request
/*     */ {
/*     */   public static Request method(Class<?> clazz, String methodName) {
/*  38 */     Description method = Description.createTestDescription(clazz, methodName);
/*  39 */     return aClass(clazz).filterWith(method);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Request aClass(Class<?> clazz) {
/*  50 */     return (Request)new ClassRequest(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Request classWithoutSuiteMethod(Class<?> clazz) {
/*  61 */     return (Request)new ClassRequest(clazz, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Request classes(Computer computer, Class<?>... classes) {
/*     */     try {
/*  74 */       AllDefaultPossibilitiesBuilder builder = new AllDefaultPossibilitiesBuilder(true);
/*  75 */       Runner suite = computer.getSuite((RunnerBuilder)builder, classes);
/*  76 */       return runner(suite);
/*  77 */     } catch (InitializationError e) {
/*  78 */       throw new RuntimeException("Bug in saff's brain: Suite constructor, called as above, should always complete");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Request classes(Class<?>... classes) {
/*  91 */     return classes(JUnitCore.defaultComputer(), classes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Request errorReport(Class<?> klass, Throwable cause) {
/* 100 */     return runner((Runner)new ErrorReportingRunner(klass, cause));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Request runner(final Runner runner) {
/* 108 */     return new Request()
/*     */       {
/*     */         public Runner getRunner() {
/* 111 */           return runner;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Runner getRunner();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Request filterWith(Filter filter) {
/* 131 */     return (Request)new FilterRequest(this, filter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Request filterWith(Description desiredDescription) {
/* 142 */     return filterWith(Filter.matchMethodDescription(desiredDescription));
/*     */   }
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
/*     */   public Request sortWith(Comparator<Description> comparator) {
/* 168 */     return (Request)new SortingRequest(this, comparator);
/*     */   }
/*     */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\runner\Request.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */