package bank.controller;

import static org.springframework.http.ResponseEntity.ok;


import java.util.HashMap;
import java.util.Map;

import bank.config.JwtTokenProvider;
import bank.models.User;
import bank.repository.UserRepository;
import bank.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @Autowired
    private CustomUserDetailsService userService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody data) throws AccessDeniedException {
        User user = users.findByEmail(data.getEmail());
        if(user.getAttempts()>=2)
            throw new AccessDeniedException("too much errors");
        try {

            String username = data.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.users.findByEmail(username).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            user.setAttempts(0);
            users.save(user);//todo walidacje aby to był poprawny adres e-mail na backendzie, hashowanie hasla, tytul przelewu, historia przelewow , https , zmiana hasla
            return ok(model);
        } catch (AuthenticationException e) {

            if(user!=null ){
                user.setAttempts(user.getAttempts()+1);
                users.save(user);
            }

            throw new BadCredentialsException("Invalid email/password supplied");


        }
    }

  
}
