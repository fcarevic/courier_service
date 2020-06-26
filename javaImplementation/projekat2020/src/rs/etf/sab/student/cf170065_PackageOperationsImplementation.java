/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.management.Query;
import rs.etf.sab.operations.PackageOperations;

/**
 *
 * @author CAR
 */
public class cf170065_PackageOperationsImplementation implements PackageOperations{

    @Override
    public int insertPackage(int addressFrom, int addressTo, String username, int type, BigDecimal weight) {
        if(weight==null) weight=new BigDecimal(10);
     String sql = "insert into PackageRequest(type, weight, userName, fromAdress, toAdress) values (?,?,?,?,?)";
     String sql2= "Update Package set price  = ? where idPackage = ?"  ;
     Connection conn = DB.get_instance();
        try (  PreparedStatement query = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement q22= conn.prepareStatement(sql2);){
          query.setInt(1,type);
          query.setBigDecimal(2, weight);
          query.setString(3, username);
          query.setInt(4,addressFrom);
          query.setInt(5,addressTo);
         if ( query.executeUpdate()==1){
             ResultSet keys=  query.getGeneratedKeys();
             int idPackage=-1;
             if(keys.next()) {idPackage=keys.getInt(1);
                 Pair<Integer,Integer> to = PackageRoutes.getInstance().getAddressOperations().getCoordinatesOfAdress(addressTo);
                Pair<Integer,Integer> from = PackageRoutes.getInstance().getAddressOperations().getCoordinatesOfAdress(addressFrom);
                double distance = Math.hypot(to.getValue()-from.getValue(), to.getKey()-from.getKey());
                BigDecimal price = getPackagePrice(type, weight, distance);
                q22.setBigDecimal(1, price);
                q22.setInt(2, idPackage);
                q22.executeUpdate();
                return idPackage;
             }
         }
         
          
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    
    }

    @Override
    public boolean acceptAnOffer(int package_id) {
        java.sql.Date now = new java.sql.Date(System.currentTimeMillis()); 
        String sql = "update Package set accepted_at = GETDATE(), status = 1 where idPackage = ? and status=0";
        Connection conn = DB.get_instance();
        try (  PreparedStatement query = conn.prepareStatement(sql);){
          query.setInt(1,package_id);
         
         return query.executeUpdate()==1;
         
          
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public boolean rejectAnOffer(int package_id) {
    String sql = "update Package set  status = 4 where idPackage = ? and status=0";
        Connection conn = DB.get_instance();
        try (  PreparedStatement query = conn.prepareStatement(sql);){
          query.setInt(1,package_id);
         
         return query.executeUpdate()==1;
         
          
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public List<Integer> getAllPackages() {
         List<Integer> list= new LinkedList<>();
            String sql = "select idPackage from Package";
            Connection conn = DB.get_instance();
        try (Statement query = conn.createStatement();){
             ResultSet result = query.executeQuery(sql);
             while(result.next())
                 list.add(result.getInt(1));
             
             return list;
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;   }

    @Override
    public List<Integer> getAllPackagesWithSpecificType(int type) {
      List<Integer> list= new LinkedList<>();
            String sql = "select idPackage from PackageRequests where type = ?";
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            query.setInt(1, type);
             ResultSet result = query.executeQuery(sql);
             while(result.next())
                 list.add(result.getInt(1));
             
             return list;
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public List<Integer> getAllUndeliveredPackages() {
    List<Integer> list= new LinkedList<>();
            String sql = "select idPackage from Package where status<>3 and status<>4 and status<>0 ";
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            
             ResultSet result = query.executeQuery();
             while(result.next())
                 list.add(result.getInt(1));
             
             return list;
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public List<Integer> getAllUndeliveredPackagesFromCity(int city_id) {
         List<Integer> list= new LinkedList<>();
            String sql = "select Package.idPackage from Package,PackageRequest, Adress where (Package.status=2 or Package.status=1)  and "
                    + "Adress.idAdress = PackageRequest.fromAdress and PackageRequest.idPackage = Package.idPackage and"
                    + " Adress.idCity = ? " ;
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            query.setInt(1, city_id);
            
             ResultSet result = query.executeQuery();
             while(result.next())
                 list.add(result.getInt(1));
             
             return list;
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list; 
    }

    @Override
    public List<Integer> getAllPackagesCurrentlyAtCity(int city_id) {
  List<Integer> list= new LinkedList<>();
            String sql = "select Package.idPackage from Package, Adress where  Package.currently_atAdress= Adress.idAdress and Package.idPackage not in (select idPackage from PackageInVehicle) and"
                    + " Adress.idCity=?";
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            query.setInt(1, city_id);
            
             ResultSet result = query.executeQuery();
             while(result.next())
                 list.add(result.getInt(1));
             
             return list;
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;       

    }

    @Override
    public boolean deletePackage(int package_id) {
         Connection conn = DB.get_instance();
        try {
            String sql1  = "delete from Package where idPackage =? and status in (0,4)";
            String sql2  = "delete from PackageRequest where idPackage =?";
           
            conn.setAutoCommit(false);
            try (PreparedStatement query1 = conn.prepareStatement(sql1);
                    PreparedStatement query2 = conn.prepareStatement(sql2);){
                query1.setInt(1, package_id);
                query2.setInt(1, package_id);
                
                boolean flag = false;
                 flag= query1.executeUpdate()==1;
                
                 flag= flag&& query2.executeUpdate()==1;
                 if(!flag)conn.rollback();
                 else conn.commit();
                 conn.setAutoCommit(true);
                 return flag;
                 
            } catch (SQLException ex) {
                Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
         
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
               
     }

    @Override
    public boolean changeWeight(int package_id, BigDecimal weight) {
     String sql = "update PackageRequest set weight = ? where idPackage = ? "
             + "and (select status from Package where idPackage = ?)=0 ";
                    
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            query.setInt(2, package_id);
             query.setInt(3, package_id);
            query.setBigDecimal(1, weight);
            if(query.executeUpdate()==1) return setPrice(package_id);
           
             
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;   }

    public boolean setPrice(int idPackage){
         int toAdress = getAdressTo(idPackage);
         int fromAdress= getCurrentAdressOfPackage(idPackage);
         Pair<Integer,Integer> to = PackageRoutes.getInstance().getAddressOperations().getCoordinatesOfAdress(toAdress);
           Pair<Integer,Integer> from = PackageRoutes.getInstance().getAddressOperations().getCoordinatesOfAdress(fromAdress);
          double distance= Math.hypot(to.getKey()- from.getKey(), to.getValue()-from.getValue());
          int type =getType(idPackage);
          BigDecimal weight = getWeight(idPackage);
     
         BigDecimal price = getPackagePrice(type, weight, distance);
        String sql2= "Update Package set price  = ? where idPackage = ?"  ;
     Connection conn = DB.get_instance();
        try (     PreparedStatement query = conn.prepareStatement(sql2);
       ){
            query.setInt(2, idPackage);
            query.setBigDecimal(1, price);
            return query.executeUpdate()==1;
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
     
   return false;
    
    }
    
    
    @Override
    public boolean changeType(int package_id, int type) {
   String sql = "update PackageRequest set type = ? where idPackage = ? and (select status from Package where idPackage = ? ) = 0";
                    
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            query.setInt(2, package_id);
             query.setInt(3, package_id);
            query.setInt(1, type);
            if( query.executeUpdate()==1){
                return setPrice(package_id);
            
            
            
            
            }
           
             
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;  
    }

    @Override
    public int getDeliveryStatus(int package_id) {
    String sql = "select status from Package where idPackage = ?";
                    
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            query.setInt(1, package_id);
            
             ResultSet result = query.executeQuery();
             while(result.next())
               return result.getInt(1);
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;  
    }

    @Override
    public BigDecimal getPriceOfDelivery(int idPackage) {
        try {
            String sql = "Select price from Package where idPackage = ?";
            Connection conn = DB.get_instance();
            PreparedStatement query = conn.prepareStatement(sql);
            query.setInt(1, idPackage);
            ResultSet executeQuery = query.executeQuery();
            if(executeQuery.next())
                return executeQuery.getBigDecimal(1);
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
                
    }

    @Override
    public int getCurrentLocationOfPackage(int package_id) {
        try {
            //ZASTO BIG DECIMAL ?
            String sql = "Select idCity from Adress, Package where Adress.idAdress = Package.currently_atAdress and Package.idPackage=? and Package.idPackage not in (Select idPackage from PackageInVehicle) ";
            Connection conn = DB.get_instance();
            PreparedStatement query = conn.prepareStatement(sql);
            query.setInt(1, package_id);
            ResultSet rs= query.executeQuery();
            if(rs.next())
                 return rs.getInt(1);
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
     }
    public int getCurrentAdressOfPackage(int idPackage){
    String sql = "Select currently_atAdress from Package where Package.idPackage=? and not exists(Select * from PackageinVehicle where PackageinVehicle.idPackage = Package.idPackage ) ";
    Connection conn = DB.get_instance();
        try (      PreparedStatement query= conn.prepareStatement(sql);
     ) {
            query.setInt(1, idPackage);
            ResultSet rs = query.executeQuery();
            if(rs.next())return rs.getInt(1);
         } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return -1;
    
    }

    @Override
    public Date getAcceptanceTime(int package_id) {
        String sql = "select accepted_at from Package where idPackage = ?";
                  
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            query.setInt(1, package_id);
            
             ResultSet result = query.executeQuery(sql);
             while(result.next())
               return result.getDate(1);
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;    
    }

    public BigDecimal getWeight(int idPackage) {
        try {
            String sql = "Select weight from PackageRequest where idPackage = ? ";
            Connection conn = DB.get_instance();
            
            PreparedStatement query= conn.prepareStatement(sql);
            query.setInt(1, idPackage);
            ResultSet rs= query.executeQuery();
            if(rs.next())
                return rs.getBigDecimal(1);
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    
    }
    public int getType(int idPackage) {
        try {
            String sql = "Select type from PackageRequest where idPackage = ? ";
            Connection conn = DB.get_instance();
            
            PreparedStatement query= conn.prepareStatement(sql);
            query.setInt(1, idPackage);
            ResultSet rs= query.executeQuery();
            if(rs.next())
                return rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    
    }

    public  boolean insertPackageInVehicle(int packageId, String myVehicle) {
        try {
            String sql = "Insert into PackageInVehicle(idPackage, registrationNum) values(?,?)";
            Connection conn= DB.get_instance();
            PreparedStatement query= conn.prepareStatement(sql);
            query.setString(2, myVehicle);
            query.setInt(1,packageId);
            return query.executeUpdate()==1;
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
        
                
    }

    public boolean changeStatus(int packageId, int status) {
         try {
            String sql = "Update Package set status= ? where idPackage = ?";
            Connection conn= DB.get_instance();
            PreparedStatement query= conn.prepareStatement(sql);
            query.setInt(2, packageId);
            query.setInt(1,status);
            return query.executeUpdate()==1;
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
        
        
    }
    public List<Integer> getUncollectedPackagesFromCity(int idCity){
        Connection conn = DB.get_instance();
        LinkedList<Integer> list= new LinkedList<>();
        String sql = "Select Package.idPackage from Package,Adress, PackageRequest "
                + "where Package.status = 1 and Adress.idAdress=PackageRequest.fromAdress and Adress.idCity=? and PackageRequest.idPackage = Package.idPackage order by Package.accepted_at";
        try (PreparedStatement st= conn.prepareStatement(sql);){
            st.setInt(1, idCity);
            ResultSet rs= st.executeQuery();
            while(rs.next()){
                list.add(rs.getInt(1));
                }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public List<Integer> getPackagesFromStockroomInCity(int idCity){
    Connection conn = DB.get_instance();
    LinkedList<Integer> list= new LinkedList<>();
    String sql = "Select p.idPackage from Package p , Stockroom s , Adress a "
            + "where p.currently_atAdress = s.idAdress and a.idAdress = s.idAdress and a.idCity=?"
            + "  and  not exists (select * from PackageInVehicle where PackageInVehicle.idPackage= p.idPackage)";
        try (     PreparedStatement query = conn.prepareStatement(sql);
      ){
            query.setInt(1, idCity);
            ResultSet rs= query.executeQuery();
            while(rs.next()){
                list.add(rs.getInt(1));
            }
            
         } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    return list;
    }
    
    public int getAdressTo(int idPackage){
    String sql = "Select toAdress from PackageRequest where idPackage = ?";
    Connection conn = DB.get_instance();
        try (       PreparedStatement query = conn.prepareStatement(sql);
     ) {
            query.setInt(1, idPackage);
            ResultSet rs= query.executeQuery();
            if(rs.next())
                return rs.getInt(1);
                  
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return -1;
    
    
    }
    
    public boolean setCurrentLocation(int idPackage, int idAdress){
    
    String sql = "Update Package set currently_atAdress = ? where idPackage = ?";
    Connection conn = DB.get_instance();
        try (      PreparedStatement query = conn.prepareStatement(sql);
      ) {
            query.setInt(1, idAdress);
                query.setInt(2, idPackage);
                
               return  query.executeUpdate()==1;
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return false;
    }
    
    
    public boolean removePackageFromVehicle(int idPackage, String vehicleLicence){
    String sql =  "Delete from PackageInVehicle where idPackage=? and registrationNum = ? ";
    Connection conn = DB.get_instance();
        try(         PreparedStatement query = conn.prepareStatement(sql);
   ) {
            query.setInt(1, idPackage);
            query.setString(2, vehicleLicence);
            return query.executeUpdate()==1;
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return false;
    }
    
     public static BigDecimal getPackagePrice(int type, BigDecimal weight, double distance) {
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
}
