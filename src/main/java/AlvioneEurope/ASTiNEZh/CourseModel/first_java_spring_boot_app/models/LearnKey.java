package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LearnKey implements Serializable {

    //ПОЛЯ//

    //Изучающий студент
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creditbook")
    private Student student;

    //Изучаемый курс
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "number")
    private Course course;

    //КОНСТРУКТОРЫ//

    public LearnKey() {}

//        public LearnKey(Student student, Course course) {
//            this.student = student;
//            this.course = course;
//        }

    //GET'РЫ И SET'РЫ//

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    //equals() и hashCode()//

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearnKey learnKey = (LearnKey) o;
        return Objects.equals(student, learnKey.student) && Objects.equals(course, learnKey.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course);
    }
}
