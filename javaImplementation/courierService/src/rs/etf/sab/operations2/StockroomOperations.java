package rs.etf.sab.operations;

import java.util.List;

public interface StockroomOperations {
  int insertStockroom(int paramInt);
  
  boolean deleteStockroom(int paramInt);
  
  int deleteStockroomFromCity(int paramInt);
  
  List<Integer> getAllStockrooms();
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\rs\etf\sab\operations\StockroomOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */