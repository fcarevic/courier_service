/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.GeneralOperations;

/**
 *
 * @author CAR
 */
public class cf170065_GeneralOperationsImplementation implements GeneralOperations{

    @Override
    public void eraseAll() {
        try {
            String sql = "{ call sp_eraseAll()}";
            Connection conn = DB.get_instance();
            CallableStatement cs = conn.prepareCall(sql);
            cs.execute();
            cs.close();
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_GeneralOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    
}
