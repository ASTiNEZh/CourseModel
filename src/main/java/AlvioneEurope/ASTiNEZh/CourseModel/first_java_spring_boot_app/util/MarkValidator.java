package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.util;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Mark;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MarkValidator implements Validator {

    //МЕТОДЫ//

    //Проверка валидируемого объекта на принадлежность к требуемуму классу
    @Override
    public boolean supports(Class<?> clazz) {
        return Mark.class.equals(clazz);
    }

    //Валидация
     @Override
    public void validate(Object target, Errors errors) {
        //Даункаст входящего объекта, до объекта требуемого класса
        Mark mark = (Mark) target;

         //Проверка адекватности параметров
         if (mark.getMarkValue() > 100) {
            errors.rejectValue("markValue", "", "Оценка не может быть больше ста!");
        }
        if (mark.getMarkValue() <= 0) {
            errors.rejectValue("markValue", "", "Оценка должна быть больше нуля!");
        }
    }
}
