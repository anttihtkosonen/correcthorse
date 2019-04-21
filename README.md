# Correcthorse

A program to generate secure passwords from wordlists chosen by the user.

## What this application is about
Weak passwords are a major problem on the internet today, and with increasing processing power the problem is only going to get worse. There is a way to formulate passwords that are easy for humans to remember and hard for computers to guess, as XKCD described years ago: https://www.xkcd.com/936/

There already are some apps to generate good passwords, but they all are missing some functionality that I think is central. Users should be able to use their own wordlists - this way they can generate passwords in their own language, and even dialect. They can choose  words words that are familiar to them, but unfamiliar to others (like words connected with their hobbies, names of local places etc.)

I wrote this program to try to answer these challenges. The user can load custom lists of words and use them to generate passwords. Also blacklists can be added to list words that are excluded from passwords. Lists of common passwords should be used for this purpose. Lists can also be removed.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

To use this application you need to have the Java JRE 8 which can be downdloaded here: https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html

You also need the H2 database engine, which you can get here: https://h2database.com/html/main.html

This application has only been tested on a linux-system – it might not work correctly on other systems.


### Installing

After you have installed JRE 8 and the H2 engine, you need to download either the source code, or just the jar-file from the /target -directory. 

To download the source code run the following command:
git clone  https://github.com/anttihtkosonen/correcthorse

After downloading run the code with an IDE of your choice.


To get the jar-file, run the following command:
wget https://github.com/anttihtkosonen/correcthorse/tree/master/target/ Passwordapplication-1.0-SNAPSHOT.jar

You can run the jar with the following command:
java -jar Passwordapplication-1.0-SNAPSHOT.jar 



## Built With

* [Java 8](https://www.java.com/en/download/) - The programming language
* [Spring boot](https://spring.io/projects/spring-boot) – The platform
* [Maven](https://maven.apache.org/) - Dependency Management
* [H2](https://h2database.com/html/main.html) – The database engine


## Authors

* **Antti Kosonen** (https://h2database.com/html/main.html)


## License

This project is licensed under the MIT License



