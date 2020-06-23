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
set @curs= cursor for 
			select  idPackage from inserted;


open @curs;
fetch next from @curs into @id;
while (@@FETCH_STATUS=0)
begin
   exec calculatePrice @id, @price output;
insert into Package(idPackage, status, price) values (@id, 0, @price);
fetch next from @curs into  @id;
end


close @curs;
deallocate @curs;
end


