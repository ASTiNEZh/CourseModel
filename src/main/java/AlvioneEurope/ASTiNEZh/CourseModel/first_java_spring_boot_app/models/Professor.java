package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Professor")
public class Professor {

    //ПОЛЯ//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //Имя профессора
    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    //Адрес профессора
    @Column(name = "address")
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 12, max = 200, message = "Address should be between 12 and 200 characters")
    @Pattern(regexp = "[ЁА-Яёа-я]+, [ЁА-Яёа-я]+, \\d{6}",
             message = "Your address should be in this format: Country, City, Post code (6 digits)")
    private String address;

    //Телефон проффессора
//    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "phone")
    @NotEmpty(message = "Phone should not be empty")
    @Pattern(regexp = "\\+\\d{1,2}\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}",
            message = "Your phone should be in this format: +7(978)xxx-xx-xx or +38(097)xxx-xx-xx")
    private String phone;

    //Зарплата
    @Column(name = "salary")
    private float salary;

    //Преподаваемые курсы
    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Course> courses;

    //КОНСТРУКТОРЫ//

    public Professor() {}

    public Professor(int id) {
        this.id = id;
    }

    //GET'РЫ И SET'РЫ//

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //equals() и hashCode()//

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return id == professor.id && Float.compare(professor.salary, salary) == 0 && Objects.equals(name, professor.name) && Objects.equals(address, professor.address) && Objects.equals(phone, professor.phone) && Objects.equals(courses, professor.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phone, salary, courses);
    }
}
