package com.example.CustProd.controller;

import com.example.CustProd.exception.ResourceNotFoundException;
import com.example.CustProd.model.Customer;
import com.example.CustProd.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
	@Autowired
	private CustomerRepository employeeRepository;

	@GetMapping("/customer")
	public List<Customer> getAllcustomer() {
		return employeeRepository.findAll();
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Customer employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	@PostMapping("/customer")
	public Customer createEmployee(@Valid @RequestBody Customer employee) {
		return employeeRepository.save(employee);
	}

	@PutMapping("/customer/{id}")
	public ResponseEntity<Customer> updateEmployee(@PathVariable(value = "id") Long employeeId,
												   @Valid @RequestBody Customer employeeDetails) throws ResourceNotFoundException {
		Customer employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));


		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmail(employeeDetails.getEmail());
		employee.setPhone(employeeDetails.getPhone());
		employee.setAddress(employeeDetails.getAddress());
		final Customer updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/customer/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Customer customer = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
