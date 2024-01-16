
SETUP instructions

## Configuration steps for MySQL local database
### MySQL commnad line client
### MySQL Workbench

1. Open MySQL command line client
2. Enter the password(if you have set any)
3. ```mysql> create database fleasy;```
4. ```mysql> use cinema;```
5. ```mysql> create passenger 'rootAdministrator'@'localhost' identified by 'Parola123';```
6. ```mysql> grant all privileges on fleasy.* to 'rootAdministrator'@'localhost';```
7. ```mysql> exit;```
8. Open MySQL Workbench
9. Click on + near the MySQL Connections title
10. Connection name ```fleasy```
11. username ```rootAdministrator```
12. password > store in vault > ```Parola123```
13. test connection > info popup with successful connection
14. Press ok 
