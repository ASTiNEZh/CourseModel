package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Course")
public class Course {

    //ПОЛЯ//

    //Название курса
    @Column(name = "title")
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 1, max = 200, message = "Title should be between 1 and 200 characters")
    private String title;

    //Номер курса
    @Id
    @Column(name = "number")
    private int number;

    //Стоимость
    @Column(name = "coast")
    private float coast;

    //Прохождение курса студентами
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Learn> learns;

    //Преподаватель
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor", referencedColumnName = "id")
    private Professor professor;
//    @Column(name = "professor")
//    private int professorId;

    //Студенты изучающие курс
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Learn",
            joinColumns = @JoinColumn(name = "number"),
            inverseJoinColumns = @JoinColumn(name = "creditbook")
    )
    private List<Student> students;

    //КОНСТРУКТОРЫ//

    public Course() {}

    public Course(int number) {
        this.number = number;
    }

    public Course(String title, int number, float coast) {
        this.title = title;
        this.number = number;
        this.coast = coast;
    }

    //GET'РЫ И SET'РЫ//

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getCoast() {
        return coast;
    }

    public void setCoast(float coast) {
        this.coast = coast;
    }

    public List<Learn> getLearns() {
        return learns;
    }

    public void setLearns(List<Learn> learns) {
        this.learns = learns;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    //equals() и hashCode()//

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return number == course.number && Float.compare(course.coast, coast) == 0 && Objects.equals(title, course.title) && Objects.equals(learns, course.learns) && Objects.equals(professor, course.professor) && Objects.equals(students, course.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, number, coast, learns, professor, students);
    }
}
