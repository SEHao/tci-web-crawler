package Models;

public class Item {

    public String Name;

    public Integer Year;

    public String Format;

    public String getName() {
        return Name;
    }

    public Integer getYear() {
        return Year;
    }

    public String getFormat() {
        return Format;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public Item(){

    }

    public Item(String name, Integer year, String format) {
        Name = name;
        Year = year;
        Format = format;
    }
}
