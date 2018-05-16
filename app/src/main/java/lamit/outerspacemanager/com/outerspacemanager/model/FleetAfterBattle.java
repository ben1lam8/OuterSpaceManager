package lamit.outerspacemanager.com.outerspacemanager.model;

import java.util.List;

public class FleetAfterBattle {

    private int capacity;
    private List<Ship> fleet;
    private int survivingShips;

    public int getCapacity() {
        return capacity;
    }

    public FleetAfterBattle setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public List<Ship> getFleet() {
        return fleet;
    }

    public FleetAfterBattle setFleet(List<Ship> fleet) {
        this.fleet = fleet;
        return this;
    }

    public int getSurvivingShips() {
        return survivingShips;
    }

    public FleetAfterBattle setSurvivingShips(int survivingShips) {
        this.survivingShips = survivingShips;
        return this;
    }
}
