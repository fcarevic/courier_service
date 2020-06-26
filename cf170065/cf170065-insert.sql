insert into vehicle(registrationNum, fuelType, consumption, capacity) values('bg1234',1,2,100 );
insert into vehicle(registrationNum, fuelType, consumption, capacity) values('va1234',0,5,100 );

set identity_insert City on;
insert into City(idCity,name, postalCode) values(1,'Cacak',32000),
			(2,'Beograd', 11000),
			(3,'Valjevo', 12000);
set identity_insert City off;

set identity_insert Adress on
insert into Adress(idAdress, idCity,number,street,xCord,yCord) values (1,1,100,'MutapovaCacak', 100,100),
 (2,1,4,'KnezaMilosaCacak', 90,90),
 (3,1,5,'KnezaMihailaCacak', 80,80),
(4,1,6,'CarLazarCacak', 70,70),
(5,2,4,'KnezaMilosaBG', 190,190),
 (6,2,5,'KnezaMihailaBG', 180,180),
(7,2,6,'CarLazarBG', 170,170),
(8,3,4,'KnezaMilosaVA', 290,290),
 (9,3,5,'KnezaMihailaVA', 280,280),
(10,3,6,'CarLazarVA', 270,270);

set identity_insert Adress off
insert into users(firstName, lastName, idAdress,password, userName) values ('Filip','Carevic', 1, 'Test_123', 'filip' ),
('Marko','Markovic', 5, 'Test_123', 'marko' ),
('Ivan','Ivanic', 8, 'Test_123', 'ivan' ),
('Jovic','Jovicic', 10, 'Test_123', 'jovica' );



insert into Courier(userName,status,profit,numberOfDeliveredPackages, dirversLicence) values('filip', 0,0,0,'vozacka1'),
('marko', 0,100,3,'vozacka2');
insert into Administrator (userName) values ('jovica');

insert into CourierRequests(userName,driversLicence) values ('jovica', 'vozacka4');
insert into EverDriven(userName,registrationNum) values ('marko', 'bg1234');

set identity_insert PackageRequest on;
insert into PackageRequest(idPackage, fromAdress,toAdress,type,weight,userName) 
						values (1,4,9,2,15, 'jovica'),
						 (2,5,10,2,15,'jovica'),
						 (3,2,9,2,15,'jovica');

update Package set status = 3 , accepted_at=GETDATE() where idPackage in (1,2,3);
update Package set currently_atAdress = 9 where idPackage in (1,3);
update Package set currently_atAdress = 10 where idPackage =2;

set identity_insert PackageRequest off;

set identity_insert Stockroom on
insert into Stockroom(idAdress,idStockroom) values (3,1), (7,2), (8,3);
insert into Parked(idStockroom,registrationNum) values (2,'bg1234'), (3,'va1234');

set identity_insert Stockroom off



select * from Package