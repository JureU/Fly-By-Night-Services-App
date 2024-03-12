# Fly-By-Night-Services-App

Link to the instructions:
https://ningom.tripod.com/instructions2613.html<br />

The application does not fulfill all the requirements of the task above. The solution is implemented for the Data Conversion Tool and user interface. <br />
The programming language used is Java using the Spring Boot framework. HTML, CSS is used to create the GUI and the connection is implemented with Thymeleaf. <br />

The database that was the framework of the task is located in main/java/com/example/ProjektMarand/batabase. There is one new function in Data.java that helps with updating the seats. There are also four new files: <br />
  1.) KonverterBinary.java and DatabaseFillBinary.java -> They convert our data into a binary file and fill the database. <br />
  2.) KonverterText.java and DatabaseFillText.java -> They convert our data into a text file and fill the database. <br />
The basic data is attached in the file Data.txt and is given in ASCII format. There are also two already converted files included: output.txt and output.bin <br />
For easier testing there is also flight_database.db included. <br />

How to run the application: <br />
  1.) Download the project <br />
  2.) Run the application with command: ./mvnw spring-boot:run <br />
  3.) Test it on: http://localhost:8080/ <br />
