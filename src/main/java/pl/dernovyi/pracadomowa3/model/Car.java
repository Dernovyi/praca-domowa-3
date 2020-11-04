package pl.dernovyi.pracadomowa3.model;



public class Car{

    private Long id;
    private String  mark;
    private String  model;
    private String color;
    private Integer year;

    public Car() {
    }

    public Car(Long car_id, String mark, String model, String color, Integer year) {
        this.id = car_id;
        this.mark = mark;
        this.model = model;
        this.color = color;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Car{");
        sb.append("id=").append(id);
        sb.append(", mark='").append(mark).append('\'');
        sb.append(", model='").append(model).append('\'');
        sb.append(", color='").append(color).append('\'');
        sb.append(", year='").append(year).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
