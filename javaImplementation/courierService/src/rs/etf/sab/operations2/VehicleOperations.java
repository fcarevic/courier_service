package rs.etf.sab.operations;

import com.sun.istack.internal.NotNull;
import java.math.BigDecimal;
import java.util.List;

public interface VehicleOperations {
  boolean insertVehicle(@NotNull String paramString, int paramInt, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2);
  
  int deleteVehicles(@NotNull String... paramVarArgs);
  
  List<String> getAllVehichles();
  
  boolean changeFuelType(@NotNull String paramString, int paramInt);
  
  boolean changeConsumption(@NotNull String paramString, BigDecimal paramBigDecimal);
  
  boolean changeCapacity(@NotNull String paramString, BigDecimal paramBigDecimal);
  
  boolean parkVehicle(@NotNull String paramString, int paramInt);
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\rs\etf\sab\operations\VehicleOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */