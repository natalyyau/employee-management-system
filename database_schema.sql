DROP DATABASE IF EXISTS employeeData;
CREATE DATABASE employeeData;
USE employeeData;
/***********************************************************************/
CREATE TABLE employees (
  empid INT NOT NULL,
  Fname VARCHAR(65) NOT NULL,
  Lname VARCHAR(65) NOT NULL,
  email VARCHAR(65) NOT NULL,
  HireDate DATE,
  Salary DECIMAL(10,2) NOT NULL,
  SSN VARCHAR(12),
  PRIMARY KEY (empid)
);
/***********************************************************************/
CREATE TABLE payroll (
  payID INT,
  pay_date DATE,
  earnings DECIMAL(8,2),
  fed_tax DECIMAL(7,2),
  fed_med DECIMAL(7,2),
  fed_SS DECIMAL(7,2),
  state_tax DECIMAL(7,2),
  retire_401k DECIMAL(7,2),
  health_care DECIMAL(7,2),
  empid INT,
  PRIMARY KEY (payID)
);
/***********************************************************************/ 
CREATE TABLE employee_job_titles (
  empid INT NOT NULL,
  job_title_id INT NOT NULL
);
/***********************************************************************/ 
CREATE TABLE job_titles (
  job_title_id INT,
  job_title VARCHAR(125) NOT NULL,
  PRIMARY KEY (job_title_id)
);
/***********************************************************************/ 
CREATE TABLE employee_division (
  empid int NOT NULL,
  div_ID int NOT NULL,
  PRIMARY KEY (empid)
);
/***********************************************************************/
CREATE TABLE division (
  ID int NOT NULL,
  Name varchar(100) DEFAULT NULL,
  city varchar(50) NOT NULL,
  addressLine1 varchar(50) NOT NULL,
  addressLine2 varchar(50) DEFAULT NULL,
  state varchar(50) DEFAULT NULL,
  country varchar(50) NOT NULL,
  postalCode varchar(15) NOT NULL,
  PRIMARY KEY (ID)
);
/***********************************************************************/ 
CREATE TABLE city (
  cityID int,
  cityName varchar(50),
  PRIMARY KEY (cityID)
);
/***********************************************************************/ 
CREATE TABLE state (
  stateID int,
  stateName varchar(50),
  PRIMARY KEY (stateID)
);
/***********************************************************************/ 
CREATE TABLE address (
  empid int NOT NULL,
  street varchar(100),
  cityID int,
  stateID int,
  zip varchar(10),
  gender varchar(20),
  race varchar(50),
  dob DATE,
  phone varchar(15),
  PRIMARY KEY (empid),
  FOREIGN KEY (empid) REFERENCES employees(empid),
  FOREIGN KEY (cityID) REFERENCES city(cityID),
  FOREIGN KEY (stateID) REFERENCES state(stateID)
);
/***********************************************************************/ 
ALTER TABLE payroll
  ADD CONSTRAINT fk_payroll_emp FOREIGN KEY (empid) REFERENCES employees (empid);
ALTER TABLE employee_job_titles
  ADD CONSTRAINT fk_jobtitles_emp FOREIGN KEY (empid) REFERENCES employees (empid),
  ADD CONSTRAINT fk_jobtitles_title FOREIGN KEY (job_title_id) REFERENCES job_titles (job_title_id);
ALTER TABLE employee_division
  ADD CONSTRAINT fk_empdiv_emp FOREIGN KEY (empid) REFERENCES employees (empid),
  ADD CONSTRAINT fk_empdiv_div FOREIGN KEY (div_ID) REFERENCES division (ID);