package lamit.outerspacemanager.com.outerspacemanager.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.model.Search;

public class SearchesListItemAdapter extends ArrayAdapter<Search>{

    public SearchesListItemAdapter(Context context){
        super(context, 0);
    }

    public void setObjects(List<Search> searchesList){
        this.clear();
        this.addAll(searchesList);
    }

    static class ViewHolder {
        @BindView(R.id.search_item_icon_imageview)        ImageView iconImageView;
        @BindView(R.id.search_item_level_textview)        TextView levelTextView;
        @BindView(R.id.search_item_upgrade_progressbar)   ProgressBar upgradeProgressBar;
        @BindView(R.id.search_item_upgrade_textview)      TextView upgradeTextView;
        @BindView(R.id.search_item_name_textview)         TextView nameTextView;
        @BindView(R.id.search_item_effect_textview)       TextView effectTextView;
        @BindView(R.id.search_item_gas_textview)          TextView gasTextView;
        @BindView(R.id.search_item_minerals_textview)      TextView mineralTextView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_search, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Search currentSearch = getItem(position);

        int nextLevel = currentSearch.getLevel()+1;

        holder.nameTextView.setText(currentSearch.getName());

        String levelText = getContext().getString(R.string.search_item_level, currentSearch.getLevel());
        holder.levelTextView.setText(levelText);

        if(currentSearch.getUpgradeFinish() != null && currentSearch.getUpgradeFinish().after(new Date())){

            long totalTime = TimeUnit.MILLISECONDS.toSeconds(currentSearch.getUpgradeFinish().getTime() - currentSearch.getUpgradeStart().getTime());
            long remainingTime = TimeUnit.MILLISECONDS.toSeconds(currentSearch.getUpgradeFinish().getTime() - new Date().getTime());
            long elapsedTime = totalTime - remainingTime;

            int progress = (int) (elapsedTime*100/totalTime);

            holder.upgradeProgressBar.setProgress(progress);
            holder.upgradeProgressBar.setVisibility(View.VISIBLE);

            String timeText = getContext().getString(R.string.search_item_upgrade_remaining_time, remainingTime);
            holder.upgradeTextView.setText(timeText);
        }else{

            holder.upgradeProgressBar.setVisibility(View.INVISIBLE);

            int nextLevelTime = currentSearch.getTimeToBuildByLevel()*nextLevel+currentSearch.getTimeToBuildLevel0();
            String timeText = getContext().getString(R.string.search_item_upgrade_total_time, nextLevelTime);
            holder.upgradeTextView.setText(timeText);
        }

        int nextLevelEffect = currentSearch.getAmountOfEffectByLevel()*nextLevel+currentSearch.getAmountOfEffectLevel0();
        String effectText = getContext().getString(R.string.search_item_effect, currentSearch.getEffect(), nextLevelEffect);
        holder.effectTextView.setText(effectText);

        int nextLevelGasCost = currentSearch.getGasCostByLevel()*nextLevel+currentSearch.getGasCostLevel0();
        String gasText = getContext().getString(R.string.search_item_gas, nextLevelGasCost);
        holder.gasTextView.setText(gasText);

        int nextLevelMineralCost = currentSearch.getMineralCostByLevel()*nextLevel+currentSearch.getMineralCostLevel0();
        String mineralText = getContext().getString(R.string.search_item_mineral, nextLevelMineralCost);
        holder.mineralTextView.setText(mineralText);

        return convertView;
    }
}
