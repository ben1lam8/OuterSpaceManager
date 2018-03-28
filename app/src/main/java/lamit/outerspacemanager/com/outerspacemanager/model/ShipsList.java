package lamit.outerspacemanager.com.outerspacemanager.model;

import java.util.ArrayList;


public class ShipsList {

    private double currentUserMinerals;

    private double currentUserGas;

    private int size;

    private ArrayList<Ship> ships;

    public double getCurrentUserMinerals() {
        return currentUserMinerals;
    }

    public ShipsList setCurrentUserMinerals(double currentUserMinerals) {
        this.currentUserMinerals = currentUserMinerals;
        return this;
    }

    public double getCurrentUserGas() {
        return currentUserGas;
    }

    public ShipsList setCurrentUserGas(double currentUserGas) {
        this.currentUserGas = currentUserGas;
        return this;
    }

    public int getSize() {
        return size;
    }

    public ShipsList setSize(int size) {
        this.size = size;
        return this;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public ShipsList setShips(ArrayList<Ship> ships) {
        this.ships = ships;
        return this;
    }

    @Override
    public String toString(){

        return String.format(
                "ShipList {size : %s, ships : %s}",
                this.size,
                this.ships
        );
    }
}
