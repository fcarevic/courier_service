package rs.etf.sab.operations;

import com.sun.istack.internal.NotNull;
import java.util.List;

public interface DriveOperation {
  boolean planingDrive(String paramString);
  
  int nextStop(@NotNull String paramString);
  
  List<Integer> getPackagesInVehicle(String paramString);
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\rs\etf\sab\operations\DriveOperation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */