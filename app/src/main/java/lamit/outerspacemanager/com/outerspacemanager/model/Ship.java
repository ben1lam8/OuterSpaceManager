package lamit.outerspacemanager.com.outerspacemanager.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Ship {

    @PrimaryKey
    @NonNull
    @Expose
    private int shipId;

    @Expose
    private String name;

    @Expose
    @SerializedName("amount")
    private int builtAmount;

    private int totalAmount;

    @Expose
    private int life;

    @Expose
    private int shield;

    @Expose
    private int minAttack;

    @Expose
    private int maxAttack;

    @Expose
    private int speed;

    @Expose
    private int mineralCost;

    @Expose
    private int gasCost;

    @Expose
    private int spatioportLevelNeeded;

    @Expose
    private int timeToBuild;

    public Ship(@NonNull int shipId, String name, int builtAmount, int totalAmount, int life, int shield, int minAttack, int maxAttack, int speed, int mineralCost, int gasCost, int spatioportLevelNeeded, int timeToBuild) {
        this.shipId = shipId;
        this.name = name;
        this.builtAmount = builtAmount;
        this.totalAmount = totalAmount;
        this.life = life;
        this.shield = shield;
        this.minAttack = minAttack;
        this.maxAttack = maxAttack;
        this.speed = speed;
        this.mineralCost = mineralCost;
        this.gasCost = gasCost;
        this.spatioportLevelNeeded = spatioportLevelNeeded;
        this.timeToBuild = timeToBuild;
    }

    public int getShipId() {
        return shipId;
    }

    public Ship setShipId(int shipId) {
        this.shipId = shipId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Ship setName(String name) {
        this.name = name;
        return this;
    }

    public int getBuiltAmount() {
        return builtAmount;
    }

    public Ship setBuiltAmount(int builtAmount) {
        this.builtAmount = builtAmount;
        return this;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public Ship setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public int getLife() {
        return life;
    }

    public Ship setLife(int life) {
        this.life = life;
        return this;
    }

    public int getShield() {
        return shield;
    }

    public Ship setShield(int shield) {
        this.shield = shield;
        return this;
    }

    public int getMinAttack() {
        return minAttack;
    }

    public Ship setMinAttack(int minAttack) {
        this.minAttack = minAttack;
        return this;
    }

    public int getMaxAttack() {
        return maxAttack;
    }

    public Ship setMaxAttack(int maxAttack) {
        this.maxAttack = maxAttack;
        return this;
    }

    public int getSpeed() {
        return speed;
    }

    public Ship setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public int getMineralCost() {
        return mineralCost;
    }

    public Ship setMineralCost(int mineralCost) {
        this.mineralCost = mineralCost;
        return this;
    }

    public int getGasCost() {
        return gasCost;
    }

    public Ship setGasCost(int gasCost) {
        this.gasCost = gasCost;
        return this;
    }

    public int getSpatioportLevelNeeded() {
        return spatioportLevelNeeded;
    }

    public Ship setSpatioportLevelNeeded(int spatioportLevelNeeded) {
        this.spatioportLevelNeeded = spatioportLevelNeeded;
        return this;
    }

    public int getTimeToBuild() {
        return timeToBuild;
    }

    public Ship setTimeToBuild(int timeToBuild) {
        this.timeToBuild = timeToBuild;
        return this;
    }

    @Override
    public String toString() {

        return String.format(
                "Ship {id : %s, name : %s, builtAmount: %s}",
                this.shipId,
                this.name,
                this.builtAmount
        );
    }
}
