package lamit.outerspacemanager.com.outerspacemanager.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import lamit.outerspacemanager.com.outerspacemanager.data.api.APIClient;
import lamit.outerspacemanager.com.outerspacemanager.data.api.SimpleAPIErrorResponse;
import lamit.outerspacemanager.com.outerspacemanager.model.Report;
import lamit.outerspacemanager.com.outerspacemanager.model.ReportsList;
import lamit.outerspacemanager.com.outerspacemanager.model.Search;
import lamit.outerspacemanager.com.outerspacemanager.model.SearchesList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ReportRepository {

    private final Context appContext;
    private final APIClient apiClient;
    private final Executor executor;

    private final MutableLiveData<List<Report>> reports;

    @Inject
    public ReportRepository(Context appContext, APIClient apiClient, Executor executor) {
        this.appContext = appContext;
        this.apiClient = apiClient;
        this.executor = executor;
        this.reports = new MutableLiveData<List<Report>>();
    }

    public LiveData<List<Report>> getReports(){
        return this.reports;
    }

    public void refreshReports(String token) {
        executor.execute(() -> {

            Timber.d("Fetching fresh reports data with token %s", token);
            apiClient.fetchReportsList(0, 10, token).enqueue(new Callback<ReportsList>() {

                @Override
                public void onResponse(Call<ReportsList> call, Response<ReportsList> response) {

                    if (response.isSuccessful()){
                        Timber.d("Reports successfully fetched !");

                        executor.execute(() -> {
                            ReportsList reportsList = response.body();
                            Timber.d("Reports %s", reportsList);

                            reports.postValue(reportsList.getReports());
                        });
                    }else{
                        try{
                            SimpleAPIErrorResponse error = new Gson().fromJson(response.errorBody().string(), SimpleAPIErrorResponse.class);

                            //String errorMessage = ctx.getResources().getString(R.string.toast_infirm_create_search_message, error.getMessage());
                            //Toast.makeText(app.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();

                        }catch(IOException e){
                            //Toast.makeText(app.getApplicationContext(), R.string.toast_infirm_create_default, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReportsList> call, Throwable t) {
                    Timber.d(t, "An error occurred while trying to refresh reports");
                }

            });
        });
    }

}
