package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.EmployeeService;
import com.udacity.jdnd.course3.critter.Service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    CustomerService customerService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = convertCustomerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerService.save(customer);
        return convertCustomerToCustomerDTO(savedCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> DTOList = new ArrayList<>();
        customerService.getAllCustomers().forEach(i -> {
            DTOList.add(convertCustomerToCustomerDTO(i));
        });

        return DTOList;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Customer customer = customerService.findByPet(petId);
        return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee=convertEmployeeDTOToEmployee(employeeDTO);
        Employee savedEmployee=employeeService.save(employee);
        return convertEmployeeToEmployeeDTO(savedEmployee);

    }

    @GetMapping("/employee/{employeeId}")                               //changed annotation to GetMapping
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.save(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        List<EmployeeDTO> ableAndAvailableEmpDTOs = new ArrayList<>();
        allEmployees.forEach(i -> {
            if (i.getDaysAvailable().contains(employeeDTO.getDate().getDayOfWeek()) && i.getSkills().containsAll(employeeDTO.getSkills())) {
                ableAndAvailableEmpDTOs.add(convertEmployeeToEmployeeDTO(i));
            }
        });
        return ableAndAvailableEmpDTOs;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO, "pets");
        // Convert List of Pets to List of PetIds
        List<Long> petIds = new ArrayList<>();
        List<Pet> pets = customer.getPets();
        if (pets != null) {
            for (Pet p : pets) {
                petIds.add(p.getId());
            }
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer, "petIds");
        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null) {
            List<Pet> pets = petService.getAllPetsByIds(petIds);
            customer.setPets(pets);
        }
        return customer;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO, "skills", "daysAvailable");
        Set<EmployeeSkill> skills = employee.getSkills();
        employeeDTO.setSkills(skills);
        Set<DayOfWeek> daysAvailable = employee.getDaysAvailable();
        employeeDTO.setDaysAvailable(daysAvailable);
        return employeeDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee=new Employee();
        BeanUtils.copyProperties(employeeDTO,employee,"skills","daysAvailable");
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());
        return employee;

    }

}
