package lamit.outerspacemanager.com.outerspacemanager.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.google.gson.annotations.Expose;
import java.util.Date;

@Entity
public class Building {

    @PrimaryKey
    @NonNull
    @Expose
    private int buildingId;

    @Expose
    private String name;

    @Expose
    private String imageUrl;

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

    public Building(@NonNull int buildingId, String name, String imageUrl, int level, boolean building, int amountOfEffectByLevel, int amountOfEffectLevel0, String effect, int gasCostByLevel, int gasCostLevel0, int mineralCostByLevel, int mineralCostLevel0, int timeToBuildByLevel, int timeToBuildLevel0, Date constructionStart, Date constructionFinish) {
        this.buildingId = buildingId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.level = level;
        this.building = building;
        this.amountOfEffectByLevel = amountOfEffectByLevel;
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
        this.effect = effect;
        this.gasCostByLevel = gasCostByLevel;
        this.gasCostLevel0 = gasCostLevel0;
        this.mineralCostByLevel = mineralCostByLevel;
        this.mineralCostLevel0 = mineralCostLevel0;
        this.timeToBuildByLevel = timeToBuildByLevel;
        this.timeToBuildLevel0 = timeToBuildLevel0;
        this.constructionStart = constructionStart;
        this.constructionFinish = constructionFinish;
    }

    public int getLevel() {
        return level;
    }

    public Building setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getAmountOfEffectByLevel() {
        return amountOfEffectByLevel;
    }

    public Building setAmountOfEffectByLevel(int amountOfEffectByLevel) {
        this.amountOfEffectByLevel = amountOfEffectByLevel;
        return this;
    }

    public int getAmountOfEffectLevel0() {
        return amountOfEffectLevel0;
    }

    public Building setAmountOfEffectLevel0(int amountOfEffectLevel0) {
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
        return this;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public Building setBuildingId(int buildingId) {
        this.buildingId = buildingId;
        return this;
    }

    public boolean isBuilding() {
        return building;
    }

    public Building setBuilding(boolean building) {
        this.building = building;
        return this;
    }

    public String getEffect() {
        return effect;
    }

    public Building setEffect(String effect) {
        this.effect = effect;
        return this;
    }

    public int getGasCostByLevel() {
        return gasCostByLevel;
    }

    public Building setGasCostByLevel(int gasCostByLevel) {
        this.gasCostByLevel = gasCostByLevel;
        return this;
    }

    public int getGasCostLevel0() {
        return gasCostLevel0;
    }

    public Building setGasCostLevel0(int gasCostLevel0) {
        this.gasCostLevel0 = gasCostLevel0;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Building setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public int getMineralCostByLevel() {
        return mineralCostByLevel;
    }

    public Building setMineralCostByLevel(int mineralCostByLevel) {
        this.mineralCostByLevel = mineralCostByLevel;
        return this;
    }

    public int getMineralCostLevel0() {
        return mineralCostLevel0;
    }

    public Building setMineralCostLevel0(int mineralCostLevel0) {
        this.mineralCostLevel0 = mineralCostLevel0;
        return this;
    }

    public String getName() {
        return name;
    }

    public Building setName(String name) {
        this.name = name;
        return this;
    }

    public int getTimeToBuildByLevel() {
        return timeToBuildByLevel;
    }

    public Building setTimeToBuildByLevel(int timeToBuildByLevel) {
        this.timeToBuildByLevel = timeToBuildByLevel;
        return this;
    }

    public int getTimeToBuildLevel0() {
        return timeToBuildLevel0;
    }

    public Building setTimeToBuildLevel0(int timeToBuildLevel0) {
        this.timeToBuildLevel0 = timeToBuildLevel0;
        return this;
    }

    public Date getConstructionStart() {
        return constructionStart;
    }

    public Building setConstructionStart(Date constructionStart) {
        this.constructionStart = constructionStart;
        return this;
    }

    public Date getConstructionFinish() {
        return constructionFinish;
    }

    public Building setConstructionFinish(Date constructionFinish) {
        this.constructionFinish = constructionFinish;
        return this;
    }

    @Override
    public String toString() {

        return String.format(
                "Building {id : %s, name : %s, level : %s}",
                this.buildingId,
                this.name,
                this.level
        );
    }
}
