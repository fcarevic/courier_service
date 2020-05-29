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
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.VehicleOperations;

/**
 *
 * @author CAR
 */
public class cf170065_VehicleOperationsImplementation implements VehicleOperations {

    @Override
    public boolean insertVehicle(String licencePlateNumber, int fuelType, BigDecimal fuelConsumption, BigDecimal capacity) {
        String sql = "select * from Vehicle";
        Connection conn = DB.get_instance();
        try ( Statement query = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);){
            ResultSet result = query.executeQuery(sql);
            result.moveToInsertRow();
            result.updateString("registrationNum", licencePlateNumber);
            result.updateInt("fuelType", fuelType);
            result.updateBigDecimal("consumption", fuelConsumption);
            result.updateBigDecimal("capacity", capacity);
            result.insertRow();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_VehicleOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
             return false;
      }

    @Override
    public int deleteVehicles(String... licencePlates) {
        int count = 0;
         String sql = "delete from Vehicle where registrationNum = ?";
        Connection conn = DB.get_instance();
        for(String licencePlate : licencePlates){
        try ( PreparedStatement query = conn.prepareStatement(sql);){
            
            query.setString(1, licencePlate);
            count += query.executeUpdate();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_VehicleOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
        return count;
       
    }

    @Override
    public List<String> getAllVehichles() {
           List<String> list= new LinkedList<>();
                String sql = "select registrationNum from Vehicle";
            Connection conn = DB.get_instance();
        try(      PreparedStatement query = conn.prepareStatement(sql);
               
     ) {
             
             ResultSet result= query.executeQuery();
             while( result.next())
                 list.add(result.getString(1));
            
         } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
            
     return list;   }

    @Override
    public boolean changeFuelType(String regNum, int fuelType) {
    String sql = "update  Vehicle set fuelType = ? where registrationNum = ?";
        Connection conn = DB.get_instance();
        try (
            PreparedStatement query = conn.prepareStatement(sql);
                )
            {
                query.setString(2, regNum);
                 query.setInt(1, fuelType);
                
                return query.executeUpdate()==1;
                
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierRequestOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;   }

    @Override
    public boolean changeConsumption(String regNum, BigDecimal consumption) {
     String sql = "update  Vehicle set consumption = ? where registrationNum = ?";
        Connection conn = DB.get_instance();
        try (
            PreparedStatement query = conn.prepareStatement(sql);
                )
            {
                query.setString(2, regNum);
                 query.setBigDecimal(1, consumption);
                
                return query.executeUpdate()==1;
                
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierRequestOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; 
    }

    @Override
    public boolean changeCapacity(String regNum, BigDecimal cap) {
          String sql = "update  Vehicle set capacity = ? where registrationNum = ?";
        Connection conn = DB.get_instance();
        try (
            PreparedStatement query = conn.prepareStatement(sql);
                )
            {
                query.setString(2, regNum);
                 query.setBigDecimal(1, cap);
                
                return query.executeUpdate()==1;
                
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierRequestOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
   
    }

    @Override
    public boolean parkVehicle(String licencePlate, int idStockroom) {
        try {
            String sql = "Insert into Parked(idStockroom, registrationNum) value(?,?) ";
            Connection conn= DB.get_instance();
            PreparedStatement query = conn.prepareStatement(sql);
            query.setInt(1, idStockroom);
            query.setString(2, licencePlate);
            return query.executeUpdate()==1;
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_VehicleOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
     
    
    }
    public boolean unparkVehicle(String licencePlate){
        try {
            String sql = "Delete from Parked where registrationNum = ?";
            Connection conn= DB.get_instance();
            PreparedStatement query = conn.prepareStatement(sql);
            query.setString(1, licencePlate);
            return query.executeUpdate()==1;
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_VehicleOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public List<String> getAllVehiclesParkedInCity(int idCity){
        LinkedList<String> list= new LinkedList<>();
        try {
            
            String sql = "Select registationNum from Parked, Stockroom, Adress where Adress.idCity = ? and Adress.idAdress= Stockroom.idAdress and Parked.idStockroom = Stockroom.idStockroom ";
            Connection conn = DB.get_instance();
            PreparedStatement query= conn.prepareStatement(sql);
            query.setInt(1, idCity);
            ResultSet rs= query.executeQuery();
            while(rs.next()){
             list.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_VehicleOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
       return list;
     
    }

    BigDecimal getCapacity(String myVehicle) {
        try {
            String sql = "Select capacity from Vehicle where registrationNum = ?";
            Connection conn= DB.get_instance();
            PreparedStatement query = conn.prepareStatement(sql);
            query.setString(1, myVehicle);
            ResultSet rs= query.executeQuery();
            if(rs.next())
                return rs.getBigDecimal(1);
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_VehicleOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }
    
    
}
