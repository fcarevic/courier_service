package rs.etf.sab.operations;

import com.sun.istack.internal.NotNull;
import java.math.BigDecimal;
import java.util.List;

public interface CourierOperations {
  boolean insertCourier(@NotNull String paramString1, @NotNull String paramString2);
  
  boolean deleteCourier(@NotNull String paramString);
  
  List<String> getCouriersWithStatus(int paramInt);
  
  List<String> getAllCouriers();
  
  BigDecimal getAverageCourierProfit(int paramInt);
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\rs\etf\sab\operations\CourierOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */