package lamit.outerspacemanager.com.outerspacemanager.model;


import java.util.ArrayList;

public class ReportsList {

    ArrayList<Report> reports;

    public ArrayList<Report> getReports() {
        return reports;
    }

    public ReportsList setReports(ArrayList<Report> reports) {
        this.reports = reports;
        return this;
    }

    @Override
    public String toString(){

        return String.format(
                "ReportList {reports : %s}",
                this.reports
        );
    }
}
