package lamit.outerspacemanager.com.outerspacemanager.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.model.Report;

public class ReportsListItemAdapter extends RecyclerView.Adapter<ReportsListItemAdapter.ViewHolder> {

    private List<Report> reports;

    public void setObjects(List<Report> reportsList){

        if (this.reports != null) {
            this.reports.clear();
        }else{
            this.reports = new ArrayList<>();
        }

        this.reports.addAll(reportsList);
        this.notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.report_item_from_textview)   TextView fromTextView;
        @BindView(R.id.report_item_to_textview)     TextView toTextView;
        @BindView(R.id.report_item_date_textview)   TextView dateTextView;
        @BindView(R.id.report_item_time_textview)   TextView timeTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @NonNull
    @Override
    public ReportsListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_report, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsListItemAdapter.ViewHolder holder, int position) {
        Report report = this.reports.get(position);

        holder.fromTextView.setText(report.getFrom());
        holder.toTextView.setText(report.getTo());
        holder.dateTextView.setText(DateFormat.getDateInstance().format(report.getDate()));
        holder.timeTextView.setText(DateFormat.getTimeInstance().format(report.getDate()));
    }

    @Override
    public int getItemCount() {

        return this.reports == null ? 0 : this.reports.size();
    }
}
