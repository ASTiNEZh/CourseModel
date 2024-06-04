package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.dao.ProfessorDAO;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Professor;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.repositories.ProfessorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)//Транзакции в классе только для чтения
public class ProfessorsService {

    //Репозитории реализущие элементарные запросы в БД
    private final ProfessorsRepository professorsRepository;

    //Подключение data assets objects(DAO) прохождения курса
    private final ProfessorDAO professorDAO;

    //Внедрение подходящих бинов в конструктор
    @Autowired
    public ProfessorsService(ProfessorsRepository professorsRepository, ProfessorDAO professorDAO) {
        this.professorsRepository = professorsRepository;
        this.professorDAO = professorDAO;
    }

    //Вывести из БД список всех сущностей
    public List<Professor> findAll() {
        return professorsRepository.findAll();
    }

    //Вывести из БД конкретную сущность
    public Professor getOne(int id) {
         return professorsRepository.getReferenceById(id);
    }

    //Сохранить сущность
    @Transactional//Данный метод использует обычную транзакцию
    public void save(Professor professor) {
        professorsRepository.save(professor);
    }

    //Обновить данные сущности
    @Transactional//Данный метод использует обычную транзакцию
    public void update(Professor updProfessor) {
        professorsRepository.save(updProfessor);
    }

    //Удалить сущность
    @Transactional//Данный метод использует обычную транзакцию
    public void delete(int id) {
        professorsRepository.deleteById(id);
    }

    public long countStudents(int id) {
        return professorDAO.countStudents(id);
    }
}
