/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.CourierRequestOperation;

/**
 *
 * @author CAR
 */
public class cf170065_CourierRequestOperationsImplementation implements CourierRequestOperation{

    @Override
    public boolean insertCourierRequest(String username, String driversLicence) {
        String sql = "insert into CourierRequests(userName, driversLicence) value(?,?)";
        Connection conn = DB.get_instance();
        try (
            PreparedStatement query = conn.prepareStatement(sql);
                )
            {
                query.setString(1, username);
                query.setString(2, driversLicence);
                return query.executeUpdate()==1;
                
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierRequestOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteCourierRequest(String userName) {
        String sql = "delete from CourierRequests where userName = ?";
        Connection conn = DB.get_instance();
        try (
            PreparedStatement query = conn.prepareStatement(sql);
                )
            {
                query.setString(1, userName);
                
                return query.executeUpdate()==1;
                
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierRequestOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeVehicleInCourierRequest(String userName, String drivingLicence) {
        //OVO JE GRESKA?
     String sql = "update  CourierRequests set driversLicence = ? where userName = ?";
        Connection conn = DB.get_instance();
        try (
            PreparedStatement query = conn.prepareStatement(sql);
                )
            {
                query.setString(2, userName);
                 query.setString(1, drivingLicence);
                
                return query.executeUpdate()==1;
                
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierRequestOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
   
    }

    @Override
    public List<String> getAllCourierRequests() {
        List<String> list= new LinkedList<>();
            String sql = "select userName from CourierRequests";
            Connection conn = DB.get_instance();
        try (Statement query = conn.createStatement();){
             ResultSet result = query.executeQuery(sql);
             while(result.next())
                 list.add(result.getString(1));
             
             return list;
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public boolean grantRequest(String userName) {
           
            
        try {
            
            
            String getDriversLicence = "select driversLicence from  CourierRequest where userName = ?";
            Connection conn = DB.get_instance();
            conn.setAutoCommit(false);
            try (PreparedStatement query = conn.prepareStatement(getDriversLicence);){
                ResultSet result = query.executeQuery();
                String driversLicene = null;
                if(result.next())
                    driversLicene= result.getString(1);
                else throw new SQLException("isforsiran izuzetak");
                
               boolean flag= true;
               
               flag= flag&& (new cf170065_CourierOperationsImplementation().insertCourier(userName, driversLicene));
               flag= flag&& deleteCourierRequest(userName);
               
               if(!flag)conn.rollback();
               else conn.commit();
               return flag;
               
                
                
                
                
                
            } catch (SQLException ex) {
                conn.setAutoCommit(true);
                Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierRequestOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
      return false;
     }
    
}
