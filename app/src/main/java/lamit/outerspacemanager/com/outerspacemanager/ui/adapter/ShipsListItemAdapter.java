package lamit.outerspacemanager.com.outerspacemanager.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lamit.outerspacemanager.com.outerspacemanager.model.Ship;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.ShipyardActivity;
import lamit.outerspacemanager.com.outerspacemanager.R;

public class ShipsListItemAdapter extends ArrayAdapter<Ship>{

    private final Context context;
    private ArrayList<Ship> shipsList;

    public ShipsListItemAdapter(ShipyardActivity shipsActivity, ArrayList<Ship> shipsList) {
        super(shipsActivity, -1, shipsList);
        this.context = shipsActivity;
        this.shipsList = shipsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //TODO: use ViewHolder

        // Élément courant
        Ship currentShip = shipsList.get(position);

        // Inflation du layout de la ligne
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_item_ship, parent, false);

        // Récupération des widgets
        ImageView iconImageView = (ImageView) rowView.findViewById(R.id.ship_item_icon_imageview);
        TextView nameTextView = (TextView) rowView.findViewById(R.id.ship_item_name_textview);

        TextView lifeTextView = (TextView) rowView.findViewById(R.id.ship_item_life_textview);
        TextView shieldTextView = (TextView) rowView.findViewById(R.id.ship_item_shield_textview);
        TextView attackTextView = (TextView) rowView.findViewById(R.id.ship_item_attack_textview);
        TextView speedTextView = (TextView) rowView.findViewById(R.id.ship_item_speed_textview);

        TextView gasTextView = (TextView) rowView.findViewById(R.id.ship_item_gas_textview);
        TextView mineralTextView = (TextView) rowView.findViewById(R.id.ship_item_mineral_textview);
        TextView spatioportTextView = (TextView) rowView.findViewById(R.id.ship_item_spatioport_textview);

        // Initialisation des widgets
        //new DownloadImageTask(iconImageView).execute(currentShip.getImageUrl());

        nameTextView.setText(currentShip.getName());

        String lifeText = context.getResources().getString(R.string.ship_item_life, currentShip.getLife());
        lifeTextView.setText(lifeText);

        String shieldText = context.getResources().getString(R.string.ship_item_shield, currentShip.getShield());
        shieldTextView.setText(shieldText);

        String attackText = context.getResources().getString(R.string.ship_item_attack, currentShip.getMinAttack(), currentShip.getMaxAttack());
        attackTextView.setText(attackText);

        String speedText = context.getResources().getString(R.string.ship_item_speed, currentShip.getSpeed());
        speedTextView.setText(speedText);

        String gasText = context.getResources().getString(R.string.ship_item_gas, currentShip.getGasCost());
        gasTextView.setText(gasText);

        String mineralText = context.getResources().getString(R.string.ship_item_mineral, currentShip.getMineralCost());
        mineralTextView.setText(mineralText);

        //TODO: display next line only if needed

        String spatioportText = context.getResources().getString(R.string.ship_item_spatioport, currentShip.getSpatioportLevelNeeded());
        spatioportTextView.setText(spatioportText);

        return rowView;
    }
}
