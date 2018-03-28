package lamit.outerspacemanager.com.outerspacemanager.model;

import java.io.Serializable;


public class Credentials implements Serializable{

    /**
     * LAST CREDENTIALS
     * login    : ben1
     * passwd   : ben1
     * email    : ben1@ben1.fr
     */

    private String email;
    private String username;
    private String password;

    public Credentials() {}

    public String getEmail() {
        return email;
    }

    public Credentials setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Credentials setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Credentials setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString(){

        return String.format(
                "Credentials {username : %s, password : %s, email : %s}",
                this.username,
                this.password.replaceAll("(?<=.).(?=.)", "*"),
                this.email
        );
    }
}
