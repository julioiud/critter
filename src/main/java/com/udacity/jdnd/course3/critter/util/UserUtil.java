package com.udacity.jdnd.course3.critter.util;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserUtil {

    public CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        List<Long> petIds = new ArrayList<>();
        List<Pet> pets = customer.getPets();
        if(pets != null){
            petIds = (customer.getPets())
                    .stream().map(c -> c.getId()).collect(Collectors.toList());
        }
        customerDTO.setPetIds(petIds);
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    public Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    public List<CustomerDTO> convertCustomersToCustomersDTO(List<Customer> customerList){
        if(customerList.size() == 0){
            return new ArrayList<>();
        }
        return customerList.stream().map(c ->{
            return convertCustomerToCustomerDTO(c);
        }).collect(Collectors.toList());
    }

    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setDaysAvailable(employee.getAvailables());
        employeeDTO.setSkills(employee.getSkills());
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }


    public Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setAvailables(employeeDTO.getDaysAvailable());
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    public List<EmployeeDTO> convertEmployeesToEmployeesDTO(List<Employee> employeeList){
        if(employeeList.size() == 0){
            return new ArrayList<>();
        }
        return employeeList.stream().map(e ->{
            return convertEmployeeToEmployeeDTO(e);
        }).collect(Collectors.toList());
    }

    public PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        petDTO.setOwnerId(pet.getCustomer().getId());
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;
    }

    public Pet convertPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    public List<PetDTO> convertPetsToPetsDTO(List<Pet> pets){
        return pets.stream().map(p ->{
            return convertPetToPetDTO(p);
        }).collect(Collectors.toList());
    }

    public ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        List<Long> petIds = new ArrayList<>();
        List<Long> employeeIds = new ArrayList<>();
        Set<EmployeeSkill> activities =  new HashSet<>();

        if(schedule.getPets() != null){
            petIds = schedule.getPets()
                    .stream().map(p -> {
                        return p.getId();
                    })
                    .collect(Collectors.toList());
        }

        if(schedule.getEmployees() != null){
            employeeIds = schedule.getEmployees()
                    .stream().map(e -> {
                        return e.getId();
                    })
                    .collect(Collectors.toList());

           schedule.getEmployees()
                      .stream().forEach(e -> {
                         e.getSkills().stream().forEach(s -> {
                            activities.add(s);
                         });
                      });
        }
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setActivities(activities);

        return scheduleDTO;
    }

    public Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        return schedule;
    }

    public List<ScheduleDTO> convertSchedulesToSchedulesDTO(List<Schedule> schedules){
        if(schedules.size() == 0){
            return new ArrayList<>();
        }
        return schedules.stream().map(s ->{
            return convertScheduleToScheduleDTO(s);
        }).collect(Collectors.toList());
    }
}
