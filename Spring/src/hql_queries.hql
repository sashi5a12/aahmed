--from Suppliers

--select s.supplierId as supplierId, s.companyName, s.address from Suppliers as s

--from Suppliers as supplier where supplier.supplierId=1

--from Suppliers as s inner join s.products where s.supplierId=1

--select distinct s from Suppliers s left join fetch s.products p order by s.supplierId

select min(s.supplierId), max(s.supplierId) from Suppliers s