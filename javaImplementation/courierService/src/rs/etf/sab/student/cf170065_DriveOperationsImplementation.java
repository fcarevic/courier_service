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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import rs.etf.sab.operations.DriveOperation;
import rs.etf.sab.operations.VehicleOperations;

/**
 *
 * @author CAR
 */
public class cf170065_DriveOperationsImplementation implements DriveOperation{
    private static final int TO_DELIVER=1;
    private static final int TO_COLLECT=0;
    private static final int CREATED=0, ACCEPTED=1,COLLECTED=2,DELIVERED=3,REJECTED=4 ,RETURN_TO_BEGINING=5;
    private static final int[] fuelPrice={15,36,32};
    private static final int GAS=0, DIESEL=2, PETROL=1; 

    @Override
    public boolean planingDrive(String username) {
               
               PackageRoutes pr= PackageRoutes.getInstance();
               
               List<String> couriers = pr.getCourierOperations().getCouriersWithStatus(0);
                       
               if(!couriers.contains(username))return false; //checks if it is courier
               int idCity = pr.getUserOperations().getUsersCity(username);
               List<String> availableVehicles = pr.getVehicleOperations().getAllVehiclesParkedInCity(idCity);
               if(availableVehicles.isEmpty()) return false;
               String myVehicle = availableVehicles.remove(0);
                 BigDecimal capacity = pr.getVehicleOperations().getCapacity(myVehicle);
               List<PackagePlanInfo> pickedPackages = new LinkedList<PackagePlanInfo>();
               BigDecimal currentCap = new BigDecimal(0);
                  currentCap =planRouteofCollectingPackages(idCity, pickedPackages, capacity, currentCap);
                  if(currentCap.compareTo(new BigDecimal(0))==0) return false;
                  int stockroomAdress = pr.getStockroomOperations().getStocroomAdressesFromCity(idCity).remove(0);
                 List<PackagePlanInfo> deliveringList = new LinkedList<>();
                 
                       for(PackagePlanInfo pi: pickedPackages){
                 PackagePlanInfo newpi= new PackagePlanInfo(pi.getIdPackage(), pi.getIdAdressFrom(), 
                                                new Pair<Integer,Integer>(pi.getxCordFrom(), pi.getyCordFrom()));
                 int toAdress = pr.getPackageOperations().getAdressTo(pi.getIdPackage());
                 Pair<Integer, Integer> cordsTo = pr.getAddressOperations().getCoordinatesOfAdress(toAdress);
                 newpi.setInfoTo(toAdress, cordsTo);
                 newpi.setToDeliver(true);
                 newpi.setStatus(TO_DELIVER);
                 deliveringList.add(newpi);
                 }
          
                  sortRoute(stockroomAdress, deliveringList);
                 
                 LinkedList<PackagePlanInfo> collecting = new LinkedList<>();
                    LinkedList<PackagePlanInfo> route = new LinkedList<PackagePlanInfo>(pickedPackages);
              
                 for(int i = deliveringList.size()-1; i>=0;i-- ){
                     PackagePlanInfo pi = deliveringList.get(i);
                     
                 int idNewCity = pr.getAddressOperations().getCityId(pi.getIdAdressTo());
                 collecting.clear();
                 currentCap= planRouteofCollectingPackages(idNewCity, collecting, capacity, currentCap);
                 if(!collecting.isEmpty()){
                     route.addAll(route.indexOf(pi)+1, collecting);
                    }
                
                 }
                
                 PackagePlanInfo end = new PackagePlanInfo(-1, stockroomAdress, pr.getAddressOperations().getCoordinatesOfAdress(stockroomAdress));
                 end.setStatus(RETURN_TO_BEGINING);
                route.addAll(0, pickedPackages);
                route.add(end);
                 RouteInfo ri = new RouteInfo();
                 ri.setRoute(route);
                 ri.setCurrentIdAdress(stockroomAdress);
                Pair<Integer, Integer> stockCoords = pr.getAddressOperations().getCoordinatesOfAdress(stockroomAdress);
                 ri.setCoords(stockCoords);
                 ri.setTotalPriceOfDelivery(new BigDecimal(0));
                  pr.getCouriersPackages().put(username, ri);
                  
               
             
                 pr.getVehicleOperations().unparkVehicle(myVehicle);
           
              
              if(!pr.getCourierOperations().changeCouriersStatus(username, 1))System.out.println("rs.etf.sab.student.cf170065_DriveOperationsImplementation.planingDrive() NIJE PROMENJEN STATUS KURIRA");;
               if(!pr.getCourierOperations().assignVehicle(username, myVehicle)) System.out.println("rs.etf.sab.student.cf170065_DriveOperationsImplementation.planingDrive() NIJE DODELJENO VOZILO");;
               return true;
               
         }
    
    private void sortRoute(int startingAdress,List<PackagePlanInfo> route){
    PackageRoutes pr = PackageRoutes.getInstance();
         Pair<Integer,Integer> startCords = pr.getAddressOperations().getCoordinatesOfAdress(startingAdress);
         int lastX = startCords.getKey();
         int lastY=startCords.getValue();
         
         List<PackagePlanInfo> sorted = new LinkedList<>();
         while(!route.isEmpty()){
               double minDist = Math.hypot(lastX - route.get(0).getxCordTo(), lastY  - route.get(0).getyCordTo());
         int ind=0;
       
            for(int i= 0 ; i< route.size();i++){
             PackagePlanInfo pi = route.get(i);
             double dist = Math.hypot(lastX-pi.getxCordTo(), lastY- pi.getyCordTo());
            if(minDist>dist){
            ind = i ;
             minDist=dist;
                }
            }  
        PackagePlanInfo remove = route.remove(ind);
        sorted.add(remove);
         }
         
         
         while(!sorted.isEmpty())route.add(sorted.remove(0));
         
    
    }
    private boolean checkIfPackageBelongsToAnyRoute(int idPackage){
        PackageRoutes pr = PackageRoutes.getInstance();
        Map<String,RouteInfo> map = pr.getCouriersPackages();
        for(RouteInfo routeInfo : map.values()){
            
                for(PackagePlanInfo pi: routeInfo.getRoute()){
                    if(pi.getIdPackage()==idPackage)return true;
                
                
                }
        }
        return false;
        
    
    
    }
    private  BigDecimal planRouteofCollectingPackages(int idCity, List<PackagePlanInfo> pickedPackages, BigDecimal vehicleCapacity, BigDecimal currentCap){
                PackageRoutes pr = PackageRoutes.getInstance();
                
               List<Integer> uncollectedPackages = pr.getPackageOperations().getUncollectedPackagesFromCity(idCity);
               List<Integer> packagesInStockroom = pr.getPackageOperations().getPackagesFromStockroomInCity(idCity);
               //TODO: izmeni Packages operations implementation
               if(packagesInStockroom.isEmpty()&& uncollectedPackages.isEmpty() ) return currentCap;
                while(!uncollectedPackages.isEmpty()){
                    
                    int idPackage = uncollectedPackages.remove(0);
                    BigDecimal packageWeight = pr.getPackageOperations().getWeight(idPackage); 
                    if(currentCap.add(packageWeight).compareTo(vehicleCapacity)>0) continue;
                    if(checkIfPackageBelongsToAnyRoute(idPackage)) continue;
                     currentCap= currentCap.add(packageWeight);
                     int idAdress = pr.getPackageOperations().getCurrentLocationOfPackage(idPackage);
                     Pair<Integer, Integer> cords = pr.getAddressOperations().getCoordinatesOfAdress(idAdress);
                     
                     pickedPackages.add(new PackagePlanInfo(idPackage, idAdress, cords));
                     
               }
             while(!packagesInStockroom.isEmpty()){
                    int idPackage = packagesInStockroom.remove(0);
                    BigDecimal packageWeight = pr.getPackageOperations().getWeight(idPackage); 
                    if(currentCap.add(packageWeight).compareTo(vehicleCapacity)>0) continue;
                   if(checkIfPackageBelongsToAnyRoute(idPackage)) continue;
                   
                    currentCap= currentCap.add(packageWeight);
                     int idAdress = pr.getPackageOperations().getCurrentLocationOfPackage(idPackage);
                     Pair<Integer, Integer> cords = pr.getAddressOperations().getCoordinatesOfAdress(idAdress);
                     
                     pickedPackages.add(new PackagePlanInfo(idPackage, idAdress, cords));
               
             
             }  
             return currentCap;
    }
    
    
    
    @Override
    public int nextStop(String username) {
        
        RouteInfo ri = PackageRoutes.getInstance().getCouriersPackages().get(username);
        if(ri==null) return -1;
        if(ri.getRoute().isEmpty())return -1;
        PackagePlanInfo  pi = ri.getRoute().remove(0);
        if(pi.getStatus()==TO_COLLECT){
            PackageRoutes.getInstance().getPackageOperations().changeStatus(pi.getIdPackage(), COLLECTED);
            PackageRoutes.getInstance().getPackageOperations().insertPackageInVehicle(pi.getIdPackage(), PackageRoutes.getInstance().getCourierOperations().getCurrentlyDrivingVehicle(username));
            ri.setCurrentIdAdress(pi.getIdAdressFrom());
            ri.setDistancepassed(ri.getDistancepassed()+Math.hypot(pi.getxCordFrom() - ri.getXcordl(), pi.getyCordFrom()- ri.getYcord()));
            ri.setXcordl(pi.getxCordFrom());
            ri.setYcord(pi.getyCordFrom());
            
            return -2;
        }
        else if(pi.getStatus()==TO_DELIVER){
                
          PackageRoutes.getInstance().getPackageOperations().changeStatus(pi.getIdPackage(), DELIVERED);
          PackageRoutes.getInstance().getPackageOperations().removePackageFromVehicle(pi.getIdPackage(), PackageRoutes.getInstance().getCourierOperations().getCurrentlyDrivingVehicle(username));
          PackageRoutes.getInstance().getCourierOperations().incrementNumberOfDeliveredPackages(username, 1);
           ri.setDistancepassed(ri.getDistancepassed()+Math.hypot(pi.getxCordTo() - ri.getXcordl(), pi.getyCordTo()- ri.getYcord()));
            ri.setXcordl(pi.getxCordTo());
            ri.setYcord(pi.getyCordTo());
           ri.setTotalPriceOfDelivery(ri.getTotalPriceOfDelivery().add(PackageRoutes.getInstance().getPackageOperations().getPriceOfDelivery(pi.getIdPackage())));
           
        
            return pi.getIdPackage();
        }else if(pi.getStatus()==RETURN_TO_BEGINING){
           
              ri.setDistancepassed(ri.getDistancepassed()+Math.hypot(pi.getxCordFrom() - ri.getXcordl(), pi.getyCordFrom()- ri.getYcord()));
            ri.setXcordl(pi.getxCordFrom());
            ri.setYcord(pi.getyCordFrom());
            String licencePlate= PackageRoutes.getInstance().getCourierOperations().getCurrentlyDrivingVehicle(username);
            List<Integer> packagesInVehicle = getPackagesInVehicle(licencePlate);
            
            for(int idPackage : packagesInVehicle){
                PackageRoutes.getInstance().getPackageOperations().setCurrentLocation(idPackage, pi.getIdAdressFrom());
                
            
            }
            int fuelType= PackageRoutes.getInstance().getVehicleOperations().getFuelType(licencePlate);
            BigDecimal consumption = PackageRoutes.getInstance().getVehicleOperations().getConsumption(licencePlate);
            BigDecimal deficit = consumption.multiply(new BigDecimal(ri.getDistancepassed())).multiply(new  BigDecimal(fuelPrice[fuelType]));
            BigDecimal profit = ri.getTotalPriceOfDelivery().subtract(deficit);
            
            PackageRoutes.getInstance().getCourierOperations().incrementProfitForCourier(username, profit);
            PackageRoutes.getInstance().getCourierOperations().changeCouriersStatus(username, 0);
            int idStockroom = PackageRoutes.getInstance().getStockroomOperations().getStockroomOnAdress(pi.getIdAdressFrom());
            PackageRoutes.getInstance().getVehicleOperations().parkVehicle(licencePlate, idStockroom);
            //  todo:: postavi null na curently diriving
            
                    
            
            
            
         }
        
         
        
        
        
        return -1;
        
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
