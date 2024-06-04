package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Learn")
public class Learn {

    //Составной первичный ключ
    @EmbeddedId
    private LearnKey learnKey;

    //Итоговая оценка
    @Column(name = "finalmark")
    private float finalMark;

    //Поле вне БД
    @Transient
    private float middleMark;

    //Оценки студента (Присоединение к дочерней сущности)
    @OneToMany(mappedBy = "learn", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Mark> marks;

    //Изучающий студент
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creditbook", referencedColumnName = "creditbook", insertable=false, updatable=false)
    private Student student;

    //Изучаемый курс
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "number", referencedColumnName = "number", insertable=false, updatable=false)
    private Course course;

    public Learn() {}

    public Learn(LearnKey learnKey) {
        this.learnKey = learnKey;
    }

    public Learn(Student student, Course course) {
        LearnKey learnKey = new LearnKey();
        learnKey.setCourse(course);
        learnKey.setStudent(student);
        this.learnKey = learnKey;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public float getFinalMark() {
        return finalMark;
    }

    public void setFinalMark(float finalMark) {
        this.finalMark = finalMark;
    }

    public float getMiddleMark() {
        return middleMark;
    }

    public void setMiddleMark(float middleMark) {
        this.middleMark = middleMark;
    }

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

    public LearnKey getLearnKey() {
        return learnKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Learn learn = (Learn) o;
        return Float.compare(learn.finalMark, finalMark) == 0 && Float.compare(learn.middleMark, middleMark) == 0 && Objects.equals(learnKey, learn.learnKey) && Objects.equals(marks, learn.marks) && Objects.equals(student, learn.student) && Objects.equals(course, learn.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(learnKey, finalMark, middleMark, marks, student, course);
    }
}
