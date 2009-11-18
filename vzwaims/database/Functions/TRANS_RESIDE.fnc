CREATE OR REPLACE function trans_reside(resides varchar2)
return number is
  resides_code int ;
begin
	 if (resides = 'firewall') then
	 	resides_code := 1 ;
	 elsif (resides = 'inside-carrier-network') then
 	 	resides_code := 2 ;
	 elsif (resides = 'managed/hostedoffsite') then
  	 	resides_code := 3 ;
	 elsif (resides is null) then
   	 	resides_code := 1 ;
	 end if ;
	 return resides_code ;
end ;
/

