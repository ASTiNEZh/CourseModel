package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.dao;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Student;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

//Data Assets Objects(далее DAO) - класс для извлечения данных из базы данных(далее БД)
@Component
public class StudentDAO {

    //Сессия транзакции Hibernate
    private final EntityManager entityManager;

    //Внедрение подходящих бинов в конструктор
    @Autowired
    public StudentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //Создание HQL запроса в БД для извлечения данных. Объект с данными помещён в "обёртку" Optional<> для удобства при
    // валидации
     public Optional<Student> selectCreditBook(int creditBook) {

        Session session = entityManager.unwrap(Session.class);
        return session.createQuery(
                "SELECT S.creditBook FROM Student S WHERE S.creditBook=:creditBook",Student.class).
                setParameter("creditBook", creditBook).stream().findAny();
    }

    public Optional<Student> selectPhone(String phone) {

        Session session = entityManager.unwrap(Session.class);
        return session.createQuery(
                        "SELECT S.creditBook FROM Student S WHERE S.phone=:phone",Student.class).
                setParameter("phone", phone).stream().findAny();
    }

    public Optional<Student> selectEmail(String email) {

        Session session = entityManager.unwrap(Session.class);
        return session.createQuery(
                        "SELECT S.creditBook FROM Student S WHERE S.email=:email",Student.class).
                setParameter("email", email).stream().findAny();
    }

    public Optional<Student> selectPhoneForEdit(int creditBook, String phone) {

        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT S.creditBook FROM Student S WHERE S.phone=:phone AND S.creditBook <>" +
                " :creditBook", Student.class).setParameter("phone", phone).setParameter("creditBook",creditBook).
                stream().findAny();
    }

    public Optional<Student> selectEmailForEdit(int creditBook, String email) {

        Session session = entityManager.unwrap(Session.class);
        return session.createQuery(
                        "SELECT S.creditBook FROM Student S WHERE S.email=:email AND S.creditBook <> :creditBook",
                        Student.class).setParameter("email", email).setParameter("creditBook",creditBook).
                stream().findAny();
    }
}
