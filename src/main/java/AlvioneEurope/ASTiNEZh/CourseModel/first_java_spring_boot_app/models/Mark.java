package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Mark")
public class Mark {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Оценка
    @Column(name = "markvalue")
    private int markValue;

    //Присоединение к родительской сущности
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creditbook", referencedColumnName = "creditbook")
    @JoinColumn(name = "number", referencedColumnName = "number")
    private Learn learn;

    public int getMarkValue() {
        return markValue;
    }

    public void setMarkValue(int markValue) {
        this.markValue = markValue;
    }

    public Learn getLearn() {
        return learn;
    }

    public void setLearn(Learn learn) {
        this.learn = learn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return id == mark.id && markValue == mark.markValue && Objects.equals(learn, mark.learn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, markValue, learn);
    }
}
