package rs.etf.sab.operations;

import com.sun.istack.internal.NotNull;
import java.util.List;

public interface CityOperations {
  int insertCity(@NotNull String paramString1, String paramString2);
  
  int deleteCity(@NotNull String... paramVarArgs);
  
  boolean deleteCity(int paramInt);
  
  List<Integer> getAllCities();
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\rs\etf\sab\operations\CityOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */