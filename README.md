# Software Estimation Tool

## Introduction

The software cost estimating platform will serve as the estimating sub module of Tongji Open source community crowdsourcing platform, and provide the online software project cost estimating service for the vast number of developers, which fills the blank of the domestic online software cost estimating platform.

When the project Publisher publishes the project, it can enter the software cost estimating platform, through the parameter input, carries on the software cost estimation to this project, in order to conveniently and accurately determine the project price, the workload and so on. 

The platform is designed to help project publishers to accurately estimate the cost of software projects, workload estimates, to prevent the project due to the lack of software cost estimates, resulting in uncontrolled software projects.

## How to Use It

### For backend

#### Environment Requirements

- **Java Runtime** : JDK 1.8
- **Database**: MongoDB 3.4
- **Package Management Tools**: Maven
- **Configuration Management tools**: git

#### Get the project

- get the code from github
  > git clone https://github.com/Sixaps/SoftwareEstimation-backend

#### Install dependency

- open the terminal and enter the project folder
- run the command

```
mvn install
```

#### Change the address of the database to your own database address

- open the file: src->main->resources->application.properties
- change the  address of the database to your own database address

#### Run the program

- run the class : src->main->java->estimation->Application.java

#### Some Regulations

- the MongoDB should have enough storage to save the data
- Do not modify database data directly according to data format
- Do not occupy 8011 ports when you want to run the front end



### For frontend

#### Environment Requirements

- **nodejs** : nodejs v6.11.4
- **Package Management Tools**: npm v5.6.0

#### Get the project

- get the code from git lab
  > git clone http://10.60.38.173:8989/shikun/mart/tree/yangbingjie

#### Install dependency

- open the terminal and enter the project folder
- run the command

```
npm install
```

#### Change the ip address and the port of the estimation backend to your own backend

- open the file: config->index.js
- change the  address of the estimation backend to your backend

#### Run the program

- run the command with terminal

```
npm run dev
```

#### Some Regulations

- Do not occupy 8080 ports when you want to run the front end

- Try to use Chrome or Safari browser for access


