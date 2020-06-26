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
import javafx.util.Pair;
import rs.etf.sab.operations.AddressOperations;

/**
 *
 * @author CAR
 */
public class cf170065_AdressOperationsImplementation implements AddressOperations {
  

    @Override
    public int insertAddress(String street, int number, int city_id, int xcord, int ycord) {
          String sql = "insert into Adress(xCord, yCord, street, number,idCity)"
                    + "values (?,?,?,?,?)";
          String sql2 = "Select * from Adress where xCord = ? and yCord=?";
            Connection conn = DB.get_instance();
            int new_id=-1;
        try(
                PreparedStatement query = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS );
                PreparedStatement q = conn.prepareStatement(sql2)) {
          
          q.setInt(1, xcord);
            q.setInt(2, ycord);
             ResultSet check = q.executeQuery();
            if(check.next()) return -1;
            query.setInt(1, xcord);
            query.setInt(2, ycord);
            query.setString(3, street);
            query.setInt(4, number);
            query.setInt(5, city_id);
            
             query.executeUpdate();
            ResultSet generatedKeys = query.getGeneratedKeys();
            
            if(generatedKeys.next())
                new_id= generatedKeys.getInt(1);
             generatedKeys.close();
           
        } catch (SQLException ex) {
            //Logger.getLogger(cf170065_AdressOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
       return new_id;
    
    
    }

    @Override
    public int deleteAddresses(String street, int number) {
        String sql = "delete from Adress where street = ? and number = ?";
        Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql)){
            query.setString(1, street);
            query.setInt(2, number);
            return query.executeUpdate();
            
        } catch (Exception e) {
        }
        return 0;
     }

    @Override
    public boolean deleteAdress(int id_address) {
        String sql = "delete from Adress where idAdress = ?";
        Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql)){
            query.setInt(1, id_address);
            return query.executeUpdate()==1;
        } catch (Exception ex) {
                   Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
     
        }
        return false;
    }

    @Override
    public int deleteAllAddressesFromCity(int id_city) {
        //NECE RADITI ZATO STO NEMA CASCADE!
         Connection conn = DB.get_instance();
            String sql = "Delete from Adress where idCity = ?";
           
        try (PreparedStatement query= conn.prepareStatement(sql);
           ){
             query.setInt(1, id_city);
            return query.executeUpdate();
            
          
            
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_AdressOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        
     }

    @Override
    public List<Integer> getAllAddressesFromCity(int city_id) {
        List<Integer> list= new LinkedList<>();
        String sql = "select idAdress from Adress where idCity = ?";
        Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql)) {
            query.setInt(1, city_id);
            ResultSet result= query.executeQuery();
            while (result.next()){
              list.add( result.getInt(1));
            }
            
            if(!list.isEmpty()) return list;
            
        } catch (Exception ex) {
                   Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
     
        }
        return null;
     }

    @Override
    public List<Integer> getAllAddresses() {
        List<Integer> list= new LinkedList<>();
        String sql = "select idAdress from Adress ";
        Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql)) {
           
            ResultSet result= query.executeQuery();
            while (result.next()){
              list.add( result.getInt(1));
            }
            
         //   if(!list.isEmpty()) 
                return list;
            
        } catch (Exception ex) {
                   Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
     
        }
        return list;
        
    }
    public int getCityId(int idAdress){
        Connection conn= DB.get_instance();
        String sql = "Select idCity from Adress where idAdress = ?";
        try( PreparedStatement query = conn.prepareStatement(sql);) {
           query.setInt(1, idAdress);
           ResultSet rs= query.executeQuery();
           if(rs.next()){
           return rs.getInt(1);
           }
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_AdressOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
           return -1;     
    
    }
    public Pair<Integer, Integer> getCoordinatesOfAdress(int idAdress){
        String sql = "Select xCord, yCord from Adress where idAdress = ?";
        Connection conn = DB.get_instance();
        try(     PreparedStatement query = conn.prepareStatement(sql);
       ) {
            query.setInt(1, idAdress);
            ResultSet rs = query.executeQuery();
            if(rs.next()){
                return new Pair(new Integer(rs.getInt(1)), new Integer(rs.getInt(2)));
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_AdressOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
               
    
    
    }
    
}
