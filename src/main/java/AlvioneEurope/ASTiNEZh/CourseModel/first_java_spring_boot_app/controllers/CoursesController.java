package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.controllers;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Course;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services.CoursesService;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.util.NewCourseValidator;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.util.EditCourseValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    //ПОЛЯ//

    //Сервис (логика приложения)
    private final CoursesService coursesService;

    //Валидаторы
    private final NewCourseValidator newCourseValidator;
    private final EditCourseValidator editCourseValidator;

    //КОНСТРУКТОРЫ//

    //Внедрения подходящих бинов в конструктор
    @Autowired//Явное указание
    public CoursesController(CoursesService coursesService, NewCourseValidator newCourseValidator, EditCourseValidator editCourseValidator) {
        this.coursesService = coursesService;
        this.newCourseValidator = newCourseValidator;
        this.editCourseValidator = editCourseValidator;
    }

    //МЕТОДЫ//

    //CREATE

    //Форма для создания новой сущности
    @GetMapping("/new")
    public String getNew(@ModelAttribute("course") Course course, @RequestParam("id") int id, Model model) {

        model.addAttribute("id", id);

        return "courses/new";
    }

    //Сохранение сущности в БД
    @PostMapping()
    public String postNew(@ModelAttribute("course") @Valid Course course, BindingResult bindingResult, Model model,
                          @RequestParam(value = "selfStudy", defaultValue = "false") boolean selfStudy,
                          @RequestParam("id") int id) {

        //Валидация по заданым полям
        newCourseValidator.validate(course,bindingResult);

        //Валидация по аннотациям полей
        if (bindingResult.hasErrors()) {

            model.addAttribute("id", id);

            return "courses/new";
        }

        //Сохранение в БД
        coursesService.save(course,id,selfStudy);

        return "redirect:/courses/professors?id=" + id;
    }

    //READ

    //Чтение всех существующих сущностей для студентов
    @GetMapping("/students")
    public String getAllForStudents(@RequestParam("creditBook") int creditBook, Model model) {

        //Запрос в БД и отправка данных всех сущностей в представление посредством Model
        model.addAttribute("courses", coursesService.findAllForStudents(creditBook));
        model.addAttribute("creditBook", creditBook);

        return "courses/allForStudents";
    }

    //Чтение всех существующих сущностей для профессоров
    @GetMapping("/professors")
    public String getAllForProfessors(@RequestParam("id") int id, Model model) {

        //Запрос в БД и отправка данных всех сущностей в представление посредством Model
        model.addAttribute("courses", coursesService.findAllForProfessors(id));

        return "courses/allForProfessors";
    }

    //Чтение конкрентной сущности для студентов
    @GetMapping("/{number}/students")
    public String getOne(@RequestParam("creditBook") int creditBook, @PathVariable("number") int number, Model model) {

        //Запрос в БД и отправка данных конкретной сущности в представление посредством Model
        model.addAttribute("course", coursesService.getById(number));
        model.addAttribute("creditBook", creditBook);

        return "courses/oneForStudents";
    }

    //Чтение конкрентной сущности для профессоров
    @GetMapping("/{number}/professors")
    public String getOne(@PathVariable("number") int number, Model model) {

        //Запрос в БД и отправка данных конкретной сущности в представление посредством Model
        model.addAttribute("course", coursesService.getById(number));

        return "courses/oneForProfessors";
    }

    //UPDATE

    //Чтение данных для редактирования конкретной сущности
    @GetMapping("/{number}/edit")
    public String getUpdate(Model model, @PathVariable("number") int number, @RequestParam("id") int id) {

        //Запрос в БД и отправка данных конкретной сущности в представление посредством Model
        model.addAttribute("course", coursesService.getById(number));
        model.addAttribute("id",id);

        return "courses/edit";
    }
    //Обновление сущности
    @PatchMapping("/{number}")
    public String postUpdate(@ModelAttribute("course") @Valid Course course, @RequestParam("id") int id, Model model,
                             BindingResult bindingResult) {

        //Валидация по заданым полям
        editCourseValidator.validate(course,bindingResult);

        //Валидация по аннотации полей
        if (bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            return "courses/edit";
        }

        //Обновление данных в БД
        coursesService.update(course,id);

        return "redirect:/courses/professors?id=" + id;
    }

    //DELETE

    //Удаление конкретной сущности
    @DeleteMapping("/{number}")
    public String delete(@PathVariable("number") int number, @RequestParam("id") int id) {

        //Удаление заданной сущности
        coursesService.delete(number);

        return "redirect:/courses/professors?id=" + id;
    }
}