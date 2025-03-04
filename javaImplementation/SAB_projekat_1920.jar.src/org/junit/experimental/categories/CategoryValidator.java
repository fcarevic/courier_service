/*    */ package org.junit.experimental.categories;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.junit.After;
/*    */ import org.junit.AfterClass;
/*    */ import org.junit.Before;
/*    */ import org.junit.BeforeClass;
/*    */ import org.junit.runners.model.FrameworkMethod;
/*    */ import org.junit.validator.AnnotationValidator;
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
/*    */ public final class CategoryValidator
/*    */   extends AnnotationValidator
/*    */ {
/* 30 */   private static final Set<Class<? extends Annotation>> INCOMPATIBLE_ANNOTATIONS = Collections.unmodifiableSet(new HashSet<Class<? extends Annotation>>(Arrays.asList((Class<? extends Annotation>[])new Class[] { BeforeClass.class, AfterClass.class, Before.class, After.class })));
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
/*    */   
/*    */   public List<Exception> validateAnnotatedMethod(FrameworkMethod method) {
/* 45 */     List<Exception> errors = new ArrayList<Exception>();
/* 46 */     Annotation[] annotations = method.getAnnotations();
/* 47 */     for (Annotation annotation : annotations) {
/* 48 */       for (Class<?> clazz : INCOMPATIBLE_ANNOTATIONS) {
/* 49 */         if (annotation.annotationType().isAssignableFrom(clazz)) {
/* 50 */           addErrorMessage(errors, clazz);
/*    */         }
/*    */       } 
/*    */     } 
/* 54 */     return Collections.unmodifiableList(errors);
/*    */   }
/*    */   
/*    */   private void addErrorMessage(List<Exception> errors, Class<?> clazz) {
/* 58 */     String message = String.format("@%s can not be combined with @Category", new Object[] { clazz.getSimpleName() });
/*    */     
/* 60 */     errors.add(new Exception(message));
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\experimental\categories\CategoryValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */