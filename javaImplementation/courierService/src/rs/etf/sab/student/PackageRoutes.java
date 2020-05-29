/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import rs.etf.sab.operations.AddressOperations;
import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.operations.CourierOperations;
import rs.etf.sab.operations.CourierRequestOperation;
import rs.etf.sab.operations.DriveOperation;
import rs.etf.sab.operations.PackageOperations;
import rs.etf.sab.operations.StockroomOperations;
import rs.etf.sab.operations.UserOperations;
import rs.etf.sab.operations.VehicleOperations;

/**
 *
 * @author CAR
 */
public class PackageRoutes {
    private static PackageRoutes instance;
    private Map<String, LinkedList<Integer>> couriersPackages = new HashMap<>();
    private cf170065_PackageOperationsImplementation packageOperations = new cf170065_PackageOperationsImplementation();
    private cf170065_CourierOperationsImplementation courierOperations = new cf170065_CourierOperationsImplementation();
    private cf170065_CourierRequestOperationsImplementation courierRequestOperation = new cf170065_CourierRequestOperationsImplementation();
    private cf170065_AdressOperationsImplementation addressOperations = new cf170065_AdressOperationsImplementation();
    private cf170065_CityOperationsImplementation cityOperations = new cf170065_CityOperationsImplementation();
    private cf170065_DriveOperationsImplementation driveOperation = new cf170065_DriveOperationsImplementation();
    private cf170065_StockroomOperationsImplementation stockroomOperations = new cf170065_StockroomOperationsImplementation();
    private cf170065_UserOperationsImplementation userOperations = new cf170065_UserOperationsImplementation();
    private cf170065_VehicleOperationsImplementation vehicleOperations= new cf170065_VehicleOperationsImplementation();
    private PackageRoutes(){}
   

    public static PackageRoutes getInstance() {
        if(instance==null) instance= new PackageRoutes();
        return instance;
    }

   

    public Map<String, LinkedList<Integer>> getCouriersPackages() {
        return couriersPackages;
    }

   
    public cf170065_PackageOperationsImplementation getPackageOperations() {
        return packageOperations;
    }

   
    public cf170065_CourierOperationsImplementation getCourierOperations() {
        return courierOperations;
    }

   
    public cf170065_CourierRequestOperationsImplementation getCourierRequestOperation() {
        return courierRequestOperation;
    }

   
    public cf170065_AdressOperationsImplementation getAddressOperations() {
        return addressOperations;
    }

   
    public cf170065_CityOperationsImplementation getCityOperations() {
        return cityOperations;
    }

   
    public cf170065_DriveOperationsImplementation getDriveOperation() {
        return driveOperation;
    }

   
    public cf170065_StockroomOperationsImplementation getStockroomOperations() {
        return stockroomOperations;
    }

   
    public cf170065_UserOperationsImplementation getUserOperations() {
        return userOperations;
    }

   
    public cf170065_VehicleOperationsImplementation getVehicleOperations() {
        return vehicleOperations;
    }

    
    
    
}
