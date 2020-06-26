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
        List<String> allcouriers = PackageRoutes.getInstance().getCourierOperations().getAllCouriers();
        if(allcouriers.contains(username)) return false;
        
         if(PackageRoutes.getInstance().getCourierRequestOperation().checkExistsDriversLicence(driversLicence, username)) return false;
       
        String sql = "insert into CourierRequests(userName, driversLicence) values(?,?)";
        Connection conn = DB.get_instance();
        try (
            PreparedStatement query = conn.prepareStatement(sql);
                )
            {
                query.setString(1, username);
                query.setString(2, driversLicence);
                return query.executeUpdate()==1;
                
        } catch (SQLException ex) {
//            Logger.getLogger(cf170065_CourierRequestOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean checkExistsDriversLicence(String driversLicence, String username){
      Connection conn = DB.get_instance();
      String sql = "select * from courierRequests where driversLicence = ? and username <> ?";
            String sql2 = "select * from courier where dirversLicence = ? and username <> ?";
        try (     PreparedStatement q = conn.prepareStatement(sql);
                PreparedStatement q2 = conn.prepareStatement(sql2);
                ){
            q.setString(1, driversLicence);
            q2.setString(1, driversLicence);
            q.setString(2, username);
            q2.setString(2, username);
            
            ResultSet rs1 = q.executeQuery();
            ResultSet rs2 = q2.executeQuery();
            if(rs1.next() || rs2.next()) return true;
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
    public boolean changeDriverLicenceNumberInCourierRequest(String userName, String drivingLicence) {
        //OVO JE GRESKA?
         if(PackageRoutes.getInstance().getCourierRequestOperation().checkExistsDriversLicence(drivingLicence,  userName)) return false;
       
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
            
            
            String getDriversLicence = "select driversLicence from  CourierRequests where userName = ?";
            Connection conn = DB.get_instance();
            conn.setAutoCommit(false);
            try (PreparedStatement query = conn.prepareStatement(getDriversLicence);){
                query.setString(1,userName);
                ResultSet result = query.executeQuery();
                String driversLicene = null;
                if(result.next())
                    driversLicene= result.getString(1);
                else throw new SQLException("isforsiran izuzetak");
                
               boolean flag= true;
               flag= flag&& deleteCourierRequest(userName);
               flag= flag&& (new cf170065_CourierOperationsImplementation().insertCourier(userName, driversLicene));
               
               
               if(!flag)conn.rollback();
               else conn.commit();
               return flag;
               
                
                
                
                
                
            } catch (SQLException ex) {
                conn.setAutoCommit(true);
               // Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (SQLException ex) {
        //    Logger.getLogger(cf170065_CourierRequestOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
      return false;
     }
    
}
