package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.data.CustomerRepository;
import com.udacity.jdnd.course3.critter.data.EmployeeRepository;
import com.udacity.jdnd.course3.critter.data.PetRepository;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.IUserService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public UserServiceImpl(
            CustomerRepository customerRepository,
            PetRepository petRepository,
            EmployeeRepository employeeRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        customers.stream().forEach(c -> {
            List<Pet> pets = c.getPets();
        });
        return customers;
    }

    @Transactional
    @Override
    public Customer getCustomerByPetId(Long petId) {
        Optional<Pet> petOpt = petRepository.findById(petId);
        if(!petOpt.isPresent()){
            throw new PetNotFoundException();
        }
        List<Pet> pets = Collections.singletonList(petOpt.get());
        List<Customer> customers = customerRepository.findByPetsIn(pets);
        Customer customer = customers.get(0);
        customer.setPets(pets);
        return customers.get(0);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void addEmployeSchedule(Set<DayOfWeek> availables, Long employeeID) {
        Optional<Employee> employeeDBOpt = employeeRepository.findById(employeeID);
        if(!employeeDBOpt.isPresent()){
            throw new EmployeeNotFoundException();
        }
        Employee employeeDB = employeeDBOpt.get();
        employeeDB.setAvailables(availables);
        employeeRepository.save(employeeDB);
    }

    @Override
    public List<Employee> checkAvailability(EmployeeRequestDTO employeeDTO) {
        DayOfWeek dayOfWeek = employeeDTO.getDate().getDayOfWeek();

        Set<EmployeeSkill> skills = employeeDTO.getSkills();

        List<Employee> employees = employeeRepository.findDistinctByAvailablesAndSkillsIn(dayOfWeek, skills);

        if(skills.size() == 1){
            return employees;
        }

        List<Employee> employeesResult =
                employees.stream()
                .filter(e -> containAll(e.getSkills(), skills))
                .collect(Collectors.toList());

        return employeesResult;
    }

    public static boolean containAll(Set<EmployeeSkill> big, Set<EmployeeSkill> small) {
        return big.containsAll(small);
    }
}
