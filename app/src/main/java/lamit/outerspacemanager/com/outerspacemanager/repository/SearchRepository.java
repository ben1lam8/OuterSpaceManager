package lamit.outerspacemanager.com.outerspacemanager.repository;


import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.data.api.APIClient;
import lamit.outerspacemanager.com.outerspacemanager.data.api.SimpleAPIErrorResponse;
import lamit.outerspacemanager.com.outerspacemanager.data.api.SimpleAPIResponse;
import lamit.outerspacemanager.com.outerspacemanager.data.room.SearchDao;
import lamit.outerspacemanager.com.outerspacemanager.event.RepositoryMessageEvent;
import lamit.outerspacemanager.com.outerspacemanager.model.Search;
import lamit.outerspacemanager.com.outerspacemanager.model.SearchesList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SearchRepository {

    private final Context appContext;
    private final APIClient apiClient;
    private final SearchDao searchDao;
    private final Executor executor;

    @Inject
    public SearchRepository(Context appContext, APIClient apiClient, SearchDao searchDao, Executor executor) {
        this.appContext = appContext;
        this.apiClient = apiClient;
        this.searchDao = searchDao;
        this.executor = executor;
    }

    public LiveData<List<Search>> getSearches(){
        return this.searchDao.loadAll();
    }

    private void save(Search search){

        int updatedRowsCount = this.searchDao.update(
                search.getSearchId(),
                search.getName(),
                search.getLevel(),
                search.isBuilding(),
                search.getEffect(),
                search.getAmountOfEffectByLevel(),
                search.getAmountOfEffectLevel0(),
                search.getGasCostByLevel(),
                search.getGasCostLevel0(),
                search.getMineralCostByLevel(),
                search.getMineralCostLevel0(),
                search.getTimeToBuildByLevel(),
                search.getTimeToBuildLevel0()
        );

        if(updatedRowsCount == 0){
            this.searchDao.insert(search);

            Timber.d("New search %s inserted", search);
        }
    }

    public void refreshSearches(String token) {
        executor.execute(() -> {

            Timber.d("Fetching fresh searches data with token %s", token);
            apiClient.fetchSearchesList(token).enqueue(new Callback<SearchesList>() {

                @Override
                public void onResponse(Call<SearchesList> call, Response<SearchesList> response) {

                    if (response.isSuccessful()){
                        Timber.d("Searches successfully fetched !");

                        executor.execute(() -> {
                            SearchesList searchesList = response.body();
                            Timber.d("Searches %s", searchesList);

                            for(Search search : searchesList.getSearches()){
                                save(search);
                            }
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
                public void onFailure(Call<SearchesList> call, Throwable t) {
                    Timber.d(t, "An error occurred while trying to refresh searches");
                }

            });
        });
    }

    public void upgradeSearch(Search search, String token){
        executor.execute(() -> {

            Timber.d("Creating %s search with token %s",  search.getName(), token);
            apiClient.createSearch(search.getSearchId(), token).enqueue(new Callback<SimpleAPIResponse>() {

                @Override
                public void onResponse(Call<SimpleAPIResponse> call, Response<SimpleAPIResponse> response) {

                    if (response.isSuccessful()){
                        Timber.d("Search successfully created !");

                        executor.execute(() -> {
                            SimpleAPIResponse simpleAPIResponse = response.body();
                            Timber.d("SimpleAPIResponse %s", simpleAPIResponse);

                            if(simpleAPIResponse.getCode().equals("ok")){

                                Calendar now = Calendar.getInstance();

                                Date upgradeStart = now.getTime();

                                int delayInSeconds = search.getTimeToBuildLevel0()
                                        +search.getTimeToBuildByLevel()
                                        *search.getLevel();

                                now.add(Calendar.SECOND, delayInSeconds);
                                Date upgradeFinish = now.getTime();

                                searchDao.updateUpgrade(search.getSearchId(), upgradeStart, upgradeFinish);

                                EventBus
                                        .getDefault()
                                        .post(new RepositoryMessageEvent(
                                                appContext.getString(R.string.toast_confirm_upgrade_search),
                                                Toast.LENGTH_SHORT)
                                        )
                                ;
                            }else{
                                Timber.d("Cannot upgrade search. API Code : %s", simpleAPIResponse.getCode());
                                //Print Error
                            }
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
                public void onFailure(Call<SimpleAPIResponse> call, Throwable t) {
                    Timber.d(t, "An error occurred while trying to create search %s", search.getName());
                }
            });
        });
    }
}
