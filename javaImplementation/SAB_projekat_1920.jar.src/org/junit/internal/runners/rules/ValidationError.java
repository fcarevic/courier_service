/*   */ package org.junit.internal.runners.rules;
/*   */ 
/*   */ import java.lang.annotation.Annotation;
/*   */ import org.junit.runners.model.FrameworkMember;
/*   */ 
/*   */ class ValidationError
/*   */   extends Exception {
/*   */   public ValidationError(FrameworkMember<?> member, Class<? extends Annotation> annotation, String suffix) {
/* 9 */     super(String.format("The @%s '%s' %s", new Object[] { annotation.getSimpleName(), member.getName(), suffix }));
/*   */   }
/*   */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\runners\rules\ValidationError.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */