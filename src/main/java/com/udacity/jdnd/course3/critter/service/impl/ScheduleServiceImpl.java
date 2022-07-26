package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.data.CustomerRepository;
import com.udacity.jdnd.course3.critter.data.EmployeeRepository;
import com.udacity.jdnd.course3.critter.data.PetRepository;
import com.udacity.jdnd.course3.critter.data.ScheduleRepository;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.IScheduleService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements IScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public ScheduleServiceImpl(
            ScheduleRepository scheduleRepository,
            EmployeeRepository employeeRepository,
            PetRepository petRepository,
            CustomerRepository customerRepository){
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public Schedule createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        List<Employee> employees = scheduleDTO.getEmployeeIds()
                        .stream()
                        .map(e -> {
                            Optional<Employee> employeeOpt =
                                    employeeRepository.findById(e);
                            if(!employeeOpt.isPresent()){
                                throw new EmployeeNotFoundException();
                            }
                            return employeeOpt.get();
                         })
                .collect(Collectors.toList());

        List<Pet> pets = scheduleDTO.getPetIds()
                .stream()
                .map(p -> {
                    Optional<Pet> petOpt =
                            petRepository.findById(p);
                    if(!petOpt.isPresent()){
                        throw new EmployeeNotFoundException();
                    }
                    return petOpt.get();
                })
                .collect(Collectors.toList());
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        schedule.setDate(scheduleDTO.getDate());

        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> getScheduleByPet(Long petId) {
        Optional<Pet> petOpt = petRepository.findById(petId);
        if(!petOpt.isPresent()){
            throw new PetNotFoundException();
        }
        List<Pet> pets = Collections.singletonList(petOpt.get());
        return scheduleRepository.findByPetsIn(pets);
    }

    @Override
    public List<Schedule> getScheduleByEmployee(Long employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if(!employeeOpt.isPresent()){
            throw new EmployeeNotFoundException();
        }
        List<Employee> employees = Collections.singletonList(employeeOpt.get());
        return scheduleRepository.findByEmployeesIn(employees);
    }

    @Override
    public List<Schedule> getScheduleByCustomer(Long customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if(!customerOpt.isPresent()){
            throw new CustomerNotFoundException();
        }
        List<Pet> pets = petRepository.findByCustomerId(customerId);
        if(pets.size() == 0){
            throw new PetNotFoundException();
        }
        return scheduleRepository.findByPetsIn(pets);
    }
}
