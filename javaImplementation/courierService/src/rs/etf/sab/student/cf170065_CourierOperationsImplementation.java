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
             String sql = "insert into Couirier (name, licencePlate, profit , status, currentlyDriving, numberOfDeliveredPackages) "
                     + "        values(?,?,0,0,null,0)";
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
                String sql = "select username form Courier where status = ?";
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
                String sql = "select username from Courier orderby profit desc";
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
        if(numOfDeliveredPackages!=-1) sql="select avg(profit) form Courier where numberOfDeliveredPackages= ?";
        else sql= "select avg(profit) form Courier";
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
    
}
