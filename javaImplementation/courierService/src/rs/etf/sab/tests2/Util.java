/*    */ package rs.etf.sab.tests2;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import javafx.util.Pair;
/*    */ 
/*    */ public class Util
/*    */ {
/*    */   public static double euclidean(int x1, int y1, int x2, int y2) {
/*  9 */     return Math.sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
/*    */   }
/*    */ 
/*    */   
/*    */    public static BigDecimal getPackagePrice(int type, BigDecimal weight, double distance) {
/* 14 */     switch (type) {
/*    */       case 0:
/* 16 */         return new BigDecimal(115.0D * distance);
/*    */       case 1:
/* 18 */         return new BigDecimal((175.0D + weight.doubleValue() * 100.0D) * distance);
/*    */       case 2:
/* 20 */         return new BigDecimal((250.0D + weight.doubleValue() * 100.0D) * distance);
/*    */       case 3:
/* 22 */         return new BigDecimal((350.0D + weight.doubleValue() * 500.0D) * distance);
/*    */     } 
/* 24 */     return null;
/*    */   }
/*    */   
/*    */ public   static double getDistance(Pair<Integer, Integer>... addresses) {
/* 28 */     double distance = 0.0D;
/* 29 */     for (int i = 1; i < addresses.length; i++)
/* 30 */       distance += euclidean(((Integer)addresses[i - 1].getKey()).intValue(), ((Integer)addresses[i - 1].getValue()).intValue(), ((Integer)addresses[i]
/* 31 */           .getKey()).intValue(), ((Integer)addresses[i].getValue()).intValue()); 
/* 32 */     return distance;
/*    */   }
/*    */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\rs\etf\sab\tests\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */