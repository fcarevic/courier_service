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




go
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