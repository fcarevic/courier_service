create trigger checkOnlyOneCar
on Courier
after update,insert
as
begin
declare @regNum varchar(100);
declare @curs cursor;

declare @newStatus int;
declare @num int;
set @curs = cursor for select currentlyDriving,status from inserted;
open @curs;

fetch next from @curs into @regNum, @newStatus;
while @@FETCH_STATUS=0
begin
if(@newStatus=1)
begin
select @num= count(*) from Courier where status=1 and currentlyDriving =@regNum;
if(@num>1) rollback transaction;
end
fetch next from @curs into @regNum, @newStatus;

end

close @curs;
deallocate @curs;
end