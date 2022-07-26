package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.service.IScheduleService;
import com.udacity.jdnd.course3.critter.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final IScheduleService scheduleService;
    private final UserUtil userUtil;

    @Autowired
    public ScheduleController(IScheduleService scheduleService,
                              UserUtil userUtil){
        this.scheduleService = scheduleService;
        this.userUtil = userUtil;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.createSchedule(scheduleDTO);
        return userUtil.convertScheduleToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return userUtil.convertSchedulesToSchedulesDTO(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleByPet(petId);
        return userUtil.convertSchedulesToSchedulesDTO(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleByEmployee(employeeId);
        return userUtil.convertSchedulesToSchedulesDTO(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleByCustomer(customerId);
        return userUtil.convertSchedulesToSchedulesDTO(schedules);
    }
}
