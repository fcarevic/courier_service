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
        String sql = "insert into Stockroon (idAdress) value (?)";
        Connection conn = DB.get_instance();
       try( PreparedStatement query = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);)
       {
            query.setInt(1, adress);
            if (query.execute())
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

    @Override
    public boolean deleteStockroom(int stockroom_id) {
           String sql = "delete from Stockroon where idStockroom = ?";
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
    public int deleteStockroomFromCity(int arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    
}
