package bank.controller;

import bank.models.Transaction;
import bank.models.User;
import bank.repository.TransactionRepository;
import bank.repository.UserRepository;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository users;

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/balance")
    private ResponseEntity getBalance(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        User currentUser = users.findByEmail(principal.getName());

        return ok(currentUser.getBalance());
    }
    @GetMapping("/receiveTransactions")
    private ResponseEntity receiveTransactions(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        User currentUser = users.findByEmail(principal.getName());
        StringBuffer response = new StringBuffer();
        List<Transaction> transactionList =  currentUser.getReceivedTransactions();
        for(Transaction tr: transactionList){
            response.append("from: ");
            response.append(tr.getSender().getEmail());
            response.append("    amount: ");
            response.append(tr.getAmount());
            response.append("   title: ");
            response.append(tr.getTitle());
            response.append("\r\n");
        }
        return ok(response);
    }
    @GetMapping("/sendTransactions")
    private ResponseEntity sendTransactions(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        User currentUser = users.findByEmail(principal.getName());
        StringBuffer response = new StringBuffer();
        List<Transaction> transactionList =  currentUser.getSentTransactions();
        for(Transaction tr: transactionList){
            response.append("to: ");
            response.append(tr.getReceiver().getEmail());
            response.append("    amount: ");
            response.append(tr.getAmount());
            response.append("   title: ");
            response.append(tr.getTitle());
            response.append("\r\n");
        }
        return ok(response);
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
            Transaction transaction = new Transaction();
            transaction.setAmount(sendData.getMoney());
            transaction.setReceiver(reciver);
            transaction.setSender(currentUser);
            transaction.setTitle(sendData.getTitle());
            transactionRepository.save(transaction);
            return ok("Transfer was send");

        }

        if(currentUser.getBalance() < sendData.getMoney()){
            return ok("You dont have enough money");
        }


        return ok(currentUser.getBalance());
    }
    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping("/changePassword")
    private ResponseEntity changePassword(HttpServletRequest request, @RequestBody ChangePasswordBody sendData){
        Principal principal = request.getUserPrincipal();
        User currentUser = users.findByEmail(principal.getName());

        if(!sendData.getNewPassword().equals(sendData.getRepeatNewPassword()))
            return ok("passwords aren't the same");
        if(!passwordEncoder.matches(sendData.getOldPassword().trim(), currentUser.getPassword()))
            return ok("You wrote bad old password");
        if(sendData.getNewPassword().trim().length()<5)
            return ok("password is too short!");
        currentUser.setPassword(passwordEncoder.encode(sendData.getNewPassword().trim()));
        users.save(currentUser);
        return ok("Password was changed");
    }


}
