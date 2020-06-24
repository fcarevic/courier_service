/*     */ package org.junit.internal;
/*     */ 
/*     */ import org.hamcrest.Description;
/*     */ import org.hamcrest.Matcher;
/*     */ import org.hamcrest.SelfDescribing;
/*     */ import org.hamcrest.StringDescription;
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
/*     */ public class AssumptionViolatedException
/*     */   extends RuntimeException
/*     */   implements SelfDescribing
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*     */   private final String fAssumption;
/*     */   private final boolean fValueMatcher;
/*     */   private final Object fValue;
/*     */   private final Matcher<?> fMatcher;
/*     */   
/*     */   @Deprecated
/*     */   public AssumptionViolatedException(String assumption, boolean hasValue, Object value, Matcher<?> matcher) {
/*  33 */     this.fAssumption = assumption;
/*  34 */     this.fValue = value;
/*  35 */     this.fMatcher = matcher;
/*  36 */     this.fValueMatcher = hasValue;
/*     */     
/*  38 */     if (value instanceof Throwable) {
/*  39 */       initCause((Throwable)value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public AssumptionViolatedException(Object value, Matcher<?> matcher) {
/*  51 */     this((String)null, true, value, matcher);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public AssumptionViolatedException(String assumption, Object value, Matcher<?> matcher) {
/*  62 */     this(assumption, true, value, matcher);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public AssumptionViolatedException(String assumption) {
/*  72 */     this(assumption, false, (Object)null, (Matcher<?>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public AssumptionViolatedException(String assumption, Throwable e) {
/*  82 */     this(assumption, false, (Object)null, (Matcher<?>)null);
/*  83 */     initCause(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  88 */     return StringDescription.asString(this);
/*     */   }
/*     */   
/*     */   public void describeTo(Description description) {
/*  92 */     if (this.fAssumption != null) {
/*  93 */       description.appendText(this.fAssumption);
/*     */     }
/*     */     
/*  96 */     if (this.fValueMatcher) {
/*     */       
/*  98 */       if (this.fAssumption != null) {
/*  99 */         description.appendText(": ");
/*     */       }
/*     */       
/* 102 */       description.appendText("got: ");
/* 103 */       description.appendValue(this.fValue);
/*     */       
/* 105 */       if (this.fMatcher != null) {
/* 106 */         description.appendText(", expected: ");
/* 107 */         description.appendDescriptionOf((SelfDescribing)this.fMatcher);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\AssumptionViolatedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */