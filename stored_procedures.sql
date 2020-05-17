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