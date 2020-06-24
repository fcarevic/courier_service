package rs.etf.sab.operations;

import com.sun.istack.internal.NotNull;
import java.util.List;

public interface UserOperations {
  boolean insertUser(@NotNull String paramString1, @NotNull String paramString2, @NotNull String paramString3, @NotNull String paramString4, @NotNull int paramInt);
  
  boolean declareAdmin(@NotNull String paramString);
  
  int getSentPackages(@NotNull String... paramVarArgs);
  
  int deleteUsers(@NotNull String... paramVarArgs);
  
  List<String> getAllUsers();
}


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\rs\etf\sab\operations\UserOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */