package lamit.outerspacemanager.com.outerspacemanager.repository;


import android.arch.lifecycle.LiveData;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import lamit.outerspacemanager.com.outerspacemanager.data.api.APIClient;
import lamit.outerspacemanager.com.outerspacemanager.data.api.SimpleAPIErrorResponse;
import lamit.outerspacemanager.com.outerspacemanager.data.api.SimpleAPIResponse;
import lamit.outerspacemanager.com.outerspacemanager.data.room.BuildingDao;
import lamit.outerspacemanager.com.outerspacemanager.model.Building;
import lamit.outerspacemanager.com.outerspacemanager.model.BuildingsList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class BuildingRepository {

    private final APIClient apiClient;
    private final BuildingDao buildingDao;
    private final Executor executor;

    @Inject
    public BuildingRepository(APIClient apiClient, BuildingDao buildingDao, Executor executor) {
        this.apiClient = apiClient;
        this.buildingDao = buildingDao;
        this.executor = executor;
    }

    public LiveData<List<Building>> getBuildings(String token) {
        refreshBuildings(token);
        return this.buildingDao.loadAll();
    }

    public void refreshBuildings(String token){
        executor.execute(() -> {

            Timber.d("Fetching fresh buildings data with token %s", token);
            apiClient.fetchBuildingsList(token).enqueue(new Callback<BuildingsList>() {

                @Override
                public void onResponse(Call<BuildingsList> call, Response<BuildingsList> response) {

                    if (response.isSuccessful()){
                        Timber.d("Buildings successfully fetched !");

                        executor.execute(() -> {
                            BuildingsList buildingsList = response.body();
                            Timber.d("Buildings %s", buildingsList);

                            for(Building building : buildingsList.getBuildings()){
                                buildingDao.save(building);
                            }
                        });
                    }else{
                        try{
                            SimpleAPIErrorResponse error = new Gson().fromJson(response.errorBody().string(), SimpleAPIErrorResponse.class);

                            //String errorMessage = ctx.getResources().getString(R.string.toast_infirm_create_building_message, error.getMessage());
                            //Toast.makeText(app.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();

                        }catch(IOException e){
                            //Toast.makeText(app.getApplicationContext(), R.string.toast_infirm_create_default, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BuildingsList> call, Throwable t) {
                    Timber.d(t, "An error occurred while trying to refresh buildings");
                }

            });
        });
    }

    public void createBuilding(Building building, String token){
        executor.execute(() -> {

            Timber.d("Creating fresh %s building with token %s", building.getName(), token);
            apiClient.createBuilding(building.getBuildingId(), token).enqueue(new Callback<SimpleAPIResponse>() {

                @Override
                public void onResponse(Call<SimpleAPIResponse> call, Response<SimpleAPIResponse> response) {

                    if (response.isSuccessful()){
                        Timber.d("Buildings successfully fetched !");

                        executor.execute(() -> {
                            SimpleAPIResponse simpleAPIResponse = response.body();
                            Timber.d("SimpleAPIResponse %s", simpleAPIResponse);

                            if(simpleAPIResponse.getCode().equals("ok")){

                                Calendar now = Calendar.getInstance();
                                building.setConstructionStart(now.getTime());

                                int delayInSeconds = building.getTimeToBuildLevel0()
                                        +building.getTimeToBuildByLevel()
                                        *building.getLevel();

                                now.add(Calendar.SECOND, delayInSeconds);
                                Date contructionFinish = now.getTime();

                                building.setConstructionFinish(contructionFinish);

                                buildingDao.save(building);
                            }else{
                                Timber.d("Cannot create building. API Code : %s", simpleAPIResponse.getCode());
                                //Print Error
                            }
                        });
                    }else{
                        try{
                            SimpleAPIErrorResponse error = new Gson().fromJson(response.errorBody().string(), SimpleAPIErrorResponse.class);

                            //String errorMessage = ctx.getResources().getString(R.string.toast_infirm_create_building_message, error.getMessage());
                            //Toast.makeText(app.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();

                        }catch(IOException e){
                            //Toast.makeText(app.getApplicationContext(), R.string.toast_infirm_create_default, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SimpleAPIResponse> call, Throwable t) {
                    Timber.d(t, "An error occurred while trying to create building %s", building.getName());
                }

            });
        });
    }
}
