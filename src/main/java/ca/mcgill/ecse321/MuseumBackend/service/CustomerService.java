package ca.mcgill.ecse321.MuseumBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;

@Service
public class CustomerService {
  @Autowired
  CustomerRepository customerRepository;
  
  @Transactional
  public Customer getCustomerByPersonRoleId(int id) {
      Customer customer = customerRepository.findCustomerByPersonRoleId(id);
      return customer;
  }
  
  @Transactional
  public Customer createCustmer(Customer customer) {
      customer = customerRepository.save(customer);
      return customer;
  }
}
