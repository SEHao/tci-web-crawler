package Models;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) &&
                Objects.equals(year, item.year) &&
                Objects.equals(format, item.format);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, format);
    }
}
