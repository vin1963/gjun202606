Delimiter //
Create procedure GetEmpInOffice
(
   officename varchar(50)  

)
BEGIN   	
    
    select *	
    from employees
    where employees.officecode=(select officecode from offices where city=officename) ;

END //
