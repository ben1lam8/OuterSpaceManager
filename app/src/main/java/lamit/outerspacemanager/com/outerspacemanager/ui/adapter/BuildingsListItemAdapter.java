package lamit.outerspacemanager.com.outerspacemanager.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lamit.outerspacemanager.com.outerspacemanager.model.Building;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.BuildingsActivity;
import lamit.outerspacemanager.com.outerspacemanager.R;
//import lamit.outerspacemanager.com.outerspacemanager.services.DownloadImageTask;

public class BuildingsListItemAdapter extends ArrayAdapter<Building>{

    private final Context context;
    private ArrayList<Building> buildingsList;

    public BuildingsListItemAdapter(BuildingsActivity buildingsActivity, ArrayList<Building> buildingsList) {
        super(buildingsActivity, -1, buildingsList);
        this.context = buildingsActivity;
        this.buildingsList = buildingsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //TODO: use ViewHolder

        // Élément courant
        Building currentBuilding = buildingsList.get(position);
        int nextLevel = currentBuilding.getLevel()+1;

        // Inflation du layout de la ligne
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_item_building, parent, false);

        // Récupération des widgets
        ImageView iconImageView = (ImageView) rowView.findViewById(R.id.building_item_icon_imageview);
        TextView levelTextView = (TextView) rowView.findViewById(R.id.building_item_level_textview);
        TextView timeTextView = (TextView) rowView.findViewById(R.id.building_item_time_textview);

        TextView nameTextView = (TextView) rowView.findViewById(R.id.building_item_name_textview);
        TextView effectTextView = (TextView) rowView.findViewById(R.id.building_item_effect_textview);
        TextView gasTextView = (TextView) rowView.findViewById(R.id.building_item_gas_textview);
        TextView mineralTextView = (TextView) rowView.findViewById(R.id.building_item_mineral_textview);

        // Initialisation des widgets
        //new DownloadImageTask(iconImageView).execute(currentBuilding.getImageUrl());

        nameTextView.setText(currentBuilding.getName());

        String levelText = context.getResources().getString(R.string.building_item_level, currentBuilding.getLevel());
        levelTextView.setText(levelText);

        int nextLevelTime = currentBuilding.getTimeToBuildByLevel()*nextLevel+currentBuilding.getTimeToBuildLevel0();
        String timeText = context.getResources().getString(R.string.building_item_time, nextLevelTime);
        timeTextView.setText(timeText);

        int nextLevelEffect = currentBuilding.getAmountOfEffectByLevel()*nextLevel+currentBuilding.getAmountOfEffectLevel0();
        String effectText = context.getResources().getString(R.string.building_item_effect, currentBuilding.getEffect(), nextLevelEffect);
        effectTextView.setText(effectText);

        int nextLevelGasCost = currentBuilding.getGasCostByLevel()*nextLevel+currentBuilding.getGasCostLevel0();
        String gasText = context.getResources().getString(R.string.building_item_gas, nextLevelGasCost);
        gasTextView.setText(gasText);

        int nextLevelMineralCost = currentBuilding.getMineralCostByLevel()*nextLevel+currentBuilding.getMineralCostLevel0();
        String mineralText = context.getResources().getString(R.string.building_item_mineral, nextLevelMineralCost);
        mineralTextView.setText(mineralText);

        return rowView;
    }
}
