/*     */ package org.junit.internal.runners.rules;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.junit.ClassRule;
/*     */ import org.junit.Rule;
/*     */ import org.junit.rules.MethodRule;
/*     */ import org.junit.rules.TestRule;
/*     */ import org.junit.runners.model.FrameworkMember;
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
/*     */ public class RuleMemberValidator
/*     */ {
/*  32 */   public static final RuleMemberValidator CLASS_RULE_VALIDATOR = classRuleValidatorBuilder().withValidator(new DeclaringClassMustBePublic()).withValidator(new MemberMustBeStatic()).withValidator(new MemberMustBePublic()).withValidator(new FieldMustBeATestRule()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public static final RuleMemberValidator RULE_VALIDATOR = testRuleValidatorBuilder().withValidator(new MemberMustBeNonStaticOrAlsoClassRule()).withValidator(new MemberMustBePublic()).withValidator(new FieldMustBeARule()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   public static final RuleMemberValidator CLASS_RULE_METHOD_VALIDATOR = classRuleValidatorBuilder().forMethods().withValidator(new DeclaringClassMustBePublic()).withValidator(new MemberMustBeStatic()).withValidator(new MemberMustBePublic()).withValidator(new MethodMustBeATestRule()).build();
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
/*  63 */   public static final RuleMemberValidator RULE_METHOD_VALIDATOR = testRuleValidatorBuilder().forMethods().withValidator(new MemberMustBeNonStaticOrAlsoClassRule()).withValidator(new MemberMustBePublic()).withValidator(new MethodMustBeARule()).build();
/*     */ 
/*     */   
/*     */   private final Class<? extends Annotation> annotation;
/*     */ 
/*     */   
/*     */   private final boolean methods;
/*     */ 
/*     */   
/*     */   private final List<RuleValidator> validatorStrategies;
/*     */ 
/*     */   
/*     */   RuleMemberValidator(Builder builder) {
/*  76 */     this.annotation = builder.annotation;
/*  77 */     this.methods = builder.methods;
/*  78 */     this.validatorStrategies = builder.validators;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(TestClass target, List<Throwable> errors) {
/*  89 */     List<? extends FrameworkMember<?>> members = this.methods ? target.getAnnotatedMethods(this.annotation) : target.getAnnotatedFields(this.annotation);
/*     */ 
/*     */     
/*  92 */     for (FrameworkMember<?> each : members) {
/*  93 */       validateMember(each, errors);
/*     */     }
/*     */   }
/*     */   
/*     */   private void validateMember(FrameworkMember<?> member, List<Throwable> errors) {
/*  98 */     for (RuleValidator strategy : this.validatorStrategies) {
/*  99 */       strategy.validate(member, this.annotation, errors);
/*     */     }
/*     */   }
/*     */   
/*     */   private static Builder classRuleValidatorBuilder() {
/* 104 */     return new Builder(ClassRule.class);
/*     */   }
/*     */   
/*     */   private static Builder testRuleValidatorBuilder() {
/* 108 */     return new Builder(Rule.class);
/*     */   }
/*     */   
/*     */   private static class Builder {
/*     */     private final Class<? extends Annotation> annotation;
/*     */     private boolean methods;
/*     */     private final List<RuleMemberValidator.RuleValidator> validators;
/*     */     
/*     */     private Builder(Class<? extends Annotation> annotation) {
/* 117 */       this.annotation = annotation;
/* 118 */       this.methods = false;
/* 119 */       this.validators = new ArrayList<RuleMemberValidator.RuleValidator>();
/*     */     }
/*     */     
/*     */     Builder forMethods() {
/* 123 */       this.methods = true;
/* 124 */       return this;
/*     */     }
/*     */     
/*     */     Builder withValidator(RuleMemberValidator.RuleValidator validator) {
/* 128 */       this.validators.add(validator);
/* 129 */       return this;
/*     */     }
/*     */     
/*     */     RuleMemberValidator build() {
/* 133 */       return new RuleMemberValidator(this);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isRuleType(FrameworkMember<?> member) {
/* 138 */     return (isMethodRule(member) || isTestRule(member));
/*     */   }
/*     */   
/*     */   private static boolean isTestRule(FrameworkMember<?> member) {
/* 142 */     return TestRule.class.isAssignableFrom(member.getType());
/*     */   }
/*     */   
/*     */   private static boolean isMethodRule(FrameworkMember<?> member) {
/* 146 */     return MethodRule.class.isAssignableFrom(member.getType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static interface RuleValidator
/*     */   {
/*     */     void validate(FrameworkMember<?> param1FrameworkMember, Class<? extends Annotation> param1Class, List<Throwable> param1List);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class MemberMustBeNonStaticOrAlsoClassRule
/*     */     implements RuleValidator
/*     */   {
/*     */     private MemberMustBeNonStaticOrAlsoClassRule() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void validate(FrameworkMember<?> member, Class<? extends Annotation> annotation, List<Throwable> errors) {
/* 168 */       boolean isMethodRuleMember = RuleMemberValidator.isMethodRule(member);
/* 169 */       boolean isClassRuleAnnotated = (member.getAnnotation(ClassRule.class) != null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 176 */       if (member.isStatic() && (isMethodRuleMember || !isClassRuleAnnotated)) {
/*     */         String message;
/* 178 */         if (RuleMemberValidator.isMethodRule(member)) {
/* 179 */           message = "must not be static.";
/*     */         } else {
/* 181 */           message = "must not be static or it must be annotated with @ClassRule.";
/*     */         } 
/* 183 */         errors.add(new ValidationError(member, annotation, message));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class MemberMustBeStatic
/*     */     implements RuleValidator {
/*     */     private MemberMustBeStatic() {}
/*     */     
/*     */     public void validate(FrameworkMember<?> member, Class<? extends Annotation> annotation, List<Throwable> errors) {
/* 193 */       if (!member.isStatic()) {
/* 194 */         errors.add(new ValidationError(member, annotation, "must be static."));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DeclaringClassMustBePublic
/*     */     implements RuleValidator
/*     */   {
/*     */     private DeclaringClassMustBePublic() {}
/*     */     
/*     */     public void validate(FrameworkMember<?> member, Class<? extends Annotation> annotation, List<Throwable> errors) {
/* 205 */       if (!isDeclaringClassPublic(member)) {
/* 206 */         errors.add(new ValidationError(member, annotation, "must be declared in a public class."));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isDeclaringClassPublic(FrameworkMember<?> member) {
/* 212 */       return Modifier.isPublic(member.getDeclaringClass().getModifiers());
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class MemberMustBePublic
/*     */     implements RuleValidator {
/*     */     private MemberMustBePublic() {}
/*     */     
/*     */     public void validate(FrameworkMember<?> member, Class<? extends Annotation> annotation, List<Throwable> errors) {
/* 221 */       if (!member.isPublic()) {
/* 222 */         errors.add(new ValidationError(member, annotation, "must be public."));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class FieldMustBeARule
/*     */     implements RuleValidator
/*     */   {
/*     */     private FieldMustBeARule() {}
/*     */     
/*     */     public void validate(FrameworkMember<?> member, Class<? extends Annotation> annotation, List<Throwable> errors) {
/* 233 */       if (!RuleMemberValidator.isRuleType(member)) {
/* 234 */         errors.add(new ValidationError(member, annotation, "must implement MethodRule or TestRule."));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class MethodMustBeARule
/*     */     implements RuleValidator
/*     */   {
/*     */     private MethodMustBeARule() {}
/*     */     
/*     */     public void validate(FrameworkMember<?> member, Class<? extends Annotation> annotation, List<Throwable> errors) {
/* 246 */       if (!RuleMemberValidator.isRuleType(member)) {
/* 247 */         errors.add(new ValidationError(member, annotation, "must return an implementation of MethodRule or TestRule."));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class MethodMustBeATestRule
/*     */     implements RuleValidator
/*     */   {
/*     */     private MethodMustBeATestRule() {}
/*     */     
/*     */     public void validate(FrameworkMember<?> member, Class<? extends Annotation> annotation, List<Throwable> errors) {
/* 259 */       if (!RuleMemberValidator.isTestRule(member)) {
/* 260 */         errors.add(new ValidationError(member, annotation, "must return an implementation of TestRule."));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class FieldMustBeATestRule
/*     */     implements RuleValidator
/*     */   {
/*     */     private FieldMustBeATestRule() {}
/*     */ 
/*     */     
/*     */     public void validate(FrameworkMember<?> member, Class<? extends Annotation> annotation, List<Throwable> errors) {
/* 273 */       if (!RuleMemberValidator.isTestRule(member))
/* 274 */         errors.add(new ValidationError(member, annotation, "must implement TestRule.")); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\internal\runners\rules\RuleMemberValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */