package lamit.outerspacemanager.com.outerspacemanager.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.google.gson.annotations.Expose;
import java.util.Date;

@Entity
public class Search {

    @PrimaryKey
    @NonNull
    private int searchId;

    @Expose
    private String name;

    @Expose
    private int level;

    @Expose
    private boolean building;

    @Expose
    private String effect;

    @Expose
    private int amountOfEffectByLevel;

    @Expose
    private int amountOfEffectLevel0;

    @Expose
    private int gasCostByLevel;

    @Expose
    private int gasCostLevel0;

    @Expose
    private int mineralCostByLevel;

    @Expose
    private int mineralCostLevel0;

    @Expose
    private int timeToBuildByLevel;

    @Expose
    private int timeToBuildLevel0;

    private Date constructionStart;

    private Date constructionFinish;

    public Search(@NonNull int searchId, String name, int level, boolean building, String effect, int amountOfEffectByLevel, int amountOfEffectLevel0, int gasCostByLevel, int gasCostLevel0, int mineralCostByLevel, int mineralCostLevel0, int timeToBuildByLevel, int timeToBuildLevel0, Date constructionStart, Date constructionFinish) {
        this.searchId = searchId;
        this.name = name;
        this.level = level;
        this.building = building;
        this.effect = effect;
        this.amountOfEffectByLevel = amountOfEffectByLevel;
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
        this.gasCostByLevel = gasCostByLevel;
        this.gasCostLevel0 = gasCostLevel0;
        this.mineralCostByLevel = mineralCostByLevel;
        this.mineralCostLevel0 = mineralCostLevel0;
        this.timeToBuildByLevel = timeToBuildByLevel;
        this.timeToBuildLevel0 = timeToBuildLevel0;
        this.constructionStart = constructionStart;
        this.constructionFinish = constructionFinish;
    }

    @NonNull
    public int getSearchId() {
        return searchId;
    }

    public Search setSearchId(@NonNull int searchId) {
        this.searchId = searchId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Search setName(String name) {
        this.name = name;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public Search setLevel(int level) {
        this.level = level;
        return this;
    }

    public boolean isBuilding() {
        return building;
    }

    public Search setBuilding(boolean building) {
        this.building = building;
        return this;
    }

    public String getEffect() {
        return effect;
    }

    public Search setEffect(String effect) {
        this.effect = effect;
        return this;
    }

    public int getAmountOfEffectByLevel() {
        return amountOfEffectByLevel;
    }

    public Search setAmountOfEffectByLevel(int amountOfEffectByLevel) {
        this.amountOfEffectByLevel = amountOfEffectByLevel;
        return this;
    }

    public int getAmountOfEffectLevel0() {
        return amountOfEffectLevel0;
    }

    public Search setAmountOfEffectLevel0(int amountOfEffectLevel0) {
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
        return this;
    }

    public int getGasCostByLevel() {
        return gasCostByLevel;
    }

    public Search setGasCostByLevel(int gasCostByLevel) {
        this.gasCostByLevel = gasCostByLevel;
        return this;
    }

    public int getGasCostLevel0() {
        return gasCostLevel0;
    }

    public Search setGasCostLevel0(int gasCostLevel0) {
        this.gasCostLevel0 = gasCostLevel0;
        return this;
    }

    public int getMineralCostByLevel() {
        return mineralCostByLevel;
    }

    public Search setMineralCostByLevel(int mineralCostByLevel) {
        this.mineralCostByLevel = mineralCostByLevel;
        return this;
    }

    public int getMineralCostLevel0() {
        return mineralCostLevel0;
    }

    public Search setMineralCostLevel0(int mineralCostLevel0) {
        this.mineralCostLevel0 = mineralCostLevel0;
        return this;
    }

    public int getTimeToBuildByLevel() {
        return timeToBuildByLevel;
    }

    public Search setTimeToBuildByLevel(int timeToBuildByLevel) {
        this.timeToBuildByLevel = timeToBuildByLevel;
        return this;
    }

    public int getTimeToBuildLevel0() {
        return timeToBuildLevel0;
    }

    public Search setTimeToBuildLevel0(int timeToBuildLevel0) {
        this.timeToBuildLevel0 = timeToBuildLevel0;
        return this;
    }

    public Date getConstructionStart() {
        return constructionStart;
    }

    public Search setConstructionStart(Date constructionStart) {
        this.constructionStart = constructionStart;
        return this;
    }

    public Date getConstructionFinish() {
        return constructionFinish;
    }

    public Search setConstructionFinish(Date constructionFinish) {
        this.constructionFinish = constructionFinish;
        return this;
    }

    @Override
    public String toString(){

        return String.format(
                "Search {id : %s, name : %s, level : %s}",
                this.searchId,
                this.name,
                this.level
        );
    }
}
