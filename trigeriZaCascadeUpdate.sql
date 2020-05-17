
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

