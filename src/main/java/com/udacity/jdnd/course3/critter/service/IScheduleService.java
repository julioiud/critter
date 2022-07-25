package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;

import java.util.List;

public interface IScheduleService {

    Schedule createSchedule(ScheduleDTO scheduleDTO);

    List<Schedule> getAllSchedules();

    List<Schedule> getScheduleByPet(Long petId);

    List<Schedule> getScheduleByEmployee(Long employeeId);

    List<Schedule> getScheduleByCustomer(Long customerId);
}
