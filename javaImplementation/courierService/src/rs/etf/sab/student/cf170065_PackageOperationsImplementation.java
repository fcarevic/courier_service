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
import rs.etf.sab.operations.PackageOperations;

/**
 *
 * @author CAR
 */
public class cf170065_PackageOperationsImplementation implements PackageOperations{

    @Override
    public int insertPackage(int addressFrom, int addressTo, String username, int type, BigDecimal weight) {
     String sql = "insert into PackageRequests(type, weight, userName, fromAdress, toAdress) value (?,?,?,?,?)";
        Connection conn = DB.get_instance();
        try (  PreparedStatement query = conn.prepareStatement(sql);){
          query.setInt(1,type);
          query.setBigDecimal(2, weight);
          query.setString(3, username);
          query.setInt(4,addressFrom);
          query.setInt(5,addressTo);
         if ( query.executeUpdate()==1){
             ResultSet keys=  query.getGeneratedKeys();
             if(keys.next()) return keys.getInt(1);
         }
         
          
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    
    }

    @Override
    public boolean acceptAnOffer(int package_id) {
        String sql = "update Package set accepted_at = now(), status = 1 where idPackage = ?";
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
    String sql = "update Package set  status = 4 where idPackage = ?";
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
            String sql = "select idPackage from Package where status<>3 and status<>4 ";
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            
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
    public List<Integer> getAllUndeliveredPackagesFromCity(int city_id) {
         List<Integer> list= new LinkedList<>();
            String sql = "select idPackage from Package,PackageRequest, Adress where Package.status<>3 and Package.status<>4 and "
                    + "Adress.idAdress = PackageRequest.fromAdress and PackageRequest.idPackage = Package.idPackage"
                    + "Adress.idCity = ? " ;
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            query.setInt(1, city_id);
            
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
    public List<Integer> getAllPackagesCurrentlyAtCity(int city_id) {
  List<Integer> list= new LinkedList<>();
            String sql = "select idPackage from Package, Adress where Package.status=2 and Package.currently_atAdress= Adress.idAdress"
                    + " Adress.idCity=?";
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            query.setInt(1, city_id);
            
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
    public boolean deletePackage(int package_id) {
        try {
            String sql1  = "delete from Package where idPackage =?";
            String sql2  = "delete from PackageRequest where idPackage =?";
            Connection conn = DB.get_instance();
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
                 return flag;
                
            } catch (SQLException ex) {
                Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_PackageOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
               
     }

    @Override
    public boolean changeWeight(int package_id, BigDecimal weight) {
     String sql = "update PackageRequest set weight = ? where idPackage = ?";
                    
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            query.setInt(2, package_id);
            query.setBigDecimal(1, weight);
            return query.executeUpdate()==1;
           
             
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;   }

    @Override
    public boolean changeType(int package_id, int type) {
   String sql = "update PackageRequest set type = ? where idPackage = ?";
                    
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            query.setInt(2, package_id);
            query.setInt(1, type);
            return query.executeUpdate()==1;
           
             
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;  
    }

    @Override
    public Integer getDeliveryStatus(int package_id) {
    String sql = "select status from Package where idPackage = ?";
                    
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            query.setInt(1, package_id);
            
             ResultSet result = query.executeQuery(sql);
             while(result.next())
               return result.getInt(1);
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;  
    }

    @Override
    public BigDecimal getPriceOfDelivery(int arg0) {
    }

    @Override
    public BigDecimal getCurrentLocationOfPackage(int package_id) {
       //ZASTO BIG DECIMAL ?
        
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
    
}
