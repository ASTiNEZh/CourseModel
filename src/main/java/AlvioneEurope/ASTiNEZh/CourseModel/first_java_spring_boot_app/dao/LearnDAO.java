package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.dao;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Learn;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class LearnDAO {

    //Сессия транзакции Hibernate
    private final EntityManager entityManager;

    //JDBC API в "обвёртке" JDBC Template
    private final JdbcTemplate jdbcTemplate;

    //Внедрение подходящих бинов в конструктор
    @Autowired
    public LearnDAO(EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    //Все сущности для студентов
    public List<Learn> findAllForStudents(int creditBook) {

        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT L FROM Learn L WHERE L.student.creditBook=:creditBook",Learn.class).
                setParameter("creditBook", creditBook).getResultList();
    }

    //Все сущности для профессоров
    public List<Learn> findAllForProfessors(int number) {

        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT L FROM Learn L WHERE L.course.number=:number AND L.finalMark = 0",
                        Learn.class).setParameter("number", number).getResultList();
    }

    //Вывод только не пройденых курсов
    public List<Learn> noFinished(int creditBook) {

        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT L FROM Learn L WHERE L.finalMark = 0 AND L.student.creditBook=:creditBook",
                Learn.class).setParameter("creditBook",creditBook).getResultList();
    }

    //Вывод только пройденых курсов
    public List<Learn> findFinished(int creditBook) {

        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT L FROM Learn L WHERE L.finalMark <> 0 AND" +
                " L.student.creditBook=:creditBook",Learn.class).setParameter("creditBook",creditBook).
                getResultList();
    }

    //Извлечение итоговых отметок из БД
    public List<Learn> middleFinalMark(int id) {
        return jdbcTemplate.query("SELECT L.finalmark FROM Learn L JOIN Course C ON C.number = L.number JOIN Professor P ON P.id = C.professor WHERE P.id = ?",
                new BeanPropertyRowMapper<>(Learn.class),id);
    }
}
