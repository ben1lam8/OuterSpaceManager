package lamit.outerspacemanager.com.outerspacemanager.repository;


import android.arch.lifecycle.LiveData;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import javax.inject.Singleton;
import lamit.outerspacemanager.com.outerspacemanager.datasource.api.SimpleAPIErrorResponse;
import lamit.outerspacemanager.com.outerspacemanager.datasource.room.UserDao;
import lamit.outerspacemanager.com.outerspacemanager.model.Credentials;
import lamit.outerspacemanager.com.outerspacemanager.model.Token;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.datasource.api.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public class UserRepository {

    private static int FRESHNESS_TIMEOUT_IN_SECONDS = 30;

    private final APIClient apiClient;
    private final UserDao userDao;
    private final Executor executor;

    @Inject
    public UserRepository(APIClient apiClient, UserDao userDao, Executor executor) {
        this.apiClient = apiClient;
        this.userDao = userDao;
        this.executor = executor;
    }

    public LiveData<User> getLastConnectedFreshUser() {
        return userDao.loadLastConnectedFreshUser(new Date());
    }

    public LiveData<User> getByUsername(String username){
        return userDao.loadByUsername(username);
    }

    public void createUser(Credentials credentials){
        executor.execute(() -> {

            Timber.d("Fetching a token for new user with credentials %s", credentials);
            apiClient.createUser(credentials).enqueue(new Callback<Token>() {

                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {

                    if (response.isSuccessful()){
                        Timber.d("Token successfully fetched !");

                        executor.execute(() -> {
                            Token token = response.body();
                            Timber.d("Token %s", token);

                            User newUser = new User(
                                    credentials.getUsername(),
                                    0,
                                    0,
                                    0.0,
                                    0,
                                    0.0,
                                    token.getToken(),
                                    token.getExpires(),
                                    new Date(),
                                    new Date()
                            );

                            userDao.save(newUser);
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
                public void onFailure(Call<Token> call, Throwable t) {
                    Timber.d(t, "An error occurred while trying to create a new user");
                }
            });
        });
    }

    public void fetchUserToken(User user, Credentials credentials) {
        executor.execute(() -> {

            Timber.d("Fetching a token for user with credentials %s", credentials);
            apiClient.fetchToken(credentials).enqueue(new Callback<Token>() {

                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {

                    if (response.isSuccessful()){
                        Timber.d("Token successfully fetched !");

                        executor.execute(() -> {
                            Token token = response.body();
                            Timber.d("Token %s", token);

                            if(user != null){
                                user.setToken(token.getToken());
                                user.setExpires(token.getExpires());
                                user.setLastRefresh(new Date());
                                user.setLastConnection(new Date());

                                userDao.save(user);
                            }else{
                                User newUser = new User(
                                        credentials.getUsername(),
                                        0,
                                        0,
                                        0.0,
                                        0,
                                        0.0,
                                        token.getToken(),
                                        token.getExpires(),
                                        new Date(),
                                        new Date()
                                );

                                userDao.save(newUser);
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
                public void onFailure(Call<Token> call, Throwable t) {
                    Timber.d(t, "An error occurred while trying to fetch a token");
                }
            });
        });
    }

    public void fetchUser(User user) {
        executor.execute(() -> {

            Timber.d("Fetching stats for user %s", user.getUsername());
            apiClient.fetchUser(user.getToken()).enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if (response.isSuccessful()){
                        Timber.d("User successfully fetched !");

                        executor.execute(() -> {
                            User freshUser = response.body();
                            Timber.d("Fresh user : %s", user);

                            user.setPoints(freshUser.getPoints());
                            user.setMineralModifier(freshUser.getMineralModifier());
                            user.setMinerals(freshUser.getMinerals());
                            user.setGasModifier(freshUser.getGasModifier());
                            user.setGas(freshUser.getGas());

                            user.setLastRefresh(new Date());

                            userDao.save(user);
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
                public void onFailure(Call<User> call, Throwable t) {
                    Timber.d(t, "An error occurred while trying to fetch fresh user stats");
                }
            });
        });
    }

    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.SECOND, -FRESHNESS_TIMEOUT_IN_SECONDS);
        return cal.getTime();
    }
}
