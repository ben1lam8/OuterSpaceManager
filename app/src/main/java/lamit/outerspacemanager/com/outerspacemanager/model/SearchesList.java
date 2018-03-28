package lamit.outerspacemanager.com.outerspacemanager.model;


import java.util.ArrayList;

public class SearchesList {

    private int size;

    private ArrayList<Search> searches;

    public int getSize() {
        return size;
    }

    public SearchesList setSize(int size) {
        this.size = size;
        return this;
    }

    public ArrayList<Search> getSearchs() {
        return searches;
    }

    public SearchesList setSearches(ArrayList<Search> searches) {
        this.searches = searches;
        return this;
    }

    @Override
    public String toString(){

        return String.format(
                "SearchList {size : %s, searches : %s",
                this.size,
                this.searches
        );
    }
}
