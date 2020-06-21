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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.UserOperations;

/**
 *
 * @author CAR
 */
public class cf170065_UserOperationsImplementation implements UserOperations{

    @Override
    public boolean insertUser(String userName, String firstName, String lastName, String password, int idAddress) {
        try {
            if(!Character.isUpperCase(firstName.charAt(0))) return false;
            if(!Character.isUpperCase(lastName.charAt(0))) return false;
            if(password.length()!=8) return false;
            boolean upper=false, lower = false, number = false, character= false;
            for(int i = 0 ; i < password.length(); i++){
                char c= password.charAt(i);
                if (Character.isAlphabetic(c)){
                    if(Character.isDigit(c)) number=true;
                    else if (Character.isLowerCase(c)) lower= true;
                    else if (Character.isUpperCase(c)) upper= true;
                    else  character=true;
                } else System.err.println("NIJE LETTER");
                
            }String sql = "insert into User(userName, firstName,lastName, password, idAdress) value(?,?,?,?,?)";
            Connection conn = DB.get_instance();
            PreparedStatement query = conn.prepareStatement(sql);
            query.setString(1,userName);
            query.setString(2,firstName);
            query.setString(3,lastName);
            query.setString(4,password);
            query.setInt(5,idAddress);
            
            return query.executeUpdate()==1;
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_UserOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
              
                return false;
        
        
    }

    @Override
    public boolean declareAdmin(String username) {
        String sql = "insert into Administrator(userName) value(?)";
        Connection conn = DB.get_instance();
        try (PreparedStatement query = conn.prepareStatement(sql);){
            
            query.setString(1, username);
            
            return( query.executeUpdate()==1) ;
            
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_UserOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }

    @Override
    public int getSentPackages(String... usernames) {
        int count = 0;
        boolean postojiKorisnik=false;
        String sql = "select count(*) from PackageRequest where userName = ?";

        Connection conn = DB.get_instance();
        List<String> allUsers= getAllUsers();
        for(String user:usernames) {
            if(!allUsers.contains(user)) continue;
             postojiKorisnik=true;
            try (PreparedStatement query = conn.prepareStatement(sql);){
                query.setString(1, user);
                ResultSet result = query.executeQuery();
                if(result.next())
                        count+= result.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(cf170065_UserOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        }
        if(!postojiKorisnik) return -1;
        return count;
     }

    @Override
    public int deleteUsers(String... usernames) {
        int count = 0; 
        String sql = "delete from User where userName  in ?";
        String delBuyer = "delete  from Buyer where userName= ?";
         String delAdmin = "delete  from Administrator where userName= ?";
          
        Connection conn = DB.get_instance();
            for(String username: usernames){
        try ( PreparedStatement query = conn.prepareStatement(sql);
                PreparedStatement delBuyerQ = conn.prepareStatement(delBuyer);
                PreparedStatement delAdminQ = conn.prepareStatement(delAdmin);
                ) {
                    delBuyerQ.setString(1, username);
                    delAdminQ.setString(1,username);
                    
                    delBuyerQ.executeUpdate();
                    delAdminQ.executeUpdate();
                    
                    new cf170065_CourierOperationsImplementation().deleteCourier(username);
                    
                    count +=query.executeUpdate();
                    
            
              
           
            
           
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_UserOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
            return count;
    }

    @Override
    public List<String> getAllUsers() {
           List<String> list= new LinkedList<>();
                String sql = "select username from User";
            Connection conn = DB.get_instance();
        try(      PreparedStatement query = conn.prepareStatement(sql);
               
     ) {
             
             ResultSet result= query.executeQuery();
             while( result.next())
                 list.add(result.getString(1));
            
         } catch (SQLException ex) {
            Logger.getLogger(cf170065_CourierOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
            
     return list; 
    }
    
    public int getUsersCity(String username){
        try {
            String sql = "Select Adress.idCity from Adress,User where User.userName = ? and User.idAdress = Adress.idAdress ";
            Connection conn = DB.get_instance();
            PreparedStatement query = conn.prepareStatement(sql);
            query.setString(1, username);
            ResultSet rs=  query.executeQuery();
            if(rs.next())
                return rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_UserOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

 
    
}
