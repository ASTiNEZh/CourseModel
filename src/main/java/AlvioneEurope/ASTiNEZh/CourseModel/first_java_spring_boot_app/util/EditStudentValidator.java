package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.util;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.dao.StudentDAO;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EditStudentValidator implements Validator {

    //ПОЛЯ//

    //Data Assets Objects(далее DAO) необходимой сущности
    private final StudentDAO studentDAO;

    //КОНСТРУКТОРЫ//

    //Внедрение подходящих бинов в конструктор
    @Autowired
    public EditStudentValidator(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    //МЕТОДЫ//

    //Проверка валидируемого объекта на принадлежность к требуемуму классу
    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    //Валидация
    @Override
    public void validate(Object target, Errors errors) {

        //Даункаст входящего объекта, до объекта требуемого класса
        Student student = (Student) target;

        //Проверка наличия сущности с указанными параметрами
        if (studentDAO.selectPhoneForEdit(student.getCreditBook(),student.getPhone()).isPresent()){
            errors.rejectValue("phone", "", "Этот номер телефона уже зарегестрирован!");
        }
        if (studentDAO.selectEmailForEdit(student.getCreditBook(),student.getEmail()).isPresent()){
            errors.rejectValue("email", "", "Этот e-Mail уже зарегестрирован!");
        }
    }
}
