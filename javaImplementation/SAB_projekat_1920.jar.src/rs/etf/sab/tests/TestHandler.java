/*     */ package rs.etf.sab.tests;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import rs.etf.sab.operations.CityOperations;
/*     */ import rs.etf.sab.operations.CourierRequestOperation;
/*     */ import rs.etf.sab.operations.GeneralOperations;
/*     */ import rs.etf.sab.operations.PackageOperations;
/*     */ 
/*     */ public class TestHandler {
/*   9 */   private static TestHandler testHandler = null;
/*     */   
/*     */   private AddressOperations addressOperations;
/*     */   
/*     */   private CityOperations cityOperations;
/*     */   
/*     */   private CourierOperations courierOperations;
/*     */   
/*     */   private CourierRequestOperation courierRequestOperation;
/*     */   
/*     */   private DriveOperation driveOperation;
/*     */   
/*     */   private GeneralOperations generalOperations;
/*     */   
/*     */   private PackageOperations packageOperations;
/*     */   
/*     */   private StockroomOperations stockroomOperations;
/*     */   
/*     */   private UserOperations userOperations;
/*     */   
/*     */   private VehicleOperations vehicleOperations;
/*     */ 
/*     */   
/*     */   private TestHandler(@NotNull AddressOperations addressOperations, @NotNull CityOperations cityOperations, @NotNull CourierOperations courierOperations, @NotNull CourierRequestOperation courierRequestOperation, @NotNull DriveOperation driveOperation, @NotNull GeneralOperations generalOperations, @NotNull PackageOperations packageOperations, @NotNull StockroomOperations stockroomOperations, @NotNull UserOperations userOperations, @NotNull VehicleOperations vehicleOperations) {
/*  33 */     this.addressOperations = addressOperations;
/*  34 */     this.cityOperations = cityOperations;
/*  35 */     this.courierOperations = courierOperations;
/*  36 */     this.courierRequestOperation = courierRequestOperation;
/*  37 */     this.driveOperation = driveOperation;
/*  38 */     this.generalOperations = generalOperations;
/*  39 */     this.packageOperations = packageOperations;
/*  40 */     this.stockroomOperations = stockroomOperations;
/*  41 */     this.userOperations = userOperations;
/*  42 */     this.vehicleOperations = vehicleOperations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createInstance(@NotNull AddressOperations addressOperations, @NotNull CityOperations cityOperations, @NotNull CourierOperations courierOperations, @NotNull CourierRequestOperation courierRequestOperation, @NotNull DriveOperation driveOperation, @NotNull GeneralOperations generalOperations, @NotNull PackageOperations packageOperations, @NotNull StockroomOperations stockroomOperations, @NotNull UserOperations userOperations, @NotNull VehicleOperations vehicleOperations) {
/*  57 */     testHandler = new TestHandler(addressOperations, cityOperations, courierOperations, courierRequestOperation, driveOperation, generalOperations, packageOperations, stockroomOperations, userOperations, vehicleOperations);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static TestHandler getInstance() {
/*  63 */     return testHandler;
/*     */   }
/*     */   
/*     */   public AddressOperations getAddressOperations() {
/*  67 */     return this.addressOperations;
/*     */   }
/*     */   
/*     */   public CityOperations getCityOperations() {
/*  71 */     return this.cityOperations;
/*     */   }
/*     */   
/*     */   public CourierOperations getCourierOperations() {
/*  75 */     return this.courierOperations;
/*     */   }
/*     */   
/*     */   public CourierRequestOperation getCourierRequestOperation() {
/*  79 */     return this.courierRequestOperation;
/*     */   }
/*     */   
/*     */   public DriveOperation getDriveOperation() {
/*  83 */     return this.driveOperation;
/*     */   }
/*     */   
/*     */   public GeneralOperations getGeneralOperations() {
/*  87 */     return this.generalOperations;
/*     */   }
/*     */   
/*     */   public PackageOperations getPackageOperations() {
/*  91 */     return this.packageOperations;
/*     */   }
/*     */   
/*     */   public StockroomOperations getStockroomOperations() {
/*  95 */     return this.stockroomOperations;
/*     */   }
/*     */   
/*     */   public UserOperations getUserOperations() {
/*  99 */     return this.userOperations;
/*     */   }
/*     */   
/*     */   public VehicleOperations getVehicleOperations() {
/* 103 */     return this.vehicleOperations;
/*     */   }
/*     */ }


/* Location:              C:\Users\CAR\Desktop\sab\SAB_projekat_1920\SAB_projekat_1920\SAB_projekat_1920.jar!\rs\etf\sab\tests\TestHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */