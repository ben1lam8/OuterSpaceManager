package lamit.outerspacemanager.com.outerspacemanager.repository;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import lamit.outerspacemanager.com.outerspacemanager.data.api.APIClient;
import lamit.outerspacemanager.com.outerspacemanager.data.api.SimpleAPIErrorResponse;
import lamit.outerspacemanager.com.outerspacemanager.data.api.SimpleAPIResponse;
import lamit.outerspacemanager.com.outerspacemanager.data.room.ShipDao;
import lamit.outerspacemanager.com.outerspacemanager.model.Ship;
import lamit.outerspacemanager.com.outerspacemanager.model.ShipsList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class ShipRepository {

    private final Context appContext;
    private final APIClient apiClient;
    private final ShipDao shipDao;
    private final Executor executor;

    @Inject
    public ShipRepository(Context appContext, APIClient apiClient, ShipDao shipDao, Executor executor) {
        this.appContext = appContext;
        this.apiClient = apiClient;
        this.shipDao = shipDao;
        this.executor = executor;
    }

    public LiveData<List<Ship>> getShips(){
        return this.shipDao.loadAll();
    }

    private void save(Ship ship){

        int updatedRowsCount = this.shipDao.update(
                ship.getShipId(),
                ship.getName(),
                ship.getLife(),
                ship.getShield(),
                ship.getMinAttack(),
                ship.getMaxAttack(),
                ship.getSpeed(),
                ship.getMineralCost(),
                ship.getGasCost(),
                ship.getSpatioportLevelNeeded(),
                ship.getTimeToBuild()
        );

        if(updatedRowsCount == 0){
            this.shipDao.insert(ship);

            Timber.d("New ship %s inserted", ship);
        }
    }

    public void refreshShips(String token) {
        executor.execute(() -> {

            Timber.d("Fetching fresh ships data with token %s", token);
            apiClient.fetchShipsList(token).enqueue(new Callback<ShipsList>() {

                @Override
                public void onResponse(Call<ShipsList> call, Response<ShipsList> response) {

                    if (response.isSuccessful()){
                        Timber.d("Ships successfully fetched !");

                        executor.execute(() -> {
                            ShipsList shipsList = response.body();
                            Timber.d("Ships %s", shipsList);

                            for(Ship ship : shipsList.getShips()){
                                save(ship);
                            }
                        });
                    }else{
                        try{
                            SimpleAPIErrorResponse error = new Gson().fromJson(response.errorBody().string(), SimpleAPIErrorResponse.class);

                            //String errorMessage = ctx.getResources().getString(R.string.toast_infirm_create_ship_message, error.getMessage());
                            //Toast.makeText(app.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();

                        }catch(IOException e){
                            //Toast.makeText(app.getApplicationContext(), R.string.toast_infirm_create_default, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ShipsList> call, Throwable t) {
                    Timber.d(t, "An error occurred while trying to refresh ships");
                }

            });
        });
    }

    public void refreshFleet(String token) {
        executor.execute(() -> {

            Timber.d("Fetching fresh fleet data with token %s", token);
            apiClient.fetchFleet(token).enqueue(new Callback<ShipsList>() {

                @Override
                public void onResponse(Call<ShipsList> call, Response<ShipsList> response) {

                    if (response.isSuccessful()){
                        Timber.d("Fleet successfully fetched !");

                        executor.execute(() -> {
                            ShipsList shipsList = response.body();
                            Timber.d("Ships %s", shipsList);

                            for(Ship ship : shipsList.getShips()){
                                shipDao.updateBuiltAmount(ship.getShipId(), ship.getBuiltAmount());
                            }
                        });
                    }else{
                        try{
                            SimpleAPIErrorResponse error = new Gson().fromJson(response.errorBody().string(), SimpleAPIErrorResponse.class);

                            //String errorMessage = ctx.getResources().getString(R.string.toast_infirm_create_ship_message, error.getMessage());
                            //Toast.makeText(app.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();

                        }catch(IOException e){
                            //Toast.makeText(app.getApplicationContext(), R.string.toast_infirm_create_default, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ShipsList> call, Throwable t) {
                    Timber.d(t, "An error occurred while trying to refresh ships");
                }

            });
        });
    }

    public void createShip(Ship ship, int amount, String token){

        HashMap<String, String> amountMap = new HashMap<>();
        amountMap.put("amount", String.valueOf(amount));

        executor.execute(() -> {

            Timber.d("Creating %s %s ship with token %s", amount, ship.getName(), token);
            apiClient.createShip(ship.getShipId(), amountMap, token).enqueue(new Callback<SimpleAPIResponse>() {

                @Override
                public void onResponse(Call<SimpleAPIResponse> call, Response<SimpleAPIResponse> response) {

                    if (response.isSuccessful()){
                        Timber.d("Ships successfully created !");

                        executor.execute(() -> {
                            SimpleAPIResponse simpleAPIResponse = response.body();
                            Timber.d("SimpleAPIResponse %s", simpleAPIResponse);

                            if(simpleAPIResponse.getCode().equals("ok")){

                                shipDao.updateTotalAmount(ship.getShipId(), ship.getTotalAmount()+1);
                            }else{
                                Timber.d("Cannot create ship. API Code : %s", simpleAPIResponse.getCode());
                                //Print Error
                            }
                        });
                    }else{
                        try{
                            SimpleAPIErrorResponse error = new Gson().fromJson(response.errorBody().string(), SimpleAPIErrorResponse.class);

                            //String errorMessage = ctx.getResources().getString(R.string.toast_infirm_create_ship_message, error.getMessage());
                            //Toast.makeText(app.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();

                        }catch(IOException e){
                            //Toast.makeText(app.getApplicationContext(), R.string.toast_infirm_create_default, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SimpleAPIResponse> call, Throwable t) {
                    Timber.d(t, "An error occurred while trying to create ship %s", ship.getName());
                }
            });
        });
    }
}
