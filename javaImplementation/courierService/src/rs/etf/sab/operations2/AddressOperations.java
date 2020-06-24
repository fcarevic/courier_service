package rs.etf.sab.operations;

import com.sun.istack.internal.NotNull;
import java.util.List;

public interface AddressOperations {
  int insertAddress(@NotNull String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  int deleteAddresses(@NotNull String paramString, int paramInt);
  
  boolean deleteAdress(int paramInt);
  
  int deleteAllAddressesFromCity(int paramInt);
  
  List<Integer> getAllAddresses();
  
  List<Integer> getAllAddressesFromCity(int paramInt);
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\rs\etf\sab\operations\AddressOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */