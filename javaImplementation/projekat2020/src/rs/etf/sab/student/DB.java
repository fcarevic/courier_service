/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CAR
 */
public class DB {
    
    private static final String url= "jdbc:sqlserver://localhost:1433;database=projekat2020;";
    private static final String user = "filip";
    private static final String password= "1234";
    private static DB instance;
    private Connection conn;
    
    private DB () throws SQLException{
    conn = DriverManager.getConnection(url,user,password);
    }
    
    public static Connection get_instance(){
        try {
            if(instance==null) instance= new DB();
            return instance.conn;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
  
    
}
