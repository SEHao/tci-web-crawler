package Models;

public class Item {
    private String name;
    private Integer year;
    private String format;

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public String getFormat() {
        return format;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Item(String name, Integer year, String format) {
        this.name = name;
        this.year = year;
        this.format = format;
    }

    public Item() {
    }
}
