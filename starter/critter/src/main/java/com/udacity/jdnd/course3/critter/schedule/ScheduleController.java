package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.Pet;
import com.udacity.jdnd.course3.critter.user.Schedule;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;



    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);

        if(compareSchedule(schedule)){
            return null;
        }
        return convertScheduleToScheduleDTO(this.scheduleService.saveSchedule(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = this.scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule schedule:schedules){
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = this.scheduleService.getSchedulesByPetId(petId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule schedule:schedules){
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = this.scheduleService.getSchedulesByEmployeeId(employeeId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule schedule:schedules){
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = this.scheduleService.getScheduleByCustomerId(customerId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule schedule:schedules){
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }


    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        scheduleDTO.setActivities(schedule.getActivities());

        List<Pet> pets = schedule.getPet();
        List<Long> petId = new ArrayList<>();
        for (Pet pet : pets) {
            petId.add(pet.getId());
        }
        scheduleDTO.setPetIds(petId);
        List<Employee> employees = schedule.getEmployee();
        List<Long> employeeId = new ArrayList<>();
        for (Employee employee : employees) {
            employeeId.add(employee.getId());
        }
        scheduleDTO.setEmployeeIds(employeeId);
        return scheduleDTO;

    }

    private boolean compareSchedule(Schedule schedule){
        //System.out.println("Inside controller compareSchedule");
        if(!schedule.getEmployee().isEmpty() && !schedule.getPet().isEmpty()) {
            List<Schedule> checkScheduleDate = this.scheduleService.getAllSchedules();
            //System.out.println("Inside controller getAllSchedules : "+ checkScheduleDate.size());
            if(!checkScheduleDate.isEmpty()){

                for (Schedule s:
                        checkScheduleDate ) {
                    //System.out.println("Inside controller checkIfEqual : "+ s.getEmployee().containsAll(schedule.getEmployee()));
                    if(s.getEmployee().containsAll(schedule.getEmployee()) && s.getDate().equals(schedule.getDate()))
                        return true;
                }
                //return checkScheduleDate.contains(schedule);
            }

        }
        return false;
    }
    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();

        Set<Employee> employeeSkillSet = employeeService.findEmployeesForService(scheduleDTO.getDate(),new HashSet<>(scheduleDTO.getActivities()));
        List<Long> employeeIdsNew = new ArrayList<>();
        if(!employeeSkillSet.isEmpty()) {
            for (Employee e : employeeSkillSet
            ) {
                employeeIdsNew.add(e.getId());
            }
        }

        List<Employee> employeeList = new ArrayList<>();
        List<Pet> petList = new ArrayList<>();


        //if (!employeeIdsNew.isEmpty()){
            employeeIds.forEach(employeeId -> {
                try {

                        employeeList.add(employeeService.findById(employeeId));

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });
        //}

        if (petIds != null){
            petIds.forEach(petId -> {
                try {
                    petList.add(petService.getById(petId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        schedule.setEmployee(employeeList);
        schedule.setPet(petList);

        return schedule;
    }
}
