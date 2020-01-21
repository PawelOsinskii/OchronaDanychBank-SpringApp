package bank.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Email
    private String email; //moze byc tylko email hibernate annotacja


    private String password;
    private String fullname;
    private boolean enabled;
    private Long balance;
    private int attempts;

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Role> roles;

    @OneToMany(targetEntity = Transaction.class, mappedBy = "sender",fetch = FetchType.EAGER, orphanRemoval = true, cascade = {CascadeType.ALL})
    private List<Transaction> sentTransactions;

    @OneToMany(targetEntity = Transaction.class, mappedBy = "receiver", fetch = FetchType.EAGER, orphanRemoval = true, cascade = {CascadeType.ALL})
    private List<Transaction> receivedTransactions;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }



    public List<Transaction> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(List<Transaction> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    public List<Transaction> getReceivedTransactions() {
        return receivedTransactions;
    }

    public void setReceivedTransactions(List<Transaction> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }
}
