package bank.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    public void saveOurUpdate(Customer customer){
        customerRepository.save(customer);
    }

}
