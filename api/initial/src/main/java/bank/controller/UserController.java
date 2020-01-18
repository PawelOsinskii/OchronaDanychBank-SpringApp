package bank.controller;

import bank.models.User;
import bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository users;

    @GetMapping("/balance")
    private ResponseEntity getBalance(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        User currentUser = users.findByEmail(principal.getName());

        return ok(currentUser.getBalance());
    }
    @PostMapping("/send")
    private ResponseEntity sendMoney(HttpServletRequest request, @RequestBody SendBody sendData){
        Principal principal = request.getUserPrincipal();
        User currentUser = users.findByEmail(principal.getName());
        if(sendData.getMoney() <= 0)
            return ok("You have to write amount");
        if(users.findByEmail(sendData.getEmail())==null)
            return ok("bad user");
        User reciver = users.findByEmail(sendData.getEmail());
        if(reciver.getEmail().equals(currentUser.getEmail())){
            return ok("you cant send for yourself");
        }
        if((reciver.getBalance()+sendData.getMoney())>=Long.MAX_VALUE)
            return ok("user has too much money!");
        if(sendData.getMoney()>10000000)
            return ok("you can not send more than 10000000");
        if(currentUser.getBalance() >= sendData.getMoney()) {
            currentUser.setBalance(currentUser.getBalance()-sendData.getMoney());
            reciver.setBalance(reciver.getBalance()+sendData.getMoney());
            users.save(currentUser);
            users.save(reciver);
            return ok("Transfer was send");

        }

        if(currentUser.getBalance() < sendData.getMoney()){
            return ok("You dony have enough money");
        }


        return ok(currentUser.getBalance());
    }

}
