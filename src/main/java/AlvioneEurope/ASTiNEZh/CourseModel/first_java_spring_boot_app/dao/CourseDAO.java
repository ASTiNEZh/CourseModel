package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.dao;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Course;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

//Data Assets Objects(далее DAO) - класс для извлечения данных из базы данных(далее БД)
@Component
public class CourseDAO {

    //Сессия транзакции Hibernate
    private final EntityManager entityManager;

    //Внедрение подходящих бинов в конструктор
    @Autowired
    public CourseDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //Создание HQL запроса в БД для извлечения данных. Объект с данными помещён в "обёртку" Optional<> для удобства при
    // валидации
    public Optional<Course> selectNumber(int number) {

        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT C.number FROM Course C WHERE C.number=:number",Course.class).
                setParameter("number",number).stream().findAny();
    }

    //Курсы которые преподаёт профессор
    public List<Course> selectForProfessor(int id) {

        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT C FROM Course C WHERE C.professor.id=:id",Course.class).
                setParameter("id",id).getResultList();
    }
}
