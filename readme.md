
What is it? 
============

A simple cache service implemented as REST WS, with Hibernate as persistence level .


Overview
===========
The main aim of this project was to develop a simple caching service which allows to save temporary the key-value pairs in the database.

In order to test the applications we make use of  JUnit, Spring-test and Mockito libraries (the latter for so called mock object techniques). 


System Requirements 
=====================
In order to  compile and deploy these applications the following software is needed.

* Maven 3.x

* Java JDK 1.8


Building
===========
mvn clean package

Running
===========
java -jar <i>path to jar file</i>

The service will start on localhost:8080


Rest service is available at the following end points (base URI is omitted):
______________________________________________________________
No.				URI						HTTP Method	
______________________________________________________________
0				/						    POST		

1				/{key}					GET			
______________________________________________________________


