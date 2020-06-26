/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.CourierOperations;

/**
 *
 * @author CAR
 */
public class cf170065_CourierOperationsImplementation  implements CourierOperations{

    @Override
    public boolean insertCourier(String username, String licencePlate) {
        if(PackageRoutes.getInstance().getCourierRequestOperation().checkExistsDriversLicence(licencePlate, username)) return false;
             String sql = "insert into Courier (username, dirversLicence, profit , status, numberOfDeliveredPackages) "
                     + "        values(?,?,0,0,0)";
            Connection conn = DB.get_instance();
        try(      PreparedStatement query = conn.prepareStatement(sql);
               
     ) {
             query.setString(1, username);
                query.setString(2, licencePlate);
            return query.executeUpdate()==1;
         } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
            
     return false;
    }

    @Override
    public boolean deleteCourier(String username) {
        
                String sql = "delete from Courier where username=?";
            Connection conn = DB.get_instance();
        try(      PreparedStatement query = conn.prepareStatement(sql);
               
     ) {
             query.setString(1, username);
                
            return query.executeUpdate()==1;
         } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
            
     return false;
    }

    @Override
    public List<String> getCouriersWithStatus(int status) {
        List<String> list= new LinkedList<>();
                String sql = "select username from Courier where status = ?";
            Connection conn = DB.get_instance();
        try(      PreparedStatement query = conn.prepareStatement(sql);
               
     ) {
             query.setInt(1, status);
             ResultSet result= query.executeQuery();
             while( result.next())
                 list.add(result.getString(1));
            
         } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
            
     return list;
   }

    @Override
    public List<String> getAllCouriers() {
         List<String> list= new LinkedList<>();
                String sql = "select username from Courier order by profit desc";
            Connection conn = DB.get_instance();
        try(      PreparedStatement query = conn.prepareStatement(sql);
               
     ) {
             
             ResultSet result= query.executeQuery();
             while( result.next())
                 list.add(result.getString(1));
            
         } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
            
     return list;  
    }

    @Override
    public BigDecimal getAverageCourierProfit(int numOfDeliveredPackages) {
       BigDecimal avg= new BigDecimal(0);
        String sql =null;
        if(numOfDeliveredPackages!=-1) sql="select avg(profit) from Courier where numberOfDeliveredPackages= ?";
        else sql= "select avg(profit) from Courier";
            Connection conn = DB.get_instance();
        try(      PreparedStatement query = conn.prepareStatement(sql);
               
     ) {
                if(numOfDeliveredPackages!=-1)
             query.setInt(1, numOfDeliveredPackages);
             ResultSet result= query.executeQuery();
             while( result.next())
                 avg= result.getBigDecimal(1);
                 
            
         } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
            
     return avg;
    }
    
    public boolean changeCouriersStatus(String username, int status){
        try {
            String sql  = "Update Courier set status=? where userName= ?";
            Connection conn = DB.get_instance();
            PreparedStatement query = conn.prepareStatement(sql);
            query.setInt(1, status);
            query.setString(2, username);
            return query.executeUpdate()==1;
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
        
    }

    public boolean incrementNumberOfDeliveredPackages(String username, int increment){
        String sql = "Update Courier set numberOfDeliveredPackages= numberOfDeliveredPackages+ ?  where username=?";
     Connection conn = DB.get_instance();
        try (     PreparedStatement query = conn.prepareStatement(sql);
      ) {
                query.setInt(1, increment);
                query.setString(2, username);
                return query.executeUpdate()==1;
         } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    return false;
    }
    public  boolean assignVehicle(String username, String myVehicle) {
          boolean flag =false;
        try {
            String sql = "Insert into EverDriven(userName, registrationNum) values (?,?)";
            String sql2 = "Insert into CurrentlyDriving(userName, registrationNum) values (?,?) ";
            
            Connection conn= DB.get_instance();
            PreparedStatement query = conn.prepareStatement(sql2);
            query.setString(1, username );
            query.setString(2, myVehicle);
           flag= query.executeUpdate()==1;
            if(flag){
                            PreparedStatement query2 = conn.prepareStatement(sql);
                            query2.setString(1, username);
                            query2.setString(2,myVehicle);
                            query2.executeUpdate();
                
                
                
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    public boolean leaveVehicle(String username){
            String sql = "Delete from CurrentlyDriving where username=?";
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);
            ){
            query.setString(1, username);
            return query.executeUpdate()==1;
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    
    }
     
    public    String getCurrentlyDrivingVehicle(String username) {
     String sql2 = " select registrationNum from CurrentlyDriving where userName=?";
     Connection conn= DB.get_instance();
        try {
            PreparedStatement query = conn.prepareStatement(sql2);
            query.setString(1, username);
            ResultSet rs=  query.executeQuery();
            if(rs.next()){
                return rs.getString(1);
                }
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
     return null;
            }
       public boolean incrementProfitForCourier(String username, BigDecimal newProfit){
       String sql  = "update Courier set profit = profit+ ? where username =?";
       Connection conn  = DB.get_instance();
        try (     PreparedStatement query = conn.prepareStatement(sql);
     ){
            query.setString(2, username);
            query.setBigDecimal(1, newProfit);
            return query.executeUpdate()==1;
          } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
       
             return false;
       
       
       
       }
       
}
