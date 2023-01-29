package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.util;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.dao.CourseDAO;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NewCourseValidator implements Validator {

    //ПОЛЯ//

    //Data Assets Objects(далее DAO) необходимой сущности
    private final CourseDAO courseDAO ;

    //КОНСТРУКТОРЫ//

    //Внедрение подходящих бинов в конструктор
    @Autowired
    public NewCourseValidator(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    //МЕТОДЫ//

    //Проверка валидируемого объекта на принадлежность к требуемуму классу
    @Override
    public boolean supports(Class<?> clazz) {
        return Course.class.equals(clazz);
    }

    //Валидация
    @Override
    public void validate(Object target, Errors errors) {

        //Даункаст входящего объекта, до объекта требуемого класса
        Course course = (Course) target;

        //Проверка наличия сущности с указанным параметром
        if (courseDAO.selectNumber(course.getNumber()).isPresent()) {
            //если такя сущность существует, то вывести сообщение о нарушении требования уникальности
            errors.rejectValue("number", "", "Курс с таким номером уже существует!");
        }

        //Проверка адекватности параметров
        if (course.getCoast() < 0) {
            //Курс, возможно, может распространятся на безвозмездной основе, поэтому стоимость не может иметь
            // отрицательных значений
            errors.rejectValue("coast","","Стоимость не может иметь отрицательное значение!");
        }
        if (course.getNumber() < 0) {
            errors.rejectValue("number", "","Номер курса должен быть больше нуля!");
        }
    }
}
