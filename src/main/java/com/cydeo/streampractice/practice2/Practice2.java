package com.cydeo.streampractice.practice2;

import com.cydeo.streampractice.model.*;

import com.cydeo.streampractice.service.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
                .findFirst().orElseThrow(() -> new Exception("No Employee found!"))
                .getSalary();
    }
    //Display the maximum salary an employee gets

    public static Long getMaxSalary() throws Exception {
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
                .mapToLong(i -> i)
                .max().getAsLong();
    }

    //Display the employee(s) who gets the maximum salary
    public static List<Employee> getMaxSalaryEmployee() {
        return getAllEmployee().stream()
                .filter(employee -> {
                    try {
                        return employee.getSalary().equals(getMaxSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    //Display the max salary employee's Job
    public static Job getMaxSalaryEmployeeJob() throws Exception {
        return getMaxSalaryEmployee().get(0).getJob();
    }

    //Display the max salary in Americas Region
    public static long getMaxSalaryInAmericasRegion() {
        return getAllEmployee().stream()
                .filter(employee -> employee.getDepartment()
                        .getLocation().getCountry().getRegion().equals("America"))
                .max(Comparator.comparing(Employee::getSalary))
                .get().getSalary();
    }

    // Display the second max salary an employee gets
    public static Long getSecondMaxSalary() throws Exception {
//        return getAllEmployee().stream()
//                .sorted(Comparator.comparing(Employee::getSalary).reversed())
//                .map(Employee::getSalary)
//                .skip(1)
//                .findFirst().get();
//
//    }
        //second option
        return getAllEmployee().stream()
                .filter(employee -> {
                    try {
                        return employee.getSalary().compareTo(getSecondMaxSalary()) < 0;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .findFirst().orElseThrow(() -> new Exception("No employee found")).getSalary();

    }

    // Display the Employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() {
        return getAllEmployee().stream()
                .filter(employee -> {
                    try {
                        return employee.getSalary().equals(getSecondMaxSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {
        return getAllEmployee()
                .stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .findFirst().get().getSalary();
    }

    //Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {
        return getAllEmployee().stream()
                .filter(employee -> {
                    try {
                        return employee.getSalary().equals(getMinSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() throws Exception {
        return getAllEmployee().stream()
                .filter(employee -> {
                    try {
                        return employee.getSalary().compareTo(getSecondMinSalary()) < 0;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .sorted(Comparator.comparing(Employee::getSalary))
                .findFirst().orElseThrow(() -> new Exception("No employee found")).getSalary();

    }
//        //Display the employee (s) who gets the second minimum salary
//    public static List<Employee> getSecondMinSalaryEmployee(){
//        return getAllEmployee().stream()
//                .filter(employee -> employee.getSalary().equals(getMinSalary()) < 0 )
//                    .sorted(Comparator.comparing(Employee::getSalary))
//                .findFirst().orElseThrow(()-> new Exception("No Employees Found")).getSalary();
//
//    }
//
    // Display the average salary of the employees
    public static Double getAverageSalary(){
        return getAllEmployee().stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));

//        return getAllEmployee().stream()
//                .map(Employee::getSalary)
//                .mapToDouble(salary->salary)
//                .average().orElse(Double.NaN);//not a number
    }
    // Display all the employees who are making more than average salary
    public static  List<Employee> getEmployeeAboveAverage(){
        return getAllEmployee().stream()
                .filter(employee -> employee.getSalary()> getAverageSalary())
                .collect(Collectors.toList());
    }
    //Display all employees who are making less than average salary
    public static List<Employee> getEmployeeBelowAverage(){
        return getAllEmployee().stream()
                .filter(employee -> employee.getSalary()<getAverageSalary())
                .collect(Collectors.toList());
    }
    //Display all the employees separated based on their department id number
    public static Map<Long ,List <Employee>>  getAllEmployeeForEachDepartment(){
        return getAllEmployee().stream()
                .collect(Collectors.groupingBy(employee -> employee.getDepartment().getId()));
    }
    //Display the total amount of departments
    public static Long getTotalDepartmentsNumber(){
        //return getAllDepartments().stream().count();
        return (long) getAllDepartments().size();
    }
  //Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlysaAndManagersFirstNameEleniAndDepartmentNameIsSales() throws Exception{
        return getAllEmployee().stream()
                .filter(employee -> employee.getFirstName().equals("Alyssa") && employee.getFirstName().equals("Eleni")
                && employee.getDepartment().equals("Sales"))
                .findFirst().get();
    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder(){
        return getAllJobHistories().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate))
                .collect(Collectors.toList());
    }
    //Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005(){
        return getAllJobHistories().stream()
                .filter(jobHistory -> jobHistory.getStartDate().isAfter(LocalDate.of(2005, 1,1)))
                .collect(Collectors.toList());
    }
    //Display all the job histories where the end date is 31.12.2007 and job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer(){
        return getAllJobHistories().stream()
                .filter(jobHistory -> jobHistory.getEndDate().equals(LocalDate.of(2007,12,31)))
                .filter(jobHistory -> jobHistory.getJob().getJobTitle().equals("Programmer"))
                .collect(Collectors.toList());
    }
    //Display the employee whose job history start date in 01.01.2007 and job history and date is 31.12.2007 and department's name is 'Shipping
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping()throws Exception{
        return getAllJobHistories().stream()
                .filter(jobHistory -> jobHistory.getStartDate().equals(LocalDate.of(2007,1,1)))
                .filter(jobHistory -> jobHistory.getEndDate().equals(LocalDate.of(2007,12,31)))
                .filter(jobHistory -> jobHistory.getDepartment().getDepartmentName().equals("Shipment"))
                .findFirst().get().getEmployee();
    }

    //Display all employees whose name starts with 'A'
    public static List<Employee> getAllEmployeesStartsWithA(){
        return getAllEmployee().stream()
                .filter(employee -> employee.getFirstName().startsWith("A"))
                .collect(Collectors.toList());
    }
    //Display all employees whose job id contains "IT
    public static List<Employee> getAllEmployeesJobIdContainsIT(){
        return getAllEmployee().stream()
                .filter(employee -> employee.getJob().getId().contains("IT"))
                .collect(Collectors.toList());
    }
    //Display the number of employee whose job title is programmer and department name is "IT"
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT(){
        return getAllEmployee().stream()
                .filter(employee -> employee.getJob().getJobTitle().equals("Programmer"))
                .filter(employee -> employee.getDepartment().getDepartmentName().equals("IT"))
                .count();
    }
    // Display all employees whose department ID is 50,80 or 100 // if we go with OR we can't use multiple filters
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100(){
        return getAllEmployee().stream()
                .filter(employee -> employee.getDepartment().getId().equals(50L)
                || employee.getDepartment().getId().equals(80L)
                || employee.getDepartment().getId().equals(100L))
                .collect(Collectors.toList());

    }
    //Display the initials of all the employees
    //Note: You can assume there is no middle name
    public static List<String> getAllEmployeesInitials(){
        return getAllEmployee().stream()
                .map(employee -> {String firstInitial=employee.getFirstName().substring(0,1);
                String secondInitial = employee.getLastName().substring(0,1);
                return firstInitial + secondInitial;})
                .collect(Collectors.toList());
    }
    //Display the full nae of all the employees
    public static List<String> getAllEmployeesFullNames() {
        return getAllEmployee().stream()
                .map(employee -> {String firstName = employee.getFirstName();
                    String lastName = employee.getLastName();
                    return firstName + " " + lastName;})
                .collect(Collectors.toList());
        // second solution

//        return getAllEmployee().stream()
//                .map(employee -> employee.getFirstName() + " " + employee.getLastName())
//                .collect(Collectors.toList());

    }
    //Display the length of the longest ful name

    public static Integer getLongestName(){
        return getAllEmployeesFullNames().stream()
                .max(Comparator.comparing(String::length))
                .get().length();

    }
    //Display the employees (s) with the longest full name (s)
    public static List<Employee> getLongestNamedEmployee(){
        return getAllEmployee().stream()
                .filter(employee -> {
                    try {
                        return employee.getFirstName().length() + employee.getLastName().length() + 1
                                == getLongestName();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }




































    }



