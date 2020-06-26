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
import rs.etf.sab.operations.StockroomOperations;

/**
 *
 * @author CAR
 */
public class cf170065_StockroomOperationsImplementation implements StockroomOperations {

    @Override
    public int insertStockroom(int adress) {
        
        List<Integer> allAdresses= getStocroomAdressesFromCity(PackageRoutes.getInstance().getAddressOperations().getCityId(adress));
        if(!allAdresses.isEmpty()) return -1;

        String sql = "insert into Stockroom (idAdress) values (?)";
        Connection conn = DB.get_instance();
       try( PreparedStatement query = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);)
       {
            query.setInt(1, adress);
            if (query.executeUpdate()==1)
            {
                ResultSet generatedKeys = query.getGeneratedKeys();
                if(generatedKeys.next()) 
                        return generatedKeys.getInt(1);
            }
            
            
       } catch (SQLException ex) {
            Logger.getLogger(cf170065_StockroomOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
              return -1;
    }

    public List<Integer> getStocroomAdressesFromCity(int idCity){
                    String sql = "Select idAdress from Stockroom where idAdress in (Select idAdress from Adress where idCity=?)";
                    Connection conn = DB.get_instance();
                    LinkedList<Integer> list = new LinkedList<>();
        try (    PreparedStatement query= conn.prepareStatement(sql);
       ) {
            query.setInt(1, idCity);
            ResultSet rs =query.executeQuery();
            while(rs.next()){
                list.add(rs.getInt(1));
            }
            
         } catch (SQLException ex) {
            Logger.getLogger(cf170065_StockroomOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
                    
    }
    @Override
    public boolean deleteStockroom(int stockroom_id) {
           
           String sql = "delete from Stockroom where idStockroom = ? and not exists(select * from Package where currently_atAdress = Stockroom.idAdress "+
                    "and not exists( Select * from PackageInVehicle where Package.idPackage=PackageInVehicle.idPackage )) and not exists (select * from parked where parked.idStockroom = Stockroom.idStockroom)";
        Connection conn = DB.get_instance();
       try( PreparedStatement query = conn.prepareStatement(sql);)
       {
            query.setInt(1, stockroom_id);
             return query.executeUpdate()==1;
            
            
       } catch (SQLException ex) {
            Logger.getLogger(cf170065_StockroomOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
              return false;
    }
    

    @Override
    public int deleteStockroomFromCity(int cityId) {
        
        List<Integer> allAdresses = getStocroomAdressesFromCity(cityId);
        List<Integer> allIds = new LinkedList<>();
        for(int i : allAdresses){
            allIds.add(getStockroomOnAdress(i));
        }
        if(allIds.isEmpty()) return -1;
        if(deleteStockroom(allIds.get(0))) return allIds.remove(0);
        return allIds.remove(0); //treba li ovo???
    
    }

    @Override
    public List<Integer> getAllStockrooms() {
     List<Integer> list= new LinkedList<>();
            String sql = "select idStockroom from Stockroom";
            Connection conn = DB.get_instance();
        try (Statement query = conn.createStatement();){
             ResultSet result = query.executeQuery(sql);
             while(result.next())
                 list.add(result.getInt(1));
             
             return list;
                
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_CityOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list; }
    
    
    public int getStockroomOnAdress(int idAdress){
        String sql= "Select idStockroom from Stockroom where idAdress = ?";
        Connection conn = DB.get_instance();
        try (    PreparedStatement query = conn.prepareStatement(sql);
      ){
            query.setInt(1, idAdress);
            ResultSet rs = query.executeQuery();
            if(rs.next()) return rs.getInt(1);
          } catch (SQLException ex) {
            Logger.getLogger(cf170065_StockroomOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    
    }
    
    
}
