package lamit.outerspacemanager.com.outerspacemanager.repository;


import android.content.Context;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import lamit.outerspacemanager.com.outerspacemanager.data.api.APIClient;
import lamit.outerspacemanager.com.outerspacemanager.data.room.SearchDao;

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

}
