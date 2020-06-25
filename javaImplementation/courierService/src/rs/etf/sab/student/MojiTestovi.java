/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;
import rs.etf.sab.operations.AddressOperations;
import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.operations.CourierOperations;
import rs.etf.sab.operations.CourierRequestOperation;
import rs.etf.sab.operations.DriveOperation;
import rs.etf.sab.operations.GeneralOperations;
import rs.etf.sab.operations.PackageOperations;
import rs.etf.sab.operations.StockroomOperations;
import rs.etf.sab.operations.UserOperations;
import rs.etf.sab.operations.VehicleOperations;
import rs.etf.sab.tests2.Util;

/**
 *
 * @author CAR
 */
public class MojiTestovi {
      AddressOperations addressOperations = PackageRoutes.getInstance().getAddressOperations(); // Change this to your implementation.
        CityOperations cityOperations = PackageRoutes.getInstance().getCityOperations(); // Do it for all classes.
        CourierOperations courierOperations = PackageRoutes.getInstance().getCourierOperations(); // e.g. = new MyDistrictOperations();
        CourierRequestOperation courierRequestOperation = PackageRoutes.getInstance().getCourierRequestOperation();
        DriveOperation driveOperation = PackageRoutes.getInstance().getDriveOperation();
        GeneralOperations generalOperations = PackageRoutes.getInstance().getGeneralOperations();
        PackageOperations packageOperations = PackageRoutes.getInstance().getPackageOperations();
        StockroomOperations stockroomOperations = PackageRoutes.getInstance().getStockroomOperations();
        UserOperations userOperations = PackageRoutes.getInstance().getUserOperations();
        VehicleOperations vehicleOperations = PackageRoutes.getInstance().getVehicleOperations();
      
     
    public static void main(String [] args){
          System.out.println("rs.etf.sab.student.StudentMain.main() poceo test");
          
         MojiTestovi mt= new MojiTestovi();
         
         
         mt.generalOperations.eraseAll();
        mt.publicOne();
        
        
         
         
    }
    
/*     */   int insertCity(String name, String postalCode) {
/*  76 */     int idCity = this.cityOperations.insertCity(name, postalCode);
/*  77 */     Assert.assertNotEquals(-1L, idCity);
/*  78 */     Assert.assertTrue(this.cityOperations.getAllCities().contains(Integer.valueOf(idCity)));
/*  79 */     return idCity;
/*     */   }
/*     */   
/*  82 */   Map<Integer, Pair<Integer, Integer>> addressesCoords = new HashMap<>();
/*     */   
/*     */   int insertAddress(String street, int number, int idCity, int x, int y) {
/*  85 */     int idAddress = this.addressOperations.insertAddress(street, number, idCity, x, y);
/*  86 */     Assert.assertNotEquals(-1L, idAddress);
/*  87 */     Assert.assertTrue(this.addressOperations.getAllAddresses().contains(Integer.valueOf(idAddress)));
/*  88 */     this.addressesCoords.put(Integer.valueOf(idAddress), new Pair(Integer.valueOf(x), Integer.valueOf(y)));
/*  89 */     return idAddress;
/*     */   }
/*     */   
/*     */   String insertUser(String username, String firstName, String lastName, String password, int idAddress) {
                boolean flag =this.userOperations.insertUser(username, firstName, lastName, password, idAddress);
/*  93 */     Assert.assertTrue(flag);
/*  94 */     Assert.assertTrue(this.userOperations.getAllUsers().contains(username));
/*  95 */     return username;
/*     */   }
/*     */   
/*     */   String insertCourier(String username, String firstName, String lastName, String password, int idAddress, String driverLicenceNumber) {
/*  99 */     insertUser(username, firstName, lastName, password, idAddress);
/* 100 */     Assert.assertTrue(this.courierOperations.insertCourier(username, driverLicenceNumber));
/* 101 */     return username;
/*     */   }
/*     */   
/*     */   public void insertAndParkVehicle(String licencePlateNumber, BigDecimal fuelConsumption, BigDecimal capacity, int fuelType, int idStockroom) {
/* 105 */     Assert.assertTrue(this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption, capacity));
/* 106 */     Assert.assertTrue(this.vehicleOperations.getAllVehichles().contains(licencePlateNumber));
/* 107 */     Assert.assertTrue(this.vehicleOperations.parkVehicle(licencePlateNumber, idStockroom));
/*     */   }
/*     */   
/*     */   public int insertStockroom(int idAddress) {
/* 111 */     int stockroomId = this.stockroomOperations.insertStockroom(idAddress);
/* 112 */     Assert.assertNotEquals(-1L, stockroomId);
/* 113 */     Assert.assertTrue(this.stockroomOperations.getAllStockrooms().contains(Integer.valueOf(stockroomId)));
/* 114 */     return stockroomId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 119 */   Map<Integer, BigDecimal> packagePrice = new HashMap<>();
/*     */   int insertAndAcceptPackage(int addressFrom, int addressTo, String userName, int packageType, BigDecimal weight) {
/* 121 */     int idPackage = this.packageOperations.insertPackage(addressFrom, addressTo, userName, packageType, weight);
/* 122 */     Assert.assertNotEquals(-1L, idPackage);
/* 123 */     Assert.assertTrue(this.packageOperations.acceptAnOffer(idPackage));
/* 124 */     Assert.assertTrue(this.packageOperations.getAllPackages().contains(Integer.valueOf(idPackage)));
/*     */     
/* 126 */     Assert.assertEquals(1L, this.packageOperations.getDeliveryStatus(idPackage));
/* 127 */     BigDecimal price = Util.getPackagePrice(packageType, weight, 
/* 128 */         Util.getDistance((Pair<Integer, Integer>[])new Pair[] { this.addressesCoords.get(Integer.valueOf(addressFrom)), this.addressesCoords.get(Integer.valueOf(addressTo)) }));
/*     */     
/* 130 */     Assert.assertTrue((this.packageOperations.getPriceOfDelivery(idPackage).compareTo(price.multiply(new BigDecimal(1.05D))) < 0));
/* 131 */   System.out.println("rs.etf.sab.student.MojiTestovi.insertAndAcceptPackage()" + packageOperations.getPriceOfDelivery(idPackage)+"  " +price);  
Assert.assertTrue((this.packageOperations.getPriceOfDelivery(idPackage).compareTo(price.multiply(new BigDecimal(0.95D))) > 0));
/*     */     
/* 133 */     this.packagePrice.put(Integer.valueOf(idPackage), price);
/*     */     
/* 135 */     System.out.println("cena " + price);
/*     */     
/* 137 */     return idPackage;
/*     */   }
/*     */   
/*     */ 
  @Test
  public void publicOne() {
    int BG = insertCity("Belgrade", "11000");
    int KG = insertCity("Kragujevac", "550000");
    int VA = insertCity("Valjevo", "14000");
    int CA = insertCity("Cacak", "32000");
    int idAddressBG1 = insertAddress("Kraljice Natalije", 37, BG, 11, 15);
    int idAddressBG2 = insertAddress("Bulevar kralja Aleksandra", 73, BG, 10, 10);
    int idAddressBG3 = insertAddress("Vojvode Stepe", 39, BG, 1, -1);
    int idAddressBG4 = insertAddress("Takovska", 7, BG, 11, 12);
    int idAddressBG5 = insertAddress("Bulevar kralja Aleksandra", 37, BG, 12, 12);
    int idAddressKG1 = insertAddress("Daniciceva", 1, KG, 4, 310);
    int idAddressKG2 = insertAddress("Dure Pucara Starog", 2, KG, 11, 320);
    int idAddressVA1 = insertAddress("Cika Ljubina", 8, VA, 102, 101);
    int idAddressVA2 = insertAddress("Karadjordjeva", 122, VA, 104, 103);
    int idAddressVA3 = insertAddress("Milovana Glisica", 45, VA, 101, 101);
    int idAddressCA1 = insertAddress("Zupana Stracimira", 1, CA, 110, 309);
    int idAddressCA2 = insertAddress("Bulevar Vuka Karadzica", 1, CA, 111, 315);
    int idStockroomBG = insertStockroom(idAddressBG1);
    int idStockroomVA = insertStockroom(idAddressVA1);
    insertAndParkVehicle("BG1675DA", new BigDecimal(6.3D), new BigDecimal(1000.5D), 2, idStockroomBG);
    insertAndParkVehicle("VA1675DA", new BigDecimal(7.3D), new BigDecimal(500.5D), 1, idStockroomVA);
    String username = "crno.dete";
    insertUser(username, "Svetislav", "Kisprdilov", "Test_123", idAddressBG1);
    String courierUsernameBG = "postarBG";
    insertCourier(courierUsernameBG, "Pera", "Peric", "Postar_73", idAddressBG2, "654321");
    String courierUsernameVA = "postarVA";
    insertCourier(courierUsernameVA, "Pera", "Peric", "Postar_73", idAddressBG2, "123456");
    int type1 = 0;
    BigDecimal weight1 = new BigDecimal(2);
    int idPackage1 = insertAndAcceptPackage(idAddressBG2, idAddressCA1, username, type1, weight1);
    int type2 = 1;
    BigDecimal weight2 = new BigDecimal(4);
    int idPackage2 = insertAndAcceptPackage(idAddressBG3, idAddressVA1, username, type2, weight2);
    int type3 = 2;
    BigDecimal weight3 = new BigDecimal(5);
    int idPackage3 = insertAndAcceptPackage(idAddressBG4, idAddressKG1, username, type3, weight3);
    Assert.assertEquals(0L, this.courierOperations.getCouriersWithStatus(1).size());
    this.driveOperation.planingDrive(courierUsernameBG);
    Assert.assertTrue(this.courierOperations.getCouriersWithStatus(1).contains(courierUsernameBG));
    int type4 = 3;
    BigDecimal weight4 = new BigDecimal(2);
    int idPackage4 = insertAndAcceptPackage(idAddressBG2, idAddressKG2, username, type4, weight4);
    Assert.assertEquals(4L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
    Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage1));
    Assert.assertEquals(1L, this.packageOperations.getDeliveryStatus(idPackage2));
    Assert.assertEquals(1L, this.packageOperations.getDeliveryStatus(idPackage3));
    Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
    Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
    Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
    Assert.assertEquals(3L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
    Assert.assertEquals(1L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
    Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage1));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage2));
    Assert.assertEquals(1L, this.packageOperations.getDeliveryStatus(idPackage3));
    Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
    Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
    Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
    Assert.assertEquals(2L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
    Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
    Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage1));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage2));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage3));
    Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
    Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
    Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
    Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
    Assert.assertEquals(3L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
    Assert.assertEquals(idPackage2, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage1));
    Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage2));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage3));
    Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
    Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
    Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
    Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(VA).size());
    Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
    Assert.assertEquals(idPackage1, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage1));
    Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage2));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage3));
    Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
    Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
    Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
    Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(CA).size());
    Assert.assertEquals(1L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
    Assert.assertEquals(idPackage3, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage1));
    Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage2));
    Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage3));
    Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
    Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
    Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
    Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(KG).size());
    Assert.assertEquals(0L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
    Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(1L, this.packageOperations.getDeliveryStatus(idPackage4));
    Assert.assertEquals(1L, this.packageOperations.getAllUndeliveredPackages().size());
    Assert.assertTrue(this.packageOperations.getAllUndeliveredPackages().contains(Integer.valueOf(idPackage4)));
    Assert.assertEquals(2L, this.courierOperations.getCouriersWithStatus(0).size());
    double distance = Util.getDistance((Pair<Integer, Integer>[])new Pair[] { this.addressesCoords.get(Integer.valueOf(idAddressBG1)), this.addressesCoords.get(Integer.valueOf(idAddressBG2)), this.addressesCoords
          .get(Integer.valueOf(idAddressBG3)), this.addressesCoords.get(Integer.valueOf(idAddressBG4)), this.addressesCoords
          .get(Integer.valueOf(idAddressVA1)), this.addressesCoords.get(Integer.valueOf(idAddressCA1)), this.addressesCoords.get(Integer.valueOf(idAddressKG1)), this.addressesCoords
          .get(Integer.valueOf(idAddressBG1)) });
    BigDecimal profit = ((BigDecimal)this.packagePrice.get(Integer.valueOf(idPackage1))).add(this.packagePrice.get(Integer.valueOf(idPackage2))).add(this.packagePrice.get(Integer.valueOf(idPackage3)));
      System.out.println("rs.etf.sab.student.MojiTestovi.publicOne() cist profit " + profit);
    profit = profit.subtract((new BigDecimal(36)).multiply(new BigDecimal(6.3D)).multiply(new BigDecimal(distance)));
      System.out.println("" + profit +  "  " +this.courierOperations.getAverageCourierProfit(3));
    Assert.assertTrue((this.courierOperations.getAverageCourierProfit(3).compareTo(profit.multiply(new BigDecimal(1.05D))) < 0));
    Assert.assertTrue((this.courierOperations.getAverageCourierProfit(3).compareTo(profit.multiply(new BigDecimal(0.95D))) > 0));
  }
  
/*     */   

  @Test
  public void publicTwo() {
    int BG = insertCity("Belgrade", "11000");
    int KG = insertCity("Kragujevac", "550000");
    int VA = insertCity("Valjevo", "14000");
    int CA = insertCity("Cacak", "32000");
    int idAddressBG1 = insertAddress("Kraljice Natalije", 37, BG, 11, 15);
    int idAddressBG2 = insertAddress("Bulevar kralja Aleksandra", 73, BG, 10, 10);
    int idAddressBG3 = insertAddress("Vojvode Stepe", 39, BG, 1, -1);
    int idAddressBG4 = insertAddress("Takovska", 7, BG, 11, 12);
    int idAddressBG5 = insertAddress("Bulevar kralja Aleksandra", 37, BG, 12, 12);
    int idAddressKG1 = insertAddress("Daniciceva", 1, KG, 4, 310);
    int idAddressKG2 = insertAddress("Dure Pucara Starog", 2, KG, 11, 320);
    int idAddressVA1 = insertAddress("Cika Ljubina", 8, VA, 102, 101);
    int idAddressVA2 = insertAddress("Karadjordjeva", 122, VA, 104, 103);
    int idAddressVA3 = insertAddress("Milovana Glisica", 45, VA, 101, 101);
    int idAddressCA1 = insertAddress("Zupana Stracimira", 1, CA, 110, 309);
    int idAddressCA2 = insertAddress("Bulevar Vuka Karadzica", 1, CA, 111, 315);
    int idStockroomBG = insertStockroom(idAddressBG1);
    int idStockroomVA = insertStockroom(idAddressVA1);
    insertAndParkVehicle("BG1675DA", new BigDecimal(6.3D), new BigDecimal(1000.5D), 2, idStockroomBG);
    insertAndParkVehicle("VA1675DA", new BigDecimal(7.3D), new BigDecimal(500.5D), 1, idStockroomVA);
    String username = "crno.dete";
    insertUser(username, "Svetislav", "Kisprdilov", "Test_123", idAddressBG1);
    String courierUsernameBG = "postarBG";
    insertCourier(courierUsernameBG, "Pera", "Peric", "Postar_73", idAddressBG2, "654321");
    String courierUsernameVA = "postarVA";
    insertCourier(courierUsernameVA, "Pera", "Peric", "Postar_73", idAddressVA2, "123456");
    int type = 1;
    BigDecimal weight = new BigDecimal(4);
    int idPackage1 = insertAndAcceptPackage(idAddressBG2, idAddressKG1, username, type, weight);
    int idPackage2 = insertAndAcceptPackage(idAddressKG2, idAddressBG4, username, type, weight);
    int idPackage3 = insertAndAcceptPackage(idAddressVA2, idAddressCA1, username, type, weight);
    int idPackage4 = insertAndAcceptPackage(idAddressCA2, idAddressBG4, username, type, weight);
    Assert.assertEquals(0L, this.courierOperations.getCouriersWithStatus(1).size());
    this.driveOperation.planingDrive(courierUsernameBG);
    this.driveOperation.planingDrive(courierUsernameVA);
    Assert.assertEquals(2L, this.courierOperations.getCouriersWithStatus(1).size());
    Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(idPackage1, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage1));
    Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameVA));
    Assert.assertEquals(idPackage3, this.driveOperation.nextStop(courierUsernameVA));
    Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage3));
    Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage2));
    Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameVA));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage4));
    Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage2));
    Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage2)));
    Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(KG).contains(Integer.valueOf(idPackage1)));
    Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameVA));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage4));
    Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(VA).contains(Integer.valueOf(idPackage4)));
    Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(CA).contains(Integer.valueOf(idPackage3)));
    int idPackage5 = insertAndAcceptPackage(idAddressVA2, idAddressCA1, username, type, weight);
    int idPackage6 = insertAndAcceptPackage(idAddressBG3, idAddressVA3, username, type, weight);
    this.driveOperation.planingDrive(courierUsernameBG);
    Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage6));
    Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage2)));
    Assert.assertFalse(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage6)));
    Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
    Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage2)));
    Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage6)));
    Assert.assertEquals(idPackage2, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage2));
    Assert.assertEquals(idPackage6, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage6));
    Assert.assertEquals(0L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
    Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage5));
    Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage5)));
    Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage4));
    Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
    Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage4)));
    Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage5)));
    Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(VA).size());
    Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(VA).contains(Integer.valueOf(idPackage6)));
    Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(0L, this.packageOperations.getAllUndeliveredPackagesFromCity(BG).size());
    Assert.assertEquals(3L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
    Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage2)));
    Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage4)));
    Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage5)));
    this.driveOperation.planingDrive(courierUsernameBG);
    Assert.assertEquals(0L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
    Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
    Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage4)));
    Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage5)));
    Assert.assertEquals(idPackage4, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage4));
    Assert.assertEquals(idPackage5, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage5));
    Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameBG));
    Assert.assertEquals(0L, this.packageOperations.getAllUndeliveredPackages().size());
    Assert.assertEquals(2L, this.courierOperations.getCouriersWithStatus(0).size());
    Assert.assertTrue((this.courierOperations.getAverageCourierProfit(1).compareTo(new BigDecimal(0)) > 0));
    Assert.assertTrue((this.courierOperations.getAverageCourierProfit(5).compareTo(new BigDecimal(0)) > 0));
  }
}
