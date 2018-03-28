package lamit.outerspacemanager.com.outerspacemanager.repository;


import java.util.concurrent.Executor;

import javax.inject.Inject;

import lamit.outerspacemanager.com.outerspacemanager.datasource.api.APIClient;
import lamit.outerspacemanager.com.outerspacemanager.datasource.room.SearchDao;

public class SearchRepository {

    private final APIClient apiClient;
    private final SearchDao searchDao;
    private final Executor executor;

    @Inject
    public SearchRepository(APIClient apiClient, SearchDao searchDao, Executor executor) {
        this.apiClient = apiClient;
        this.searchDao = searchDao;
        this.executor = executor;
    }

}
