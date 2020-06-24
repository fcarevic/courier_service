/*    */ package org.junit.experimental.categories;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.junit.internal.Classes;
/*    */ import org.junit.runner.FilterFactory;
/*    */ import org.junit.runner.FilterFactoryParams;
/*    */ import org.junit.runner.manipulation.Filter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class CategoryFilterFactory
/*    */   implements FilterFactory
/*    */ {
/*    */   public Filter createFilter(FilterFactoryParams params) throws FilterFactory.FilterNotCreatedException {
/*    */     try {
/* 23 */       return createFilter(parseCategories(params.getArgs()));
/* 24 */     } catch (ClassNotFoundException e) {
/* 25 */       throw new FilterFactory.FilterNotCreatedException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract Filter createFilter(List<Class<?>> paramList);
/*    */ 
/*    */ 
/*    */   
/*    */   private List<Class<?>> parseCategories(String categories) throws ClassNotFoundException {
/* 37 */     List<Class<?>> categoryClasses = new ArrayList<Class<?>>();
/*    */     
/* 39 */     for (String category : categories.split(",")) {
/* 40 */       Class<?> categoryClass = Classes.getClass(category);
/*    */       
/* 42 */       categoryClasses.add(categoryClass);
/*    */     } 
/*    */     
/* 45 */     return categoryClasses;
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\org\junit\experimental\categories\CategoryFilterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */