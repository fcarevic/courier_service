/*     */ package org.junit.internal.runners;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import org.junit.internal.AssumptionViolatedException;
/*     */ import org.junit.runner.Description;
/*     */ import org.junit.runner.notification.Failure;
/*     */ import org.junit.runner.notification.RunNotifier;
/*     */ import org.junit.runners.model.TestTimedOutException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class MethodRoadie
/*     */ {
/*     */   private final Object test;
/*     */   private final RunNotifier notifier;
/*     */   private final Description description;
/*     */   private TestMethod testMethod;
/*     */   
/*     */   public MethodRoadie(Object test, TestMethod method, RunNotifier notifier, Description description) {
/*  33 */     this.test = test;
/*  34 */     this.notifier = notifier;
/*  35 */     this.description = description;
/*  36 */     this.testMethod = method;
/*     */   }
/*     */   
/*     */   public void run() {
/*  40 */     if (this.testMethod.isIgnored()) {
/*  41 */       this.notifier.fireTestIgnored(this.description);
/*     */       return;
/*     */     } 
/*  44 */     this.notifier.fireTestStarted(this.description);
/*     */     try {
/*  46 */       long timeout = this.testMethod.getTimeout();
/*  47 */       if (timeout > 0L) {
/*  48 */         runWithTimeout(timeout);
/*     */       } else {
/*  50 */         runTest();
/*     */       } 
/*     */     } finally {
/*  53 */       this.notifier.fireTestFinished(this.description);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void runWithTimeout(final long timeout) {
/*  58 */     runBeforesThenTestThenAfters(new Runnable()
/*     */         {
/*     */           public void run() {
/*  61 */             ExecutorService service = Executors.newSingleThreadExecutor();
/*  62 */             Callable<Object> callable = new Callable() {
/*     */                 public Object call() throws Exception {
/*  64 */                   MethodRoadie.this.runTestMethod();
/*  65 */                   return null;
/*     */                 }
/*     */               };
/*  68 */             Future<Object> result = service.submit(callable);
/*  69 */             service.shutdown();
/*     */             try {
/*  71 */               boolean terminated = service.awaitTermination(timeout, TimeUnit.MILLISECONDS);
/*     */               
/*  73 */               if (!terminated) {
/*  74 */                 service.shutdownNow();
/*     */               }
/*  76 */               result.get(0L, TimeUnit.MILLISECONDS);
/*  77 */             } catch (TimeoutException e) {
/*  78 */               MethodRoadie.this.addFailure((Throwable)new TestTimedOutException(timeout, TimeUnit.MILLISECONDS));
/*  79 */             } catch (Exception e) {
/*  80 */               MethodRoadie.this.addFailure(e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void runTest() {
/*  87 */     runBeforesThenTestThenAfters(new Runnable() {
/*     */           public void run() {
/*  89 */             MethodRoadie.this.runTestMethod();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void runBeforesThenTestThenAfters(Runnable test) {
/*     */     
/*  96 */     try { runBefores();
/*  97 */       test.run(); }
/*  98 */     catch (FailedBefore e) {  }
/*  99 */     catch (Exception e)
/* 100 */     { throw new RuntimeException("test should never throw an exception to this level"); }
/*     */     finally
/* 102 */     { runAfters(); }
/*     */   
/*     */   }
/*     */   
/*     */   protected void runTestMethod() {
/*     */     try {
/* 108 */       this.testMethod.invoke(this.test);
/* 109 */       if (this.testMethod.expectsException()) {
/* 110 */         addFailure(new AssertionError("Expected exception: " + this.testMethod.getExpectedException().getName()));
/*     */       }
/* 112 */     } catch (InvocationTargetException e) {
/* 113 */       Throwable actual = e.getTargetException();
/* 114 */       if (actual instanceof AssumptionViolatedException)
/*     */         return; 
/* 116 */       if (!this.testMethod.expectsException()) {
/* 117 */         addFailure(actual);
/* 118 */       } else if (this.testMethod.isUnexpected(actual)) {
/* 119 */         String message = "Unexpected exception, expected<" + this.testMethod.getExpectedException().getName() + "> but was<" + actual.getClass().getName() + ">";
/*     */         
/* 121 */         addFailure(new Exception(message, actual));
/*     */       } 
/* 123 */     } catch (Throwable e) {
/* 124 */       addFailure(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void runBefores() throws FailedBefore {
/*     */     try {
/*     */       try {
/* 131 */         List<Method> befores = this.testMethod.getBefores();
/* 132 */         for (Method before : befores) {
/* 133 */           before.invoke(this.test, new Object[0]);
/*     */         }
/* 135 */       } catch (InvocationTargetException e) {
/* 136 */         throw e.getTargetException();
/*     */       } 
/* 138 */     } catch (AssumptionViolatedException e) {
/* 139 */       throw new FailedBefore();
/* 140 */     } catch (Throwable e) {
/* 141 */       addFailure(e);
/* 142 */       throw new FailedBefore();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void runAfters() {
/* 147 */     List<Method> afters = this.testMethod.getAfters();
/* 148 */     for (Method after : afters) {
/*     */       try {
/* 150 */         after.invoke(this.test, new Object[0]);
/* 151 */       } catch (InvocationTargetException e) {
/* 152 */         addFailure(e.getTargetException());
/* 153 */       } catch (Throwable e) {
/* 154 */         addFailure(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void addFailure(Throwable e) {
/* 160 */     this.notifier.fireTestFailure(new Failure(this.description, e));
/*     */   }
/*     */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\runners\MethodRoadie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */