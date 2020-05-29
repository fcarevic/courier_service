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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.DriveOperation;
import rs.etf.sab.operations.VehicleOperations;

/**
 *
 * @author CAR
 */
public class cf170065_DriveOperationsImplementation implements DriveOperation{

    @Override
    public boolean planingDrive(String username) {
               
               PackageRoutes pr= PackageRoutes.getInstance();
               
               List<String> couriers = pr.getCourierOperations().getCouriersWithStatus(0);
                       
               if(!couriers.contains(username))return false; //checks if it is courier
               int idCity = pr.getUserOperations().getUsersCity(username);
               List<String> availableVehicles = pr.getVehicleOperations().getAllVehiclesParkedInCity(idCity);
               if(availableVehicles.isEmpty()) return false;
               String myVehicle = availableVehicles.remove(0);
               
               List<Integer> allPackages = pr.getPackageOperations().getAllPackagesCurrentlyAtCity(idCity);
               //TODO: izmeni Packages operations implementation
               if(allPackages==null || allPackages.isEmpty() ) return false;
               pr.getVehicleOperations().unparkVehicle(myVehicle);
               List<Integer> pickedPackages = new LinkedList<Integer>();
               BigDecimal capacity = pr.getVehicleOperations().getCapacity(myVehicle);
               BigDecimal currentCap = new BigDecimal(0);
               while(!allPackages.isEmpty()){
                    int idPackage = allPackages.remove(0);
                    BigDecimal packageWeight = pr.getPackageOperations().getWeight(idPackage); 
                    if(currentCap.add(packageWeight).compareTo(capacity)>0) continue;
                     currentCap= currentCap.add(packageWeight);
                     
                     pickedPackages.add(idPackage);
                     
               }
               
               for(int packageId: pickedPackages){
                           if(! pr.getPackageOperations().insertPackageInVehicle(packageId, myVehicle))
                               System.out.println("rs.etf.sab.student.cf170065_DriveOperationsImplementation.planingDrive() NIJE UBACEN PAKET U VOZILO");
                           if(! pr.getPackageOperations().changeStatus(packageId, 2))
                               System.out.println("rs.etf.sab.student.cf170065_DriveOperationsImplementation.planingDrive() NIJE PROMENJEN STATUS PAKETA");
                }
              if(!pr.getCourierOperations().changeCouriersStatus(username, 1))System.out.println("rs.etf.sab.student.cf170065_DriveOperationsImplementation.planingDrive() NIJE PROMENJEN STATUS KURIRA");;
               if(!pr.getCourierOperations().assignVehicle(username, myVehicle)) System.out.println("rs.etf.sab.student.cf170065_DriveOperationsImplementation.planingDrive() NIJE DODELJENO VOZILO");;
               return true;
               
         }

    @Override
    public int nextStop(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> getPackagesInVehicle(String username) {
        String sql = "Select idPackage from PackageInVehicle where PackageInVehicle.registrationNum = Courier.currentlyDriving and Coruier.userName= ?";
        Connection conn = DB.get_instance();
        List<Integer> list= new LinkedList<>();
        
        try {
                PreparedStatement query = conn.prepareStatement(sql);
                   query.setString(1, username);
                   ResultSet rs= query.executeQuery();
                   while(rs.next()){
                       list.add(rs.getInt(1));
                   }
        } catch (SQLException ex) {
            Logger.getLogger(cf170065_DriveOperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return list;
    }
    
}
