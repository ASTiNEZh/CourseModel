package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Student;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.repositories.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)//Транзакции в классе только для чтения
public class StudentsService {

    //Репозитории реализущие элементарные запросы в БД
    private final StudentsRepository studentsRepository;

    //Внедрение подходящих бинов в конструктор
    @Autowired
    public StudentsService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    //Вывести из БД список всех сущностей
    public List<Student> findAll() {
        return studentsRepository.findAll();
    }

    //Вывести из БД конкретную сущность и вычеслить среднюю успеваемость
    @Transactional//Данный метод использует обычную транзакцию
    public Student getOne(int creditBook) {

        //Вызов списка изучаемых курсов
        Student student = studentsRepository.getReferenceById(creditBook);

        //Вычисляем среднее значение
        float middleMark = new MiddleValue().middleFinalMark(student.getLearns());

        //Внедрение вычесленного значения в поле
        student.setMiddleMark(middleMark);

        return student;
    }

    //Сохранить сущность
    @Transactional//Данный метод использует обычную транзакцию
    public void save(Student student) {
        studentsRepository.save(student);
    }

    //Обновить данные сущности
    @Transactional//Данный метод использует обычную транзакцию
    public void update(int creditBook, Student updStudent) {
        updStudent.setCreditBook(creditBook);
        studentsRepository.save(updStudent);
    }

    //Удалить сущность
    @Transactional//Данный метод использует обычную транзакцию
    public void delete(int creditBook) {
        studentsRepository.deleteById(creditBook);
    }
}
