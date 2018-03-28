package lamit.outerspacemanager.com.outerspacemanager.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.google.gson.annotations.Expose;
import java.util.Date;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    @Expose
    private String username;

    @Expose
    private long points;

    @Expose
    private int mineralModifier;

    @Expose
    private double minerals;

    @Expose
    private int gasModifier;

    @Expose
    private double gas;

    private String token;

    private Date expires;

    private Date lastConnection;

    private Date lastRefresh;

    public User(@NonNull String username, long points, int mineralModifier, double minerals, int gasModifier, double gas, String token, Date expires, Date lastConnection, Date lastRefresh){
        this.username = username;
        this.points = points;
        this.mineralModifier = mineralModifier;
        this.minerals = minerals;
        this.gasModifier = gasModifier;
        this.gas = gas;
        this.token = token;
        this.expires = expires;
        this.lastConnection = lastConnection;
        this.lastRefresh = lastRefresh;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public User setUsername(@NonNull String username) {
        this.username = username;
        return this;
    }

    public long getPoints() {
        return points;
    }

    public User setPoints(long points) {
        this.points = points;
        return this;
    }

    public int getMineralModifier() {
        return mineralModifier;
    }

    public User setMineralModifier(int mineralModifier) {
        this.mineralModifier = mineralModifier;
        return this;
    }

    public double getMinerals() {
        return minerals;
    }

    public User setMinerals(double minerals) {
        this.minerals = minerals;
        return this;
    }

    public int getGasModifier() {
        return gasModifier;
    }

    public User setGasModifier(int gasModifier) {
        this.gasModifier = gasModifier;
        return this;
    }

    public double getGas() {
        return gas;
    }

    public User setGas(double gas) {
        this.gas = gas;
        return this;
    }

    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    public Date getExpires() {
        return expires;
    }

    public User setExpires(Date expires) {
        this.expires = expires;
        return this;
    }

    public Date getLastConnection() {
        return lastConnection;
    }

    public User setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
        return this;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public User setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
        return this;
    }

    @Override
    public String toString(){

        return String.format(
                "User {username : %s}",
                this.username
        );
    }
}
