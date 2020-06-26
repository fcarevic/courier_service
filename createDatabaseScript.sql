
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
		ON UPDATE CASCADE
go

ALTER TABLE [PackageRequest]
	ADD CONSTRAINT [R_13] FOREIGN KEY ([toAdress]) REFERENCES [Adress]([idAdress])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
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
		ON UPDATE CASCADE
go
