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
import rs.etf.sab.operations.CityOperations;

/**
 *
 * @author CAR
 */
public class cf170065_CityOperationsImplementation implements CityOperations{

    @Override
    public int insertCity(String name, String postalCode) {
         String sql = "insert into City(name, postalCode) values(?,?)";
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
                query.setString(1, name);
                query.setString(2, postalCode);
                
                return query.executeUpdate();
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        
     }

    @Override
    public int deleteCity(String... names) {
        int count=0;
             String sql = "delete from City where name= ?";
            Connection conn = DB.get_instance();
    
        for(String name : names){
           try (PreparedStatement query = conn.prepareStatement(sql);){
                query.setString(1, name);
                
                
                count += query.executeUpdate();
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        return count;
     }

    @Override
    public boolean deleteCity(int id_city) {
            String sql = "delete from City where idCity = ?";
            Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
               query.setInt(1, id_city);
                return query.executeUpdate()==1;
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
     }

    @Override
    public List<Integer> getAllCities() {
         List<Integer> list= new LinkedList<>();
            String sql = "select idCity from City";
            Connection conn = DB.get_instance();
        try (Statement query = conn.createStatement();){
             ResultSet result = query.executeQuery(sql);
             while(result.next())
                 list.add(result.getInt(1));
             
             return list;
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }
    
}
