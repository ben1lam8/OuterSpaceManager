package lamit.outerspacemanager.com.outerspacemanager.data.api;

import java.util.HashMap;

import lamit.outerspacemanager.com.outerspacemanager.model.BuildingsList;
import lamit.outerspacemanager.com.outerspacemanager.model.SearchesList;
import lamit.outerspacemanager.com.outerspacemanager.model.ShipsList;
import lamit.outerspacemanager.com.outerspacemanager.model.Token;
import lamit.outerspacemanager.com.outerspacemanager.model.Credentials;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface APIClient {

    // USER

    @POST("/api/v1/auth/login")
    Call<Token> fetchToken(@Body Credentials credentials);

    @POST("/api/v1/auth/create")
    Call<Token> createUser(@Body Credentials credentials);

    @GET("/api/v1/users/get")
    Call<User> fetchUser(@Header("x-access-token") String token);

    @GET("/api/v1/users/{from}/{limit}")
    Call<User> fetchPlayers(@Path("from") int from, @Path("limit") int limit, @Header("x-access-token") String token);

    @GET("/api/v1/reports/{from}/{limit}")
    Call<User> fetchReports(@Path("from") int from, @Path("limit") int limit, @Header("x-access-token") String token);

    // BUILDING

    @GET("/api/v1/buildings/list")
    Call<BuildingsList> fetchBuildingsList(@Header("x-access-token") String token);

    @POST("/api/v1/buildings/create/{buildingId}")
    Call<SimpleAPIResponse> upgradeBuilding(@Path("buildingId") int buildingId, @Header("x-access-token") String token);

    // SHIP

    @GET("/api/v1/ships")
    Call<ShipsList> fetchShipsList(@Header("x-access-token") String token);

    @POST("/api/v1/ships/create/{shipId}")
    Call<SimpleAPIResponse> createShip(@Path("shipId") int shipId, @Body HashMap<String, String> bodyKeysValuesPairs, @Header("x-access-token") String token);

    @GET("/api/v1/fleet/list")
    Call<ShipsList> fetchFleet(@Header("x-access-token") String token);

    @POST("/api/v1/fleet/attack/{userName}")
    Call<AttackAPIResponse> attack(@Path("userName") String username); // To be completed

    // SEARCH

    @GET("/api/v1/searches/list")
    Call<SearchesList> fetchSearchesList(@Header("x-access-token") String token);

    @POST("/api/v1/searches/create/{searchId}")
    Call<SimpleAPIResponse> createSearch(@Path("searchId") int searchId, @Header("x-access-token") String token);

}
