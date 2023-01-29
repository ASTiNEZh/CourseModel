package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.dao.CourseDAO;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Course;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Professor;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Student;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.repositories.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)//Транзакции в классе только для чтения
public class CoursesService {

    //ПОЛЯ//

    //Репозитории реализущие элементарные запросы в БД
    private final CoursesRepository coursesRepository;

    //Подключение сервисов других сущностей для взаимодействия с ними на уровне бизнес-логики
    private final ProfessorsService professorsService;

    //Подключение data assets objects(DAO) прохождения курса
    private final CourseDAO courseDAO;

    //КОНСТРУКТОРЫ//

    //Внедрение подходящих бинов в конструктор
    @Autowired
    public CoursesService(CoursesRepository coursesRepository, ProfessorsService professorsService,
                          CourseDAO courseDAO) {
        this.coursesRepository = coursesRepository;
        this.professorsService = professorsService;
        this.courseDAO = courseDAO;
    }

    //МЕТОДЫ//

    //Вывести из БД список всех сущностей для студентов
    //Курс должен не изучатся ни в данный момент, ни ранее
    public List<Course> findAllForStudents(int creditBook) {

        //Список пришедший из БД
        List<Course> courses = coursesRepository.findAll();

        //Итератор для исключения конфликта с итератором courses
        Iterator<Course> iterator = courses.iterator();

        //Перебераем список courses через итератор
        while(iterator.hasNext()) {

            //Обращаясь к итератору создаём объект
            Course course = iterator.next();

            //Вызываем всех студентов этого курса
            List<Student> students = course.getStudents();

            //Перебираем студентов
            for (Student student : students){

                //Если обнаруживается искомый студент, то удаляем курс из списка
                if (student.getCreditBook() == creditBook){
                    iterator.remove();
                }
            }
        }

        return courses;
    }

    //Вывести из БД список всех сущностей для профессоров
    //Курс должен находится в стадии изучения
    public List<Course> findAllForProfessors(int id) {
        return courseDAO.selectForProfessor(id);
    }

    //Вывести из БД конкретную сущность
    public Course getById(int number) {
        return coursesRepository.getReferenceById(number);
    }

    //Сохранить сущность
    @Transactional//Данный метод использует обычную транзакцию
    public void save(Course course, int id, boolean selfStudy) {

        //Если курс не для самообучения - назначить преподавателя
        Professor professor;
        if (!selfStudy)
            professor = professorsService.getOne(id);
        else
            professor = professorsService.getOne(0);

        //Назначаем преподавателя курса
        course.setProfessor(professor);

        //Сохраняем курс
        coursesRepository.save(course);
    }

    //Обновить данные сущности
    @Transactional//Данный метод использует обычную транзакцию
    public void update(Course course, int id) {
        //Назначаем преподавателя курса
        course.setProfessor(professorsService.getOne(id));

        //Сохраняем курс в БД
        coursesRepository.save(course);
    }

    //Удалить сущность
    @Transactional//Данный метод использует обычную транзакцию
    public  void delete(int number) {
        coursesRepository.deleteById(number);
    }
}
