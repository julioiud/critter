package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface IUserService {

    Customer createCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerByPetId(Long petId);

    Employee createEmployee(Employee employee);

    Employee getEmployeeById(Long id);

    void addEmployeSchedule(Set<DayOfWeek> availables, Long employeeID);

    List<Employee> checkAvailability(EmployeeRequestDTO employeeDTO);
}
