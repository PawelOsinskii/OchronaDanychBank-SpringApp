package bank.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private int id;
    private String login;
    private String pass;

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }


    public int getId() {
        return id;
    }
}
