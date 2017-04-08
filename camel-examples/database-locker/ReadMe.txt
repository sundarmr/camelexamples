
failover for camel routes with database

Create a table that has the below columns
=================================================
ServerName - Where the application will be deployed
Time - A millisecond holder which depicts the lock hold time
Application Name - This is needed if you have multiple applications that need the fail-over
================================================
Each application will then have two routes
=================================================
The actual consumer route which will be marked as autostart false ( borrowed from Christian Schneider's Code  at https://github.com/cschneider/simplecluster/) 
A route which checks if the lock is owned by it currently and then stops or starts the  consumers of the above route


