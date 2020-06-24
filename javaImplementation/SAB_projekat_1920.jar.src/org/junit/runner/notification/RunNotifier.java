/*     */ package org.junit.runner.notification;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.junit.runner.Description;
/*     */ import org.junit.runner.Result;
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
/*     */ public class RunNotifier
/*     */ {
/*  22 */   private final List<RunListener> listeners = new CopyOnWriteArrayList<RunListener>();
/*     */ 
/*     */   
/*     */   private volatile boolean pleaseStop = false;
/*     */ 
/*     */   
/*     */   public void addListener(RunListener listener) {
/*  29 */     if (listener == null) {
/*  30 */       throw new NullPointerException("Cannot add a null listener");
/*     */     }
/*  32 */     this.listeners.add(wrapIfNotThreadSafe(listener));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListener(RunListener listener) {
/*  39 */     if (listener == null) {
/*  40 */       throw new NullPointerException("Cannot remove a null listener");
/*     */     }
/*  42 */     this.listeners.remove(wrapIfNotThreadSafe(listener));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RunListener wrapIfNotThreadSafe(RunListener listener) {
/*  50 */     return listener.getClass().isAnnotationPresent((Class)RunListener.ThreadSafe.class) ? listener : new SynchronizedRunListener(listener, this);
/*     */   }
/*     */ 
/*     */   
/*     */   private abstract class SafeNotifier
/*     */   {
/*     */     private final List<RunListener> currentListeners;
/*     */     
/*     */     SafeNotifier() {
/*  59 */       this(RunNotifier.this.listeners);
/*     */     }
/*     */     
/*     */     SafeNotifier(List<RunListener> currentListeners) {
/*  63 */       this.currentListeners = currentListeners;
/*     */     }
/*     */     
/*     */     void run() {
/*  67 */       int capacity = this.currentListeners.size();
/*  68 */       ArrayList<RunListener> safeListeners = new ArrayList<RunListener>(capacity);
/*  69 */       ArrayList<Failure> failures = new ArrayList<Failure>(capacity);
/*  70 */       for (RunListener listener : this.currentListeners) {
/*     */         try {
/*  72 */           notifyListener(listener);
/*  73 */           safeListeners.add(listener);
/*  74 */         } catch (Exception e) {
/*  75 */           failures.add(new Failure(Description.TEST_MECHANISM, e));
/*     */         } 
/*     */       } 
/*  78 */       RunNotifier.this.fireTestFailures(safeListeners, failures);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract void notifyListener(RunListener param1RunListener) throws Exception;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fireTestRunStarted(final Description description) {
/*  88 */     (new SafeNotifier()
/*     */       {
/*     */         protected void notifyListener(RunListener each) throws Exception {
/*  91 */           each.testRunStarted(description);
/*     */         }
/*     */       }).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireTestRunFinished(final Result result) {
/* 100 */     (new SafeNotifier()
/*     */       {
/*     */         protected void notifyListener(RunListener each) throws Exception {
/* 103 */           each.testRunFinished(result);
/*     */         }
/*     */       }).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireTestStarted(final Description description) throws StoppedByUserException {
/* 115 */     if (this.pleaseStop) {
/* 116 */       throw new StoppedByUserException();
/*     */     }
/* 118 */     (new SafeNotifier()
/*     */       {
/*     */         protected void notifyListener(RunListener each) throws Exception {
/* 121 */           each.testStarted(description);
/*     */         }
/*     */       }).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireTestFailure(Failure failure) {
/* 132 */     fireTestFailures(this.listeners, Arrays.asList(new Failure[] { failure }));
/*     */   }
/*     */ 
/*     */   
/*     */   private void fireTestFailures(List<RunListener> listeners, final List<Failure> failures) {
/* 137 */     if (!failures.isEmpty()) {
/* 138 */       (new SafeNotifier(listeners)
/*     */         {
/*     */           protected void notifyListener(RunListener listener) throws Exception {
/* 141 */             for (Failure each : failures) {
/* 142 */               listener.testFailure(each);
/*     */             }
/*     */           }
/*     */         }).run();
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
/*     */   public void fireTestAssumptionFailed(final Failure failure) {
/* 157 */     (new SafeNotifier()
/*     */       {
/*     */         protected void notifyListener(RunListener each) throws Exception {
/* 160 */           each.testAssumptionFailure(failure);
/*     */         }
/*     */       }).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireTestIgnored(final Description description) {
/* 171 */     (new SafeNotifier()
/*     */       {
/*     */         protected void notifyListener(RunListener each) throws Exception {
/* 174 */           each.testIgnored(description);
/*     */         }
/*     */       }).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireTestFinished(final Description description) {
/* 187 */     (new SafeNotifier()
/*     */       {
/*     */         protected void notifyListener(RunListener each) throws Exception {
/* 190 */           each.testFinished(description);
/*     */         }
/*     */       }).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pleaseStop() {
/* 202 */     this.pleaseStop = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFirstListener(RunListener listener) {
/* 209 */     if (listener == null) {
/* 210 */       throw new NullPointerException("Cannot add a null listener");
/*     */     }
/* 212 */     this.listeners.add(0, wrapIfNotThreadSafe(listener));
/*     */   }
/*     */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\runner\notification\RunNotifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */