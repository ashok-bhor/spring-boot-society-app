select * from society.roles;
select * from society.users;
select * from society.users_roles;

select * from society.extrachargesentry;
select * from society.actionedpaymentsdetails where flatnumber=402;
select * from society.maintenancemasterentry;
select * from society.deletedextracharges;
select * from society.approvalpendingpayments;
select * from society.societymaintenancepaidhistory where flatnumber=402;
select * from society.society_maintenance_values_year_wise where flattype='1BHK';
select * from society.flat_type_flat_number_map;
select sum(amount) from society.societymaintenancepaidhistory where flatnumber=204
SELECT u.username, r.name AS role
FROM society.users u
JOIN society.users_roles ur ON u.user_id = ur.user_id
JOIN society.roles r ON ur.role_id = r.role_id;

society.flat_type_flat_number_map


drop table society.actionedpaymentsdetails;
drop table society.approvalpendingpayments;
drop table society.deletedextracharges;
drop table society.extrachargesentry;
drop table society.maintenancemasterentry;


