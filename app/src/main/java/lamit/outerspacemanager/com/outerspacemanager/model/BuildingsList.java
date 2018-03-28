package lamit.outerspacemanager.com.outerspacemanager.model;

import java.util.ArrayList;

public class BuildingsList {

    private int size;

    private ArrayList<Building> buildings;

    public int getSize() {
        return size;
    }

    public BuildingsList setSize(int size) {
        this.size = size;
        return this;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public BuildingsList setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
        return this;
    }

    @Override
    public String toString(){

        return String.format(
                "BuildingList {size : %s, buildings : %s",
                this.size,
                this.buildings
        );
    }
}
