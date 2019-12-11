package bank.controller;

import bank.Greeting;
import bank.database.Customer;
import bank.database.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/customer")
    private int saveCustomer(@RequestBody Customer customer){
        customerService.saveOurUpdate(customer);

        return customer.getId();
    }



}
