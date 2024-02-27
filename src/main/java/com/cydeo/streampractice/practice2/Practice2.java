package com.cydeo.streampractice.practice2;

import com.cydeo.streampractice.model.*;

import com.cydeo.streampractice.service.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Practice2 {


    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice2(CountryService countryService, DepartmentService departmentService,
                     EmployeeService employeeService, JobHistoryService jobHistoryService,
                     JobService jobService, LocationService locationService,
                     RegionService regionService) {

        Practice2.countryService = countryService;
        Practice2.departmentService = departmentService;
        Practice2.employeeService = employeeService;
        Practice2.jobHistoryService = jobHistoryService;
        Practice2.jobService = jobService;
        Practice2.locationService = locationService;
        Practice2.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    // Display all the employees

    public static List<Employee> getAllEmployee() {
        return employeeService.readAll();
    }

    // Display all the countries
    public static List<Country> getAllCountries() {
        return countryService.readAll();
    }

    // Display all the departments
    public static List<Department> getAllDepartments() {
        return departmentService.readAll();
    }

    // Display all the jobs
    public static List<Job> getAllJobs() {
        return jobService.readAll();
    }

    // Display all the locations

    public static List<Location> getAllLocations() {
        return locationService.readAll();
    }

    // Display all the regions
    public static List<Region> getAllRegions() {
        return regionService.readAll();
    }

    // Display all the job histories

    public static List<JobHistory> getAllJobHistories() {
        return jobHistoryService.readAll();
    }

    // Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {
        return getAllEmployee().stream()
                .map(Employee::getFirstName)
                .collect(Collectors.toList());
    }

    // Display all the countries' names
    public static List<String> getAllCountryName() {
        return getAllCountries().stream()
                .map(country -> country.getCountryName())
                .collect(Collectors.toList());
    }

    // Display all the departments' managers' first names
    public static List<String> getAllDepartmentManagerFirstName() {
        return getAllDepartments().stream()
                .map(department -> department.getManager().getFirstName())
                .collect(Collectors.toList());
    }
    // Display all the departments where manager name of the department is 'Steven'

    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {
        return getAllDepartments().stream()
                .filter(department -> department.getManager().getFirstName().equals("Steven"))
                .collect(Collectors.toList());
    }

    // Display all the departments where postal code of the location of the department is '98199'

    public static List<Department> getAllDepartmentsWhereLocationPostCodeIs98199() {
        return getAllDepartments().stream()
                .filter(department -> department.getLocation().getPostalCode().equals("98199"))
                .collect(Collectors.toList());
    }

    // Display the region of the IT department
    public static Region getRegionOfItDepartment() throws Exception {
//        return getAllDepartments().stream()
//                .filter(department -> department.getDepartmentName().equals("IT"))
//                .findFirst().get().getLocation().getCountry().getRegion();

        return getAllDepartments().stream()
                .filter(department -> department.getDepartmentName().equals("IT"))
                .findFirst().orElseThrow(() -> new Exception("No Department Found!"))//orElse()will return what we put inside  / is working with lambda
                .getLocation().getCountry().getRegion();

    }

    //Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {
        return getAllDepartments().stream()
                .filter(department -> department.getLocation()
                        .getCountry().getRegion().equals("Europe"))
                .collect(Collectors.toList());
    }

    //Display if there is any employee with salary less than 1000.
    //If there is none,the methode should return true
    public static boolean checkIfThereIsNoSalaryLessThn1000() {
//        return getAllEmployee().stream()
//                .anyMatch(employee -> employee.getSalary()>1000);
//        return getAllEmployee().stream()
//                .noneMatch(employee -> employee.getSalary()<1000);
        return !getAllEmployee().stream()//will return true
                .anyMatch(employee -> employee.getSalary() < 1000);

    }

    //check if the salaries of all the employees in IT department are greater than 2000(departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
//        return getAllEmployee().stream()
//                .filter(employee -> employee.getDepartment().getDepartmentName().equals("IT"))
//                .noneMatch(employee -> employee.getSalary()<2000);
        return getAllEmployee().stream()
                .filter(employee -> employee.getDepartment().getDepartmentName().equals("IT"))
                .map(Employee::getSalary)//with double collen operator
                .noneMatch(salary -> salary < 2000);

    }

    //Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {
        return getAllEmployee().stream()
                .filter(employee -> employee.getSalary() < 5000)
                .collect(Collectors.toList());
    }

    //Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {
//        return getAllEmployee().stream()
//                .filter(employee -> employee.getSalary()> 6000 && employee.getSalary()<7000)
//                .collect(Collectors.toList());
//    }
        return getAllEmployee().stream()//using 2 filters
                .filter(employee -> employee.getSalary() > 6000)
                .filter(employee -> employee.getSalary() < 7000)
                .collect(Collectors.toList());

    }

    //Display the salary of the employee Grant Douglas(lastName: Grant, firstName: Douglas)
    public static Long getGrantDouglasSalary() throws Exception {
        return getAllEmployee().stream()
                .filter(employee -> employee.equals("Douglas"))
                .filter(employee -> employee.equals("Grant"))
                .findFirst().orElseThrow(()->new Exception("No Employee found!"))
                .getSalary();
    }
    //Display the maximum salary an employee gets

    public static Long getMaxSalary()throws Exception{
//        return getAllEmployee().stream()
//                .sorted(Comparator.comparing(Employee::getSalary).reversed())
//                .findFirst().get().getSalary();
//
//        return getAllEmployee().stream()
//                .sorted(Comparator.comparing(Employee::getSalary).reversed())
//                .limit(1).collect(Collectors.toList()).get(0).getSalary(); // ny using Index

//        return getAllEmployee().stream()
//                .max(Comparator.comparing(Employee::getSalary))
//                .get().getSalary(); //get() returning optional
//
//        return getAllEmployee().stream()
//                .map(Employee:: getSalary)
//                .reduce(Long::max)
//                .get();
        return getAllEmployee().stream()
                .map(Employee::getSalary)
                .mapToLong(i-> i)
                .max().getAsLong();
    }





}








