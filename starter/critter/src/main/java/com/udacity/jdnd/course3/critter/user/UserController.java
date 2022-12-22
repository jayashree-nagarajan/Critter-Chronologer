package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private PetService petService;


    public UserController(CustomerService customerService, PetService petService,EmployeeService employeeService) {
        this.customerService = customerService;
        this.petService = petService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        //System.out.println("Test print");
        //throw new UnsupportedOperationException();
        Customer customer = customerService.save(customerDTOToCustomerFunction.apply(customerDTO));
        return customerToCustomerDTOFunction.apply(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAll().stream().map(customerToCustomerDTOFunction).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getById(petId);
        return customerToCustomerDTOFunction.apply(pet.getCustomer());
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.save(employeeDTOToEmployeeFunction.apply(employeeDTO));
        return employeeToEmployeeDTOFunction.apply(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) throws Exception {
        return employeeToEmployeeDTOFunction.apply(employeeService.findById(employeeId));

    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) throws Exception {
        Employee employee = employeeService.findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.save(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        HashSet<EmployeeSkill> requiredSkills = new HashSet<>(employeeDTO.getSkills()) ;
        LocalDate requiredDate = employeeDTO.getDate();
        return employeeService.findEmployeesForService( requiredDate,requiredSkills)
                    .stream()
                    .map(employeeToEmployeeDTOFunction)
                    .collect(Collectors.toList());

    }

    Function<Customer, CustomerDTO> customerToCustomerDTOFunction = customer -> {
        CustomerDTO newCustomerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, newCustomerDTO);
        if (customer.getPets() != null) {
            newCustomerDTO.setPetIds(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        }
        return newCustomerDTO;
    };

    Function<CustomerDTO, Customer> customerDTOToCustomerFunction = customerDTO -> {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        if (customerDTO.getPetIds() != null) {
            customer.setPets(customerDTO.getPetIds().stream().map(id -> petService.getById(id)).collect(Collectors.toList()));
        }
        return customer;
    };
    Function<Employee, EmployeeDTO> employeeToEmployeeDTOFunction = employee -> {
        EmployeeDTO newEmployeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, newEmployeeDTO);
        return newEmployeeDTO;
    };

    Function<EmployeeDTO, Employee> employeeDTOToEmployeeFunction = employeeDTO -> {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    };

}
