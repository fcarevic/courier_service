
CREATE TABLE [Administrator]
( 
	[userName]           varchar(100)  NOT NULL 
)
go

CREATE TABLE [Adress]
( 
	[xCord]              integer  NULL ,
	[yCord]              integer  NULL ,
	[street]             varchar(100)  NULL ,
	[number]             integer  NULL ,
	[idAdress]           integer  IDENTITY  NOT NULL ,
	[idCity]             integer  NOT NULL 
)
go

CREATE TABLE [City]
( 
	[idCity]             integer  IDENTITY  NOT NULL ,
	[name]               varchar(100)  NULL ,
	[postalCode]         varchar(100)  NULL 
)
go

CREATE TABLE [Courier]
( 
	[dirversLicence]     varchar(100)  NULL ,
	[userName]           varchar(100)  NOT NULL ,
	[profit]             decimal(10,3)  NULL ,
	[status]             smallint  NULL ,
	[numberOfDeliveredPackages] integer  NULL 
)
go

CREATE TABLE [CourierRequests]
( 
	[driversLicence]     varchar(100)  NULL ,
	[userName]           varchar(100)  NOT NULL 
)
go

CREATE TABLE [CurrentlyDriving]
( 
	[userName]           varchar(100)  NOT NULL ,
	[registrationNum]    varchar(100)  NOT NULL 
)
go

CREATE TABLE [EverDriven]
( 
	[userName]           varchar(100)  NOT NULL ,
	[registrationNum]    varchar(100)  NOT NULL 
)
go

CREATE TABLE [Package]
( 
	[price]              decimal(10,3)  NULL ,
	[created_at]         datetime  NULL ,
	[accepted_at]        datetime  NULL ,
	[status]             integer  NULL 
	CONSTRAINT [Validation_Rule_310_503122986]
		CHECK  ( status BETWEEN 0 AND 4 ),
	[currently_atAdress] integer  NULL ,
	[idPackage]          integer  NOT NULL 
)
go

CREATE TABLE [PackageInVehicle]
( 
	[registrationNum]    varchar(100)  NOT NULL ,
	[idPackage]          integer  NOT NULL 
)
go

CREATE TABLE [PackageRequest]
( 
	[idPackage]          integer  IDENTITY  NOT NULL ,
	[type]               integer  NULL 
	CONSTRAINT [tipPaketa]
		CHECK  ( type BETWEEN 0 AND 3 ),
	[weight]             decimal(10,3)  NULL ,
	[userName]           varchar(100)  NOT NULL ,
	[fromAdress]         integer  NOT NULL ,
	[toAdress]           integer  NOT NULL 
)
go

CREATE TABLE [Parked]
( 
	[idStockroom]        integer  NOT NULL ,
	[registrationNum]    varchar(100)  NOT NULL 
)
go

CREATE TABLE [Stockroom]
( 
	[idStockroom]        integer  IDENTITY  NOT NULL ,
	[idAdress]           integer  NOT NULL 
)
go

CREATE TABLE [Users]
( 
	[firstName]          varchar(100)  NULL ,
	[lastName]           varchar(100)  NULL ,
	[password]           varchar(100)  NULL ,
	[userName]           varchar(100)  NOT NULL ,
	[idAdress]           integer  NOT NULL 
)
go

CREATE TABLE [Vehicle]
( 
	[registrationNum]    varchar(100)  NOT NULL ,
	[fuelType]           integer  NULL 
	CONSTRAINT [proveraTipa]
		CHECK  ( fuelType BETWEEN 0 AND 2 ),
	[consumption]        decimal(10,3)  NULL ,
	[capacity]           decimal(10,3)  NULL 
)
go

ALTER TABLE [Administrator]
	ADD CONSTRAINT [XPKAdministrator] PRIMARY KEY  CLUSTERED ([userName] ASC)
go

ALTER TABLE [Adress]
	ADD CONSTRAINT [XPKAdress] PRIMARY KEY  CLUSTERED ([idAdress] ASC)
go

ALTER TABLE [City]
	ADD CONSTRAINT [XPKCity] PRIMARY KEY  CLUSTERED ([idCity] ASC)
go

ALTER TABLE [Courier]
	ADD CONSTRAINT [XPKCourier] PRIMARY KEY  CLUSTERED ([userName] ASC)
go

ALTER TABLE [Courier]
	ADD CONSTRAINT [XAK1CourierDriversLicence] UNIQUE ([dirversLicence]  ASC)
go

ALTER TABLE [CourierRequests]
	ADD CONSTRAINT [XPKCourierRequests] PRIMARY KEY  CLUSTERED ([userName] ASC)
go

ALTER TABLE [CourierRequests]
	ADD CONSTRAINT [XAK1CourierRequestsdriverLicence] UNIQUE ([driversLicence]  ASC)
go

ALTER TABLE [CurrentlyDriving]
	ADD CONSTRAINT [XPKCurrentlyDriving] PRIMARY KEY  CLUSTERED ([userName] ASC)
go

ALTER TABLE [EverDriven]
	ADD CONSTRAINT [XPKEverDriven] PRIMARY KEY  CLUSTERED ([userName] ASC,[registrationNum] ASC)
go

ALTER TABLE [Package]
	ADD CONSTRAINT [XPKPackage] PRIMARY KEY  CLUSTERED ([idPackage] ASC)
go

ALTER TABLE [PackageInVehicle]
	ADD CONSTRAINT [XPKPackageInVehicle] PRIMARY KEY  CLUSTERED ([idPackage] ASC)
go

ALTER TABLE [PackageRequest]
	ADD CONSTRAINT [XPKPackageRequest] PRIMARY KEY  CLUSTERED ([idPackage] ASC)
go

ALTER TABLE [Parked]
	ADD CONSTRAINT [XPKParked] PRIMARY KEY  CLUSTERED ([registrationNum] ASC)
go

ALTER TABLE [Stockroom]
	ADD CONSTRAINT [XPKStockroom] PRIMARY KEY  CLUSTERED ([idStockroom] ASC)
go

ALTER TABLE [Users]
	ADD CONSTRAINT [XPKUser] PRIMARY KEY  CLUSTERED ([userName] ASC)
go

ALTER TABLE [Vehicle]
	ADD CONSTRAINT [XPKVehicle] PRIMARY KEY  CLUSTERED ([registrationNum] ASC)
go


ALTER TABLE [Administrator]
	ADD CONSTRAINT [R_6] FOREIGN KEY ([userName]) REFERENCES [Users]([userName])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [Adress]
	ADD CONSTRAINT [R_1] FOREIGN KEY ([idCity]) REFERENCES [City]([idCity])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Courier]
	ADD CONSTRAINT [R_5] FOREIGN KEY ([userName]) REFERENCES [Users]([userName])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [CourierRequests]
	ADD CONSTRAINT [R_4] FOREIGN KEY ([userName]) REFERENCES [Users]([userName])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [CurrentlyDriving]
	ADD CONSTRAINT [R_21] FOREIGN KEY ([userName]) REFERENCES [Courier]([userName])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [CurrentlyDriving]
	ADD CONSTRAINT [R_22] FOREIGN KEY ([registrationNum]) REFERENCES [Vehicle]([registrationNum])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [EverDriven]
	ADD CONSTRAINT [R_9] FOREIGN KEY ([userName]) REFERENCES [Courier]([userName])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [EverDriven]
	ADD CONSTRAINT [R_10] FOREIGN KEY ([registrationNum]) REFERENCES [Vehicle]([registrationNum])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Package]
	ADD CONSTRAINT [R_17] FOREIGN KEY ([currently_atAdress]) REFERENCES [Adress]([idAdress])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Package]
	ADD CONSTRAINT [R_18] FOREIGN KEY ([idPackage]) REFERENCES [PackageRequest]([idPackage])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [PackageInVehicle]
	ADD CONSTRAINT [R_19] FOREIGN KEY ([registrationNum]) REFERENCES [Vehicle]([registrationNum])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [PackageInVehicle]
	ADD CONSTRAINT [R_20] FOREIGN KEY ([idPackage]) REFERENCES [PackageRequest]([idPackage])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [PackageRequest]
	ADD CONSTRAINT [R_11] FOREIGN KEY ([userName]) REFERENCES [Users]([userName])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [PackageRequest]
	ADD CONSTRAINT [R_12] FOREIGN KEY ([fromAdress]) REFERENCES [Adress]([idAdress])
		ON DELETE NO ACTION
		ON UPDATE no action
go

ALTER TABLE [PackageRequest]
	ADD CONSTRAINT [R_13] FOREIGN KEY ([toAdress]) REFERENCES [Adress]([idAdress])
		ON DELETE NO ACTION
		ON UPDATE no action
go


ALTER TABLE [Parked]
	ADD CONSTRAINT [R_15] FOREIGN KEY ([idStockroom]) REFERENCES [Stockroom]([idStockroom])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Parked]
	ADD CONSTRAINT [R_16] FOREIGN KEY ([registrationNum]) REFERENCES [Vehicle]([registrationNum])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Stockroom]
	ADD CONSTRAINT [R_2] FOREIGN KEY ([idAdress]) REFERENCES [Adress]([idAdress])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Users]
	ADD CONSTRAINT [R_3] FOREIGN KEY ([idAdress]) REFERENCES [Adress]([idAdress])
		ON DELETE NO ACTION
		ON UPDATE no action
go
----------------------------------------------------------------------------

---INSERT PACKAGE TRIGGER

create procedure calculatePrice
		@idPackage int,
		@price decimal(10,3) output

as
begin
declare @weight decimal(10,3);
declare @type int;
declare @fromAdress int;
declare @toAdress int;
declare @xcordFrom int;
declare @ycordFrom int;
declare @xcordTo int;
declare @ycordTo int;
declare @res int;
declare @osnovnaCena int;
declare @cenaKg int;





select @weight =weight, @fromAdress = fromAdress, @toAdress = toAdress, @type = type
	from PackageRequest 
		where idPackage = @idPackage;

select @xcordFrom = xCord, @ycordFrom = yCord from Adress where idAdress = @fromAdress;
select @xcordTo = xCord, @ycordTo = yCord from Adress where idAdress = @toAdress;

set @res=  (@xcordFrom- @xcordTo)* (@xcordFrom- @xcordTo) + (@ycordFrom- @ycordTo)* (@ycordFrom- @ycordTo);

set @osnovnaCena= 115;
set @cenaKg = 0;
 if(@type=1)
 begin 
set @osnovnaCena= 175;
set @cenaKg = 100;
	
 end
 else if(@type=2)
 begin 
set @osnovnaCena= 250;
set @cenaKg = 100;
	
 end
 else if(@type=1)
 begin 
set @osnovnaCena= 350;
set @cenaKg = 500;
	
 end

 set @price = (CAST(@osnovnaCena as decimal(10,3))  +@weight * CAST(@cenaKg as decimal(10,3)))*SQRT(CAST(@res as decimal(10,3))); 

end




create trigger PackageInsert
on PackageRequest
after insert, update
as 
begin

declare @id int;

declare @price decimal(10,3);
declare @curs cursor;
declare @idFrom int;
set @curs= cursor for 
			select  idPackage, fromAdress from inserted;


open @curs;
fetch next from @curs into @id, @idFrom;
while (@@FETCH_STATUS=0)
begin
   exec calculatePrice @id, @price output;
insert into Package(idPackage, status, price, currently_atAdress, created_at) values (@id, 0, @price, @idFrom, GETDATE());
fetch next from @curs into  @id, @idFrom;
end


close @curs;
deallocate @curs;
end
---------------------------------------- 
--STORED PROCEDURE

create procedure sp_takeVehicle
			@user_id varchar(100),
			@available_vehicle varchar(100) output
as
begin
		declare @status int;
		declare @isDriving smallint;
		select @isDriving = status 
					from Courier 
					where userName= @user_id;
		if(@isDriving=1)
			begin 
			set @status= -1;
			set @available_vehicle = null;
			return @status;
			end;
		
		set @status = 0;
		select top 1 @available_vehicle = Parked.registrationNum 
				from Users, Parked , Stockroom 
				where  Parked.idStockroom = Stockroom.idStockroom and Users.userName =@user_id and Users.idAdress= Stockroom.idAdress;
		
		if(@available_vehicle is null) set @status =-2;
		else 
				begin
							update Courier 
								set currentlyDriving= @available_vehicle, status=1
									where userName = @user_id;
							
							delete from Parked
							where registrationNum= @available_vehicle;

				end
		
		return @status 
end

go 

create procedure sp_eraseAll
as 
begin
 delete from CurrentlyDriving where 1=1;
 delete from Package where 1=1;
 delete from PackageInVehicle where 1=1;
 delete from PackageRequest where 1=1;
 delete from EverDriven where 1=1;
 delete from Parked where 1=1;
 delete from Administrator where 1=1;
 delete from Courier where 1=1;
 delete from CourierRequests where 1=1;
 delete from Vehicle where 1=1;
 delete from Stockroom where 1=1;
 delete from Users where 1=1;
 delete from Adress where 1=1;
 delete from City where 1=1;

end

------------------------


--UNIQUE INDEX ON REGISTRATION NUM

create unique index uqVehicle
on CurrentlyDriving(registrationNum);


--------------------------------

--TRIGERI ZA CASCADE UPDATE, POKRETATI SAMO AKO JE NEOPHODNO



CREATE trigger tr_updatePrimaryKeyVehicle
on Vehicle
after update
as 
begin
   declare @old_id char(18) ;
    declare @new_id char(18) ;
   declare @cursor_old cursor;
   declare @cursor_new cursor;
   set @cursor_old= cursor for 
				select registrationNum 
					from deleted;
	 set @cursor_new= cursor for 
				select registrationNum 
					from inserted;
	fetch next from @cursor_old into @old_id;
	fetch next from @cursor_new into @new_id;

	while(@@FETCH_STATUS=0)
	begin
			if(@old_id<>@new_id) 
				begin
							update EverDriven
								set registrationNum=@new_id
								where registrationNum=@old_id
				end
			
			fetch next from @cursor_old into @old_id;
			fetch next from @cursor_new into @new_id;
	end

	close @cursor_old;
	close @cursor_new;
	deallocate @cursor_old;
	deallocate @cursor_new



end
GO


create trigger tr_updatePrimaryKeyAdress
on Adress
after update 
as 
begin
 declare @old_id int ;
    declare @new_id int ;
   declare @cursor_old cursor;
   declare @cursor_new cursor;
   set @cursor_old= cursor for 
				select idAdress 
					from deleted;
	 set @cursor_new= cursor for 
				select idAdress 
					from inserted;
	fetch next from @cursor_old into @old_id;
	fetch next from @cursor_new into @new_id;

	while(@@FETCH_STATUS=0)
	begin
			if(@old_id<>@new_id) 
				begin
							update Users
								set idAdress=@new_id
								where idAdress=@old_id;
							update Package
								set fromAdress = @new_id
								where fromAdress = @old_id;
							update Package
								set toAdress = @new_id
								where toAdress = @old_id;
							update Package
								set currently_atAdress = @new_id
								where currently_atAdress= @old_id


				end
			
			fetch next from @cursor_old into @old_id;
			fetch next from @cursor_new into @new_id;
	end

	close @cursor_old;
	close @cursor_new;
	deallocate @cursor_old;
	deallocate @cursor_new




end
go 


------------------------------------
--POPUNJAVANJE BAZE


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





