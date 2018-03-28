package lamit.outerspacemanager.com.outerspacemanager.model;

import android.support.annotation.NonNull;
import com.google.gson.annotations.Expose;

import java.util.Date;

public class Token {

    @NonNull
    @Expose
    private String token;

    @Expose
    private Date expires;

    public Token(@NonNull String token, Date expires){
        this.token = token;
        this.expires = expires;
    }

    @NonNull
    public String getToken() {
        return token;
    }

    public Token setToken(String token) {
        this.token = token;
        return this;
    }

    public Date getExpires() {
        return expires;
    }

    public Token setExpires(Date expires) {
        this.expires = expires;
        return this;
    }

    @Override
    public String toString(){

        return String.format(
                "Token {token : %s, expires : %s}",
                this.token,
                this.expires
        );
    }

}
