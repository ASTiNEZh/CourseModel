package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Student")
public class Student {

    //ПОЛЯ//

    //Имя студента
    @Column(name = "name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно содержать от двух до ста символов")
    private String name;

    //Адрес студента
    @Column(name = "address")
    @NotEmpty(message = "Адрес не должен быть пустым")
    @Size(min = 2, max = 200, message = "Адрес должен содержать от двух до ста символов")
    @Pattern(regexp = "[ЁА-Яёа-я]+, [ЁА-Яёа-я]+, \\d{6}",
            message = "Ваш адрес должен быть в формате: Страна, Город, Почтовый индекс (6 цифр)")
    private String address;

    //Телефон студента
    @Column(name = "phone")
    @NotEmpty(message = "Телефон не должен быть пустым")
    @Pattern(regexp = "\\+\\d{1,2}\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}",
            message = "Ваш телефон должен быть в формате: +х(ххх)xxx-xx-xx либо +хх(ххх)xxx-xx-xx")
    private String phone;

    //e-Mail студента
    @Column(name = "email")
    @NotEmpty(message = "е-Мail не должен быть пустым")
    @Size(max = 120, message = "е-Мail должен быть менее 120 символов")
    @Email(message = "е-Мail должен соответствовать")
    private String email;

    //Номер зачётки
    @Id
    @Column(name = "creditbook")
    private int creditBook;

    //Средняя успеваемость
    @Transient
    private float middleMark;

    //Прохождение курсов и их итоговые оценки(пройденные курсы)
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Learn> learns;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    //КОНСТРУКТОРЫ//

    public Student() {}

    public Student(int creditBook) {
        this.creditBook = creditBook;
    }

    public Student(String name, String address, String phone, String email, int creditBook) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.creditBook = creditBook;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCreditBook() {
        return creditBook;
    }

    public void setCreditBook(int creditBook) {
        this.creditBook = creditBook;
    }

    public float getMiddleMark() {
        return middleMark;
    }

    public void setMiddleMark(float middleMark) {
        this.middleMark = middleMark;
    }

    public List<Learn> getLearns() {
        return learns;
    }

    public void setLearns(List<Learn> learns) {
        this.learns = learns;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    //equals() и hashCode()//

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return creditBook == student.creditBook && Float.compare(student.middleMark, middleMark) == 0 && Objects.equals(name, student.name) && Objects.equals(address, student.address) && Objects.equals(phone, student.phone) && Objects.equals(email, student.email) && Objects.equals(learns, student.learns) && Objects.equals(courses, student.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phone, email, creditBook, middleMark, learns, courses);
    }
}
