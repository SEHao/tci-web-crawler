package Database.Models;

public class Action {

    public Integer Id;

    public long TimeElapsed;

    public Integer PagesExplored;

    public Integer UniquePagesFound;

    public Integer SearchDepth;

    public Integer getId() {
        return Id;
    }

    public long getTimeElapsed() {
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

    public void setTimeElapsed(long timeElapsed) {
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

    public Action(Integer id, long timeElapsed, Integer pagesExplored, Integer uniquePagesFound, Integer searchDepth) {
        Id = id;
        TimeElapsed = timeElapsed;
        PagesExplored = pagesExplored;
        UniquePagesFound = uniquePagesFound;
        SearchDepth = searchDepth;
    }
}
