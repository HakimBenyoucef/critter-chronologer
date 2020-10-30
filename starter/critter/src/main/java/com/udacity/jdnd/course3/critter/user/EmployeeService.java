package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findById(long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for ID : " + employeeId));
    }

    public List<Employee> findByIdIn(List<Long> employeeIds) {
        return employeeRepository.findAllById(employeeIds);
    }

    public List<Employee> findAvailableEmployees(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek) {

        List<Employee> employees = employeeRepository.findAllBySkillsInAndDaysAvailableContains(skills, dayOfWeek);

        List<Employee> result = new ArrayList<>();
        employees.stream().forEach(employee -> {
            if(employee.getSkills().containsAll(skills)){
                result.add(employee);
            }
        });
        return result;
    }
}