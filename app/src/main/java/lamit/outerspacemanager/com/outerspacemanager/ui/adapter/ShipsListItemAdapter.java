package lamit.outerspacemanager.com.outerspacemanager.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lamit.outerspacemanager.com.outerspacemanager.model.Ship;
import lamit.outerspacemanager.com.outerspacemanager.R;

public class ShipsListItemAdapter extends ArrayAdapter<Ship>{

    private double userCurrentGas;
    private double userCurrentMinerals;
    private int currentAmount;
    private boolean maxMode;

    public ShipsListItemAdapter(Context context){
        super(context, 0);
    }

    public void setObjects(List<Ship> shipsList){
        this.clear();
        this.addAll(shipsList);
    }

    public void setCurrentAmount(int amount){
        this.currentAmount = amount;
        this.notifyDataSetChanged();
    }

    public void setUserCurrentGas(double userCurrentGas){
        this.userCurrentGas = userCurrentGas;
    }

    public void setUserCurrentMinerals(double userCurrentMinerals){
        this.userCurrentMinerals = userCurrentMinerals;
    }

    public void setMaxMode(boolean maxMode){
        this.maxMode = maxMode;
        this.notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.ship_item_icon_imageview)        ImageView iconImageView;
        @BindView(R.id.ship_item_name_textview)         TextView nameTextView;
        @BindView(R.id.ship_item_amount_textview)       TextView amountTextView;
        @BindView(R.id.ship_item_create_textview)       TextView createTextView;
        @BindView(R.id.ship_item_life_textview)         TextView lifeTextView;
        @BindView(R.id.ship_item_shield_textview)       TextView shieldTextView;
        @BindView(R.id.ship_item_attack_textview)       TextView attackTextView;
        @BindView(R.id.ship_item_speed_textview)        TextView speedTextView;
        @BindView(R.id.ship_item_gas_textview)          TextView gasTextView;
        @BindView(R.id.ship_item_minerals_textview)     TextView mineralsTextView;
        @BindView(R.id.ship_item_spatioport_textview)   TextView spatioportTextView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ShipsListItemAdapter.ViewHolder holder;
        if (convertView != null) {
            holder = (ShipsListItemAdapter.ViewHolder) convertView.getTag(R.id.ship_item_view_tag);
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_ship, parent, false);
            holder = new ShipsListItemAdapter.ViewHolder(convertView);
            convertView.setTag(R.id.ship_item_view_tag, holder);
        }

        Ship currentShip = getItem(position);

        switch(currentShip.getShipId()){
            case 0:
                holder.iconImageView.setImageResource(R.drawable.light_fighter);
                break;
            case 1:
                holder.iconImageView.setImageResource(R.drawable.heavy_fighter);
                break;
            case 2:
                holder.iconImageView.setImageResource(R.drawable.probe);
                break;
            case 3:
                holder.iconImageView.setImageResource(R.drawable.destroyer);
                break;
            case 4:
                holder.iconImageView.setImageResource(R.drawable.death_star);
                break;
        }

        holder.nameTextView.setText(currentShip.getName());

        double maxAmountByGas = this.userCurrentGas / currentShip.getGasCost();
        double maxAmountByMinerals = this.userCurrentMinerals / currentShip.getMineralCost();

        int maxAmount = (int) Math.min(maxAmountByGas, maxAmountByMinerals);

        int buildAmount = this.currentAmount > maxAmount ? maxAmount : this.currentAmount;
        buildAmount = this.maxMode ? maxAmount : buildAmount;
        convertView.setTag(R.id.ship_item_build_amount_tag, buildAmount);

        int pendingAmount = currentShip.getTotalAmount() - currentShip.getBuiltAmount();
        holder.amountTextView.setText(getContext().getString(R.string.ship_item_amount, currentShip.getBuiltAmount(), pendingAmount));

        holder.createTextView.setText(getContext().getString(R.string.ship_item_create, buildAmount, currentShip.getTimeToBuild()));

        holder.lifeTextView.setText(getContext().getString(R.string.ship_item_life, currentShip.getLife()));

        holder.shieldTextView.setText(getContext().getString(R.string.ship_item_shield, currentShip.getShield()));

        holder.attackTextView.setText(getContext().getString(R.string.ship_item_attack, currentShip.getMinAttack(), currentShip.getMaxAttack()));

        holder.speedTextView.setText(getContext().getString(R.string.ship_item_speed, currentShip.getSpeed()));

        holder.gasTextView.setText(getContext().getString(R.string.ship_item_gas, currentShip.getGasCost()));

        holder.mineralsTextView.setText(getContext().getString(R.string.ship_item_minerals, currentShip.getMineralCost()));

        holder.spatioportTextView.setText(getContext().getString(R.string.ship_item_spatioport, currentShip.getSpatioportLevelNeeded()));

        return convertView;
    }
}
