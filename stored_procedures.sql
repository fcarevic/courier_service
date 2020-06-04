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
 delete from projekat2020.User where 1=1;
 delete from Adress where 1=1;
 delete from City where 1=1;

end