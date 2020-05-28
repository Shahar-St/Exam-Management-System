# Exam-Management-System

Developers: Shahar-St, GalMirovsky, adar2 and ronnieshafran.

## What is this project?

This project is a university system to manage and execute exams.
* *Teachers* can create and edit questions and then combine a few into an exam. They can execute exams online and view past exams statistics.
* *Students* can take exams online or download them to their local machine. They cam also watch their prev exams and grades.
* *Dean* can watch different types of statistical reports and approve time extension during exams.

## How was it built?

Written in `Java`, GUI with `JavaFX`, built with `Maven` and stored in `MySQL` using `Hibernate`. 
The project was built in a server-client architecture and is separated into 3 modules `Server`, `Client` and `CommonLibs`. 

* The *Server* supports multiple user simultaneously and holds an SQL DB. it has a command line shell as an interface.
* Thin *Client* that is being managed by `EventBus`.
* *CommonLibs* holds the requests and responses classes, as well as classes for "light" entities.

## How do I run it?
*you need to have MySql installed on your machine.*
1) Download server.jar and client.jar from the main dir.
2) Open the archive file and open the `hibernate.properties` file and make the required changes.
3) run server.jar from the command line and follow the instructions until it "Server connected" message appears.
4) run client.jar, you can lookup usernames and passwords in the DB.

