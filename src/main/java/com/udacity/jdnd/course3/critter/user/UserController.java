package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.service.IUserService;
import com.udacity.jdnd.course3.critter.util.UserUtil;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;
    private final UserUtil userUtil;

    public UserController(IUserService userService,
                          UserUtil userUtil){
        this.userService = userService;
        this.userUtil = userUtil;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
       Customer customer = userUtil.convertCustomerDTOToCustomer(customerDTO);
       customer = userService.createCustomer(customer);
       return userUtil.convertCustomerToCustomerDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = userService.getAllCustomers();
        return userUtil.convertCustomersToCustomersDTO(customerList);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = userService.getCustomerByPetId(petId);
        return userUtil.convertCustomerToCustomerDTO(customer);

    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = userUtil.convertEmployeeDTOToEmployee(employeeDTO);
        employee = userService.createEmployee(employee);
        return userUtil.convertEmployeeToEmployeeDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = userService.getEmployeeById(employeeId);
        return userUtil.convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.addEmployeSchedule(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = userService.checkAvailability(employeeDTO);
        return userUtil.convertEmployeesToEmployeesDTO(employees);
    }

}
