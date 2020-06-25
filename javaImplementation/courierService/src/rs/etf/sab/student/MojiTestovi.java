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
/*     */   @Test
/*     */   public void publicOne() {
/* 142 */     int BG = insertCity("Belgrade", "11000");
/* 143 */     int KG = insertCity("Kragujevac", "550000");
/* 144 */     int VA = insertCity("Valjevo", "14000");
/* 145 */     int CA = insertCity("Cacak", "32000");
/*     */     
/* 147 */     int idAddressBG1 = insertAddress("Kraljice Natalije", 37, BG, 11, 15);
/* 148 */     int idAddressBG2 = insertAddress("Bulevar kralja Aleksandra", 73, BG, 10, 10);
/* 149 */     int idAddressBG3 = insertAddress("Vojvode Stepe", 39, BG, 1, -1);
/* 150 */     int idAddressBG4 = insertAddress("Takovska", 7, BG, 11, 12);
/* 151 */     int idAddressBG5 = insertAddress("Bulevar kralja Aleksandra", 37, BG, 12, 12);
/*     */     
/* 153 */     int idAddressKG1 = insertAddress("Daniciceva", 1, KG, 4, 310);
/* 154 */     int idAddressKG2 = insertAddress("Dure Pucara Starog", 2, KG, 11, 320);
/*     */     
/* 156 */     int idAddressVA1 = insertAddress("Cika Ljubina", 8, VA, 102, 101);
/* 157 */     int idAddressVA2 = insertAddress("Karadjordjeva", 122, VA, 104, 103);
/* 158 */     int idAddressVA3 = insertAddress("Milovana Glisica", 45, VA, 101, 101);
/*     */     
/* 160 */     int idAddressCA1 = insertAddress("Zupana Stracimira", 1, CA, 110, 309);
/* 161 */     int idAddressCA2 = insertAddress("Bulevar Vuka Karadzica", 1, CA, 111, 315);
/*     */     
/* 163 */     int idStockroomBG = insertStockroom(idAddressBG1);
/* 164 */     int idStockroomVA = insertStockroom(idAddressVA1);
/* 165 */     insertAndParkVehicle("BG1675DA", new BigDecimal(6.3D), new BigDecimal(1000.5D), 2, idStockroomBG);
/* 166 */     insertAndParkVehicle("VA1675DA", new BigDecimal(7.3D), new BigDecimal(500.5D), 1, idStockroomVA);
/*     */     
/* 168 */     String username = "crno.dete";
/* 169 */     insertUser(username, "Svetislav", "Kisprdilov", "Test_123", idAddressBG1);
/*     */     
/* 171 */     String courierUsernameBG = "postarBG";
/* 172 */     insertCourier(courierUsernameBG, "Pera", "Peric", "Postar_73", idAddressBG2, "654321");
/*     */     
/* 174 */     String courierUsernameVA = "postarVA";
/* 175 */     insertCourier(courierUsernameVA, "Pera", "Peric", "Postar_73", idAddressBG2, "123456");
/*     */     
/* 177 */     int type1 = 0;
/* 178 */     BigDecimal weight1 = new BigDecimal(2);
/* 179 */     int idPackage1 = insertAndAcceptPackage(idAddressBG2, idAddressCA1, username, type1, weight1);
/*     */     
/* 181 */     int type2 = 1;
/* 182 */     BigDecimal weight2 = new BigDecimal(4);
/* 183 */     int idPackage2 = insertAndAcceptPackage(idAddressBG3, idAddressVA1, username, type2, weight2);
/*     */     
/* 185 */     int type3 = 2;
/* 186 */     BigDecimal weight3 = new BigDecimal(5);
/* 187 */     int idPackage3 = insertAndAcceptPackage(idAddressBG4, idAddressKG1, username, type3, weight3);
/*     */     
/* 189 */     Assert.assertEquals(0L, this.courierOperations.getCouriersWithStatus(1).size());
/* 190 */     this.driveOperation.planingDrive(courierUsernameBG);
/* 191 */     Assert.assertTrue(this.courierOperations.getCouriersWithStatus(1).contains(courierUsernameBG));
/*     */     
/* 193 */     int type4 = 3;
/* 194 */     BigDecimal weight4 = new BigDecimal(2);
/* 195 */     int idPackage4 = insertAndAcceptPackage(idAddressBG2, idAddressKG2, username, type4, weight4);
/*     */     
/* 197 */     Assert.assertEquals(4L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
/*     */     
/* 199 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 200 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage1));
/* 201 */     Assert.assertEquals(1L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 202 */     Assert.assertEquals(1L, this.packageOperations.getDeliveryStatus(idPackage3));
/* 203 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
/* 204 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
/* 205 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
/* 206 */     Assert.assertEquals(3L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
/* 207 */     Assert.assertEquals(1L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 209 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 210 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage1));
/* 211 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 212 */     Assert.assertEquals(1L, this.packageOperations.getDeliveryStatus(idPackage3));
/* 213 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
/* 214 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
/* 215 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
/* 216 */     Assert.assertEquals(2L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
/* 217 */     Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 219 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 220 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage1));
/* 221 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 222 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage3));
/* 223 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
/* 224 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
/* 225 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
/* 226 */     Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(BG).size());
/* 227 */     Assert.assertEquals(3L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 229 */     Assert.assertEquals(idPackage2, this.driveOperation.nextStop(courierUsernameBG));
/* 230 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage1));
/* 231 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 232 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage3));
/* 233 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
/* 234 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
/* 235 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
/* 236 */     Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(VA).size());
/* 237 */     Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 239 */     Assert.assertEquals(idPackage1, this.driveOperation.nextStop(courierUsernameBG));
/* 240 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage1));
/* 241 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 242 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage3));
/* 243 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
/* 244 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
/* 245 */     Assert.assertEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
/* 246 */     Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(CA).size());
/* 247 */     Assert.assertEquals(1L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 249 */     Assert.assertEquals(idPackage3, this.driveOperation.nextStop(courierUsernameBG));
/* 250 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage1));
/* 251 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 252 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage3));
/* 253 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage1));
/* 254 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage2));
/* 255 */     Assert.assertNotEquals(-1L, this.packageOperations.getCurrentLocationOfPackage(idPackage3));
/* 256 */     Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(KG).size());
/* 257 */     Assert.assertEquals(0L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 259 */     Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameBG));
/* 260 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage4));
/* 261 */     Assert.assertEquals(1L, this.packageOperations.getAllUndeliveredPackages().size());
/* 262 */     Assert.assertTrue(this.packageOperations.getAllUndeliveredPackages().contains(Integer.valueOf(idPackage4)));
/*     */     
/* 264 */     Assert.assertEquals(2L, this.courierOperations.getCouriersWithStatus(0).size());
/*     */     
/* 266 */     double distance = Util.getDistance((Pair<Integer, Integer>[])new Pair[] { this.addressesCoords.get(Integer.valueOf(idAddressBG1)), this.addressesCoords.get(Integer.valueOf(idAddressBG2)), this.addressesCoords
/* 267 */           .get(Integer.valueOf(idAddressBG3)), this.addressesCoords.get(Integer.valueOf(idAddressBG4)), this.addressesCoords
/* 268 */           .get(Integer.valueOf(idAddressVA1)), this.addressesCoords.get(Integer.valueOf(idAddressCA1)), this.addressesCoords.get(Integer.valueOf(idAddressKG1)), this.addressesCoords
/* 269 */           .get(Integer.valueOf(idAddressBG1)) });
/* 270 */     BigDecimal profit = ((BigDecimal)this.packagePrice.get(Integer.valueOf(idPackage1))).add(this.packagePrice.get(Integer.valueOf(idPackage2))).add(this.packagePrice.get(Integer.valueOf(idPackage3)));
/* 271 */     profit = profit.subtract((new BigDecimal(32)).multiply(new BigDecimal(6.3D)).multiply(new BigDecimal(distance)));
/*     */     
/* 273 */     Assert.assertTrue((this.courierOperations.getAverageCourierProfit(3).compareTo(profit.multiply(new BigDecimal(1.05D))) < 0));
/* 274 */     Assert.assertTrue((this.courierOperations.getAverageCourierProfit(3).compareTo(profit.multiply(new BigDecimal(0.95D))) > 0));
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void publicTwo() {
/* 280 */     int BG = insertCity("Belgrade", "11000");
/* 281 */     int KG = insertCity("Kragujevac", "550000");
/* 282 */     int VA = insertCity("Valjevo", "14000");
/* 283 */     int CA = insertCity("Cacak", "32000");
/*     */     
/* 285 */     int idAddressBG1 = insertAddress("Kraljice Natalije", 37, BG, 11, 15);
/* 286 */     int idAddressBG2 = insertAddress("Bulevar kralja Aleksandra", 73, BG, 10, 10);
/* 287 */     int idAddressBG3 = insertAddress("Vojvode Stepe", 39, BG, 1, -1);
/* 288 */     int idAddressBG4 = insertAddress("Takovska", 7, BG, 11, 12);
/* 289 */     int idAddressBG5 = insertAddress("Bulevar kralja Aleksandra", 37, BG, 12, 12);
/*     */     
/* 291 */     int idAddressKG1 = insertAddress("Daniciceva", 1, KG, 4, 310);
/* 292 */     int idAddressKG2 = insertAddress("Dure Pucara Starog", 2, KG, 11, 320);
/*     */     
/* 294 */     int idAddressVA1 = insertAddress("Cika Ljubina", 8, VA, 102, 101);
/* 295 */     int idAddressVA2 = insertAddress("Karadjordjeva", 122, VA, 104, 103);
/* 296 */     int idAddressVA3 = insertAddress("Milovana Glisica", 45, VA, 101, 101);
/*     */     
/* 298 */     int idAddressCA1 = insertAddress("Zupana Stracimira", 1, CA, 110, 309);
/* 299 */     int idAddressCA2 = insertAddress("Bulevar Vuka Karadzica", 1, CA, 111, 315);
/*     */     
/* 301 */     int idStockroomBG = insertStockroom(idAddressBG1);
/* 302 */     int idStockroomVA = insertStockroom(idAddressVA1);
/* 303 */     insertAndParkVehicle("BG1675DA", new BigDecimal(6.3D), new BigDecimal(1000.5D), 2, idStockroomBG);
/* 304 */     insertAndParkVehicle("VA1675DA", new BigDecimal(7.3D), new BigDecimal(500.5D), 1, idStockroomVA);
/*     */     
/* 306 */     String username = "crno.dete";
/* 307 */     insertUser(username, "Svetislav", "Kisprdilov", "Test_123", idAddressBG1);
/*     */     
/* 309 */     String courierUsernameBG = "postarBG";
/* 310 */     insertCourier(courierUsernameBG, "Pera", "Peric", "Postar_73", idAddressBG2, "654321");
/*     */     
/* 312 */     String courierUsernameVA = "postarVA";
/* 313 */     insertCourier(courierUsernameVA, "Pera", "Peric", "Postar_73", idAddressBG2, "123456");
/*     */     
/* 315 */     int type = 0;
/* 316 */     BigDecimal weight = new BigDecimal(2);
/*     */     
/* 318 */     int idPackage1 = insertAndAcceptPackage(idAddressBG2, idAddressKG1, username, type, weight);
/* 319 */     int idPackage2 = insertAndAcceptPackage(idAddressKG2, idAddressBG4, username, type, weight);
/*     */     
/* 321 */     int idPackage3 = insertAndAcceptPackage(idAddressVA2, idAddressCA1, username, type, weight);
/* 322 */     int idPackage4 = insertAndAcceptPackage(idAddressCA2, idAddressBG4, username, type, weight);
/*     */     
/* 324 */     Assert.assertEquals(0L, this.courierOperations.getCouriersWithStatus(1).size());
/* 325 */     this.driveOperation.planingDrive(courierUsernameBG);
/* 326 */     this.driveOperation.planingDrive(courierUsernameVA);
/* 327 */     Assert.assertEquals(2L, this.courierOperations.getCouriersWithStatus(1).size());
/*     */     
/* 329 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 330 */     Assert.assertEquals(idPackage1, this.driveOperation.nextStop(courierUsernameBG));
/* 331 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage1));
/*     */     
/* 333 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameVA));
/* 334 */     Assert.assertEquals(idPackage3, this.driveOperation.nextStop(courierUsernameVA));
/* 335 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage3));
/*     */     
/* 337 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 338 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage2));
/*     */     
/* 340 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameVA));
/* 341 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage4));
/*     */     
/* 343 */     Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameBG));
/* 344 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 345 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage2)));
/* 346 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(KG).contains(Integer.valueOf(idPackage1)));
/*     */     
/* 348 */     Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameBG));
/* 349 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 350 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(VA).contains(Integer.valueOf(idPackage4)));
/* 351 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(CA).contains(Integer.valueOf(idPackage3)));
/*     */ 
/*     */ 
/*     */     
/* 355 */     int idPackage5 = insertAndAcceptPackage(idAddressVA2, idAddressCA1, username, type, weight);
/* 356 */     int idPackage6 = insertAndAcceptPackage(idAddressBG3, idAddressVA3, username, type, weight);
/*     */     
/* 358 */     this.driveOperation.planingDrive(courierUsernameBG);
/*     */     
/* 360 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 361 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage6));
/* 362 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage2)));
/* 363 */     Assert.assertFalse(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage6)));
/*     */     
/* 365 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 366 */     Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/* 367 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage2)));
/* 368 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage6)));
/*     */     
/* 370 */     Assert.assertEquals(idPackage2, this.driveOperation.nextStop(courierUsernameBG));
/* 371 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage2));
/* 372 */     Assert.assertEquals(idPackage6, this.driveOperation.nextStop(courierUsernameBG));
/* 373 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage6));
/* 374 */     Assert.assertEquals(0L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 376 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 377 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage5));
/* 378 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage5)));
/*     */     
/* 380 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 381 */     Assert.assertEquals(2L, this.packageOperations.getDeliveryStatus(idPackage4));
/* 382 */     Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/* 383 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage4)));
/* 384 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage5)));
/* 385 */     Assert.assertEquals(1L, this.packageOperations.getAllPackagesCurrentlyAtCity(VA).size());
/* 386 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(VA).contains(Integer.valueOf(idPackage6)));
/*     */     
/* 388 */     Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameBG));
/* 389 */     Assert.assertEquals(2L, this.packageOperations.getAllUndeliveredPackagesFromCity(BG).size());
/* 390 */     Assert.assertTrue(this.packageOperations.getAllPackagesCurrentlyAtCity(BG).contains(Integer.valueOf(idPackage2)));
/* 391 */     Assert.assertTrue(this.packageOperations.getAllUndeliveredPackagesFromCity(BG).contains(Integer.valueOf(idPackage4)));
/* 392 */     Assert.assertTrue(this.packageOperations.getAllUndeliveredPackagesFromCity(BG).contains(Integer.valueOf(idPackage5)));
/*     */ 
/*     */     
/* 395 */     this.driveOperation.planingDrive(courierUsernameBG);
/* 396 */     Assert.assertEquals(0L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/*     */     
/* 398 */     Assert.assertEquals(-2L, this.driveOperation.nextStop(courierUsernameBG));
/* 399 */     Assert.assertEquals(0L, this.packageOperations.getAllUndeliveredPackagesFromCity(BG).size());
/* 400 */     Assert.assertEquals(2L, this.driveOperation.getPackagesInVehicle(courierUsernameBG).size());
/* 401 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage4)));
/* 402 */     Assert.assertTrue(this.driveOperation.getPackagesInVehicle(courierUsernameBG).contains(Integer.valueOf(idPackage5)));
/*     */     
/* 404 */     Assert.assertEquals(idPackage4, this.driveOperation.nextStop(courierUsernameBG));
/* 405 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage4));
/*     */     
/* 407 */     Assert.assertEquals(idPackage5, this.driveOperation.nextStop(courierUsernameBG));
/* 408 */     Assert.assertEquals(3L, this.packageOperations.getDeliveryStatus(idPackage5));
/*     */     
/* 410 */     Assert.assertEquals(-1L, this.driveOperation.nextStop(courierUsernameBG));
/*     */     
/* 412 */     Assert.assertEquals(0L, this.packageOperations.getAllUndeliveredPackages().size());
/*     */     
/* 414 */     Assert.assertEquals(4L, 2L);
/*     */     
/* 416 */     Assert.assertEquals(2L, this.courierOperations.getCouriersWithStatus(0).size());
/*     */     
/* 418 */     Assert.assertTrue((this.courierOperations.getAverageCourierProfit(1).compareTo(new BigDecimal(0)) > 0));
/* 419 */     Assert.assertTrue((this.courierOperations.getAverageCourierProfit(5).compareTo(new BigDecimal(0)) > 0));
/*     */   }
}
