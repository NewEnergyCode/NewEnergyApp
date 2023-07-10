package app.model;

public class Direction {

    int id;
    int ageCategory;
    String title;
    String img;
    String color;
    String age;
    String logoDirection;

    String text;

    public Direction(int id, String title, String img, String color, String age, String logoDirection, String text, int ageCategory) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.color = color;
        this.age = age;
        this.logoDirection = logoDirection;
        this.text = text;
        this.ageCategory = ageCategory;
    }

    public int getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(int ageCategory) {
        this.ageCategory = ageCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLogoDirection() {
        return logoDirection;
    }

    public void setLogoDirection(String logoDirection) {
        this.logoDirection = logoDirection;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
