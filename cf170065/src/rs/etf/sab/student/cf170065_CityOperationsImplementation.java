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
        String sql2 = "select * from city where  postalCode = ?";
         String sql = "insert into City(name, postalCode) values(?,?)";
            Connection conn = DB.get_instance();
        
         
        try ( PreparedStatement q = conn.prepareStatement(sql2);
                PreparedStatement query = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
                query.setString(1, name);
                query.setString(2, postalCode);
              q.setString(1, postalCode);
               ResultSet check = q.executeQuery();
               if(check.next()) return -1;
               query.executeUpdate();
               ResultSet rs= query.getGeneratedKeys();
               if(rs.next())return rs.getInt(1);
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
        
     }

    @Override
    public int deleteCity(String... names) {
        int count=0;
             String sql = "delete from City where name= ?";
            Connection conn = DB.get_instance();
    
        
           try (PreparedStatement query = conn.prepareStatement(sql);){
               for(String name : names){ query.setString(1, name);
                
                
                count += query.executeUpdate();
                
               }
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        
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
