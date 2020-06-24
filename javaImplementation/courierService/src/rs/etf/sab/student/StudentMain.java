package rs.etf.sab.student;

import rs.etf.sab.operations.*;
import rs.etf.sab.tests.TestHandler;
import rs.etf.sab.tests.TestRunner;


public class StudentMain {

    public static void main(String[] args) {
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
            System.out.println("rs.etf.sab.student.StudentMain.main() poceo test");

        TestHandler.createInstance(
                addressOperations,
                cityOperations,
                courierOperations,
                courierRequestOperation,
                driveOperation,
                generalOperations,
                packageOperations,
                stockroomOperations,
                userOperations,
                vehicleOperations);

        TestRunner.runTests();
    }
}
