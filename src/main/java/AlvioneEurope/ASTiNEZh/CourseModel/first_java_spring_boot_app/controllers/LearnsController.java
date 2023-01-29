package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.controllers;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services.LearnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/learns")
public class LearnsController {

    //ПОЛЯ//

    //Сервис (логика приложения)
    private final LearnsService learnsService;

    //КОНСТРУКТОРЫ//

    //Внедрения подходящих бинов в конструктор
    @Autowired//Явное указание
    public LearnsController(LearnsService learnsService) {
        this.learnsService = learnsService;
    }

    //МЕТОДЫ//

    //CREATE

    //Сохранение сущности в БД
    @GetMapping("/new")
    public String addNew(@RequestParam("creditBook") int creditBook, @RequestParam("number") int number) {
        //Сохранение сущности в БД
        learnsService.save(creditBook,number);
        return "redirect:/professors";
    }

    //READ

    //Просмотр изучаемых курсов
    @GetMapping("/students")
    public String getAllForStudents(@RequestParam("creditBook") int creditBook, Model model) {

        //Запрос в БД и отправка данных всех сущностей в представление посредством Model
        model.addAttribute("learns", learnsService.noFinished(creditBook));
        model.addAttribute("creditBook", creditBook);

        return "learns/allNoFinishedForStudents";
    }
    //Просмотр изучаемых курсов
    @GetMapping("/students/{number}")
    public String getOneForStudents(@RequestParam("creditBook") int creditBook, @PathVariable("number") int number,
                                    Model model) {

        //Запрос в БД и отправка данных всех сущностей в представление посредством Model
        model.addAttribute("learn", learnsService.getOne(creditBook, number));
        model.addAttribute("middleMark", learnsService.getMiddleMark(creditBook, number));

        return "learns/oneForStudents";
    }

    //Просмотр изученных курсов
    @GetMapping("/students/finished")
    public String getFinished(@RequestParam("creditBook") int creditBook, Model model) {

        //Запрос в БД и отправка данных всех сущностей в представление посредством Model
        model.addAttribute("learns", learnsService.findFinished(creditBook));

        return "learns/allFinishedForStudents";
    }

    //Чтение всех существующих сущностей для профессоров
    @GetMapping("/professors")
    public String getAllForProfessors(@RequestParam("number") int number, Model model) {

        //Запрос в БД и отправка данных всех сущностей в представление посредством Model
        model.addAttribute("learns", learnsService.findAllForProfessors(number));

        return "learns/allForProfessors";
    }

    //Вычисление среднего текущего бала
    @GetMapping("/middleMark")
    public String getMiddleMark(@RequestParam("creditBook") int creditBook, @RequestParam("number") int number) {

        float middleMark = learnsService.getMiddleMark(creditBook, number);

        return "redirect:/marks/new?creditBook=" + creditBook + "&number=" + number + "&middleMark=" + middleMark;
    }

    //Выставление итоговой оценки
    @GetMapping("/finalMark")
    public String finalCourse(@RequestParam("creditBook") int creditBook, @RequestParam("number") int number) {

        learnsService.finalCourse(creditBook, number);

        return "redirect:/learns/professors?number=" + number;
    }

    //DELETE

    //Удаление конкретной сущности
    @GetMapping("/delete")
    public String delete(@RequestParam("creditBook") int creditBook, @RequestParam("number") int number) {

        //Удаление заданной сущности
        learnsService.delete(learnsService.getOne(creditBook,number));

        return "redirect:/students/" + creditBook;
    }
}