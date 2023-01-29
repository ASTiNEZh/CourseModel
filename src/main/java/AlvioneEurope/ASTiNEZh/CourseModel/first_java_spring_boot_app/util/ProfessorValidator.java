package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.util;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.dao.ProfessorDAO;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProfessorValidator implements Validator {

    //ПОЛЯ//

    //Data Assets Objects(далее DAO) необходимой сущности
    private final ProfessorDAO professorDAO;

    //КОНСТРУКТОРЫ//

    //Внедрение подходящих бинов в конструктор
    @Autowired
    public ProfessorValidator(ProfessorDAO professorDAO) {
        this.professorDAO = professorDAO;
    }

    //МЕТОДЫ//

    //Проверка валидируемого объекта на принадлежность к требуемуму классу
    @Override
    public boolean supports(Class<?> clazz) {
        return Professor.class.equals(clazz);
    }

    //Валидация
    @Override
    public void validate(Object target, Errors errors) {

        //Даункаст входящего объекта, до объекта требуемого класса
        Professor professor = (Professor) target;

        //Проверка наличия сущности с указанным параметром
        if (professorDAO.selectPhone(professor.getId(), professor.getPhone()).isPresent() ) {
            //если такя сущность существует, то вывести сообщение о нарушении требования уникальности
            errors.rejectValue("phone", "", "Этот телефон уже зарегестрирован!");
        }

        //Проверка адекватности параметра
        if (professor.getSalary() < 0) {
            //Преподавание, возможно, может реализовыватся на безвозмездной основе, поэтому ЗП не может иметь
            // отрицательных значений
            errors.rejectValue("salary", "", "Зарплата не может иметь отрицательное значение!");
        }
    }
}
