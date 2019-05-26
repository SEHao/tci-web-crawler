package Database.Models;

public class Action {

    public Integer Id;

    public String TimeElapsed;

    public Integer PagesExplored;

    public Integer UniquePagesFound;

    public Integer SearchDepth;

    public Integer getId() {
        return Id;
    }

    public String getTimeElapsed() {
        return TimeElapsed;
    }

    public Integer getPagesExplored() {
        return PagesExplored;
    }

    public Integer getUniquePagesFound() {
        return UniquePagesFound;
    }

    public Integer getSearchDepth() {
        return SearchDepth;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setTimeElapsed(String timeElapsed) {
        TimeElapsed = timeElapsed;
    }

    public void setPagesExplored(Integer pagesExplored) {
        PagesExplored = pagesExplored;
    }

    public void setUniquePagesFound(Integer uniquePagesFound) {
        UniquePagesFound = uniquePagesFound;
    }

    public void setSearchDepth(Integer searchDepth) {
        SearchDepth = searchDepth;
    }

    public Action(Integer id, String timeElapsed, Integer pagesExplored, Integer uniquePagesFound, Integer searchDepth) {
        Id = id;
        TimeElapsed = timeElapsed;
        PagesExplored = pagesExplored;
        UniquePagesFound = uniquePagesFound;
        SearchDepth = searchDepth;
    }
}
