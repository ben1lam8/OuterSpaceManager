package lamit.outerspacemanager.com.outerspacemanager.di;


import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lamit.outerspacemanager.com.outerspacemanager.datasource.api.APIClient;
import lamit.outerspacemanager.com.outerspacemanager.datasource.room.BuildingDao;
import lamit.outerspacemanager.com.outerspacemanager.datasource.room.RoomDatabase;
import lamit.outerspacemanager.com.outerspacemanager.datasource.room.SearchDao;
import lamit.outerspacemanager.com.outerspacemanager.datasource.room.ShipDao;
import lamit.outerspacemanager.com.outerspacemanager.datasource.room.UserDao;
import lamit.outerspacemanager.com.outerspacemanager.repository.BuildingRepository;
import lamit.outerspacemanager.com.outerspacemanager.repository.SearchRepository;
import lamit.outerspacemanager.com.outerspacemanager.repository.ShipRepository;
import lamit.outerspacemanager.com.outerspacemanager.repository.UserRepository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {
    // --- PROVIDE DATABASE FACTORIES ---

    @Provides
    @Singleton
    RoomDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                RoomDatabase.class, "OSMDatabase.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(RoomDatabase database) { return database.userDao(); }

    @Provides
    @Singleton
    BuildingDao provideBuildingDao(RoomDatabase database) { return database.buildingDao(); }

    @Provides
    @Singleton
    ShipDao provideShipDao(RoomDatabase database) { return database.shipDao(); }

    @Provides
    @Singleton
    SearchDao provideSearchDao(RoomDatabase database) { return database.searchDao(); }

    // --- REPOSITORY FACTORIES ---

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(APIClient apiClient, UserDao userDao, Executor executor) {
        return new UserRepository(apiClient, userDao, executor);
    }

    @Provides
    @Singleton
    BuildingRepository provideBuildingRepository(APIClient apiClient, BuildingDao buildingDao, Executor executor) {
        return new BuildingRepository(apiClient, buildingDao, executor);
    }

    @Provides
    @Singleton
    ShipRepository provideShipRepository(APIClient apiClient, ShipDao shipDao, Executor executor) {
        return new ShipRepository(apiClient, shipDao, executor);
    }

    @Provides
    @Singleton
    SearchRepository provideSearchRepository(APIClient apiClient, SearchDao searchDao, Executor executor) {
        return new SearchRepository(apiClient, searchDao, executor);
    }

    // --- PROVIDE NETWORK FACTORIES ---

    private static String BASE_URL = "https://outer-space-manager-staging.herokuapp.com";

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(
                        Date.class,
                        (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()))
                .create();
    }

    @Provides
    Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    APIClient provideAPIClient(Retrofit restAdapter) {
        return restAdapter.create(APIClient.class);
    }
}
