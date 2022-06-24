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
@RequestMapping("/api/users")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("")
	public List<Customer> getAllCustomer() {
		return customerRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId)
			throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(Math.toIntExact(customerId))
				.orElseThrow(() -> new ResourceNotFoundException("customer not found for this id :: " + customerId));
		return ResponseEntity.ok().body(customer);
	}

//	@PostMapping("/customer")
//	public Customer createCustomer(@Valid @RequestBody Customer customer) {
//		return customerRepository.save(customer);
//	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long customerId,
												   @Valid @RequestBody Customer customerDetails) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(Math.toIntExact(customerId))
				.orElseThrow(() -> new ResourceNotFoundException("customer not found for this id :: " + customerId));


		customer.setFirstName(customerDetails.getFirstName());
		customer.setLastName(customerDetails.getLastName());
		customer.setEmail(customerDetails.getEmail());
		customer.setPhone(customerDetails.getPhone());
		customer.setAddress(customerDetails.getAddress());
		final Customer updatedCustomer = customerRepository.save(customer);
		return ResponseEntity.ok(updatedCustomer);
	}

	@DeleteMapping("/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId)
			throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(Math.toIntExact(customerId))
				.orElseThrow(() -> new ResourceNotFoundException("customer not found for this id :: " + customerId));

		customerRepository.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Customer deleted successfully", Boolean.TRUE);
		return response;
	}
}
