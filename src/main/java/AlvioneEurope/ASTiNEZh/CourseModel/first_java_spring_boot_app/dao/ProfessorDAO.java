package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.dao;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Professor;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Student;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.Optional;

//Data Assets Objects(далее DAO) - класс для извлечения данных из базы данных(далее БД)
@Component
public class ProfessorDAO {

    //ПОЛЯ//

    //Сессия транзакции Hibernate
    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;

    //КОНСТРУКТОРЫ//

    //Внедрение подходящих бинов в конструктор
    @Autowired
    public ProfessorDAO(EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    //МЕТОДЫ//

    //Создание HQL запроса в БД для извлечения данных. Объект с данными помещён в "обёртку" Optional<> для удобства при
    // валидации
    public Optional<Professor> selectPhone(int id, String phone) {

        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT P.phone FROM Professor P WHERE P.phone = :phone AND P.id <> :id",
                Professor.class).setParameter("phone",phone).setParameter("id",id).stream().findAny();
    }

    public long countStudents(int id) {
//
//        return jdbcTemplate.query("SELECT S FROM Student S" +
//                        " JOIN Learn L ON L.creditbook = S.creditbook JOIN Course C ON C.number = L.number" +
//                        " JOIN Professor P ON P.id = C.professor WHERE P.id = ?",
//                new BeanPropertyRowMapper<>(Student.class),id).size();

        Session session = entityManager.unwrap(Session.class);

        //Текст HQL запроса
        String str;
        str = "SELECT S FROM Student S JOIN Learn L ON L.student.creditBook=S.creditBook JOIN Course C ON C.number=L.course.number JOIN Professor P ON P.id=C.professor.id WHERE P.id=:id";

        return session.createQuery(str,Student.class).setParameter("id",id).stream().count();
    }
}