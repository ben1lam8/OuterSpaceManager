package lamit.outerspacemanager.com.outerspacemanager.model;

import java.util.Date;
import java.util.List;

public class Report {

    private List<Ship> attackerFleet;
    private FleetAfterBattle attackerFleetAfterBattle;
    private Date date;
    private String dateInv;
    private List<Ship> defenderFleet;
    private FleetAfterBattle defenderFleetAfterBattle;
    private String from;
    private String to;
    private String type;
    private int gasWon;
    private int mineralsWon;

    public List<Ship> getAttackerFleet() {
        return attackerFleet;
    }

    public Report setAttackerFleet(List<Ship> attackerFleet) {
        this.attackerFleet = attackerFleet;
        return this;
    }

    public FleetAfterBattle getAttackerFleetAfterBattle() {
        return attackerFleetAfterBattle;
    }

    public Report setAttackerFleetAfterBattle(FleetAfterBattle attackerFleetAfterBattle) {
        this.attackerFleetAfterBattle = attackerFleetAfterBattle;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Report setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getDateInv() {
        return dateInv;
    }

    public Report setDateInv(String dateInv) {
        this.dateInv = dateInv;
        return this;
    }

    public List<Ship> getDefenderFleet() {
        return defenderFleet;
    }

    public Report setDefenderFleet(List<Ship> defenderFleet) {
        this.defenderFleet = defenderFleet;
        return this;
    }

    public FleetAfterBattle getDefenderFleetAfterBattle() {
        return defenderFleetAfterBattle;
    }

    public Report setDefenderFleetAfterBattle(FleetAfterBattle defenderFleetAfterBattle) {
        this.defenderFleetAfterBattle = defenderFleetAfterBattle;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public Report setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public Report setTo(String to) {
        this.to = to;
        return this;
    }

    public String getType() {
        return type;
    }

    public Report setType(String type) {
        this.type = type;
        return this;
    }

    public int getGasWon() {
        return gasWon;
    }

    public Report setGasWon(int gasWon) {
        this.gasWon = gasWon;
        return this;
    }

    public int getMineralsWon() {
        return mineralsWon;
    }

    public Report setMineralsWon(int mineralsWon) {
        this.mineralsWon = mineralsWon;
        return this;
    }

    @Override
    public String toString(){

        return String.format(
                "Report {from: %s, to: %s, date: %s}",
                this.from,
                this.to,
                this.date
        );
    }
}
