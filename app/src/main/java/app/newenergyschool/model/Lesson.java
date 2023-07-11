package app.newenergyschool.model;

public class Lesson {
    private String telephoneNumber;
    private String coursesName;
    private String time;
    private String dayOfWeek;

    public Lesson(String telephoneNumber, String coursesName, String time, String dayOfWeek) {
        this.telephoneNumber = telephoneNumber;
        this.coursesName = coursesName;
        this.time = time;
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "telephoneNumber='" + telephoneNumber + '\'' +
                ", coursesName='" + coursesName + '\'' +
                ", time='" + time + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                '}';
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getCoursesName() {
        return coursesName;
    }

    public void setCoursesName(String coursesName) {
        this.coursesName = coursesName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
