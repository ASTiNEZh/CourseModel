package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.controllers;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Student;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services.StudentsService;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.util.EditStudentValidator;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.util.NewStudentValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentsController {

    //ПОЛЯ//

    //Сервис (логика приложения)
    private final StudentsService studentsService;

    //Валидаторы
    private final NewStudentValidator newStudentValidator;
    private final EditStudentValidator editStudentValidator;

    //КОНСТРУКТОРЫ//

    //Внедрения подходящих бинов в конструктор
    @Autowired//Явное указание
    public StudentsController(StudentsService studentsService, NewStudentValidator newStudentValidator, EditStudentValidator editStudentValidator) {
        this.studentsService = studentsService;
        this.newStudentValidator = newStudentValidator;
        this.editStudentValidator = editStudentValidator;
    }

    //МЕТОДЫ//

    //CREATE

    //Форма для создания новой сущности
    @GetMapping("/new")
    public String getNew(@ModelAttribute("student") Student student) {
        return "students/new";
    }

    //Сохранение сущности в БД
    @PostMapping()
    public String postNew(@ModelAttribute("student") @Valid Student student, BindingResult bindingResult) {

        //Валидация по заданым полям
        newStudentValidator.validate(student,bindingResult);

        //Валидация по аннотациям полей
        if (bindingResult.hasErrors())
            return "students/new";

        //Сохранение в БД
        studentsService.save(student);

        return "redirect:/students";
    }

    //READ

    //Чтение всех существующих сущностей
    @GetMapping()
    public String getAll(Model model) {

        model.addAttribute("students", studentsService.findAll());

        return "students/all";
    }
    //Чтение конкрентной сущности
    @GetMapping("/{creditBook}")
    public String getOne(@PathVariable("creditBook") int creditBook, Model model) {

        model.addAttribute("student", studentsService.getOne(creditBook));

        return "students/one";
    }

    //UPDATE

    //Чтение данных для редактирования конкретной сущности
    @GetMapping("/{creditBook}/edit")
    public String getUpdate(Model model, @PathVariable("creditBook") int creditBook) {

        //Запрос в БД и отправка данных конкретной сущности в представление посредством Model
        model.addAttribute("student", studentsService.getOne(creditBook));

        return "students/edit";
    }
    //Обновление сущности
    @PatchMapping("/{creditBook}")
    public String postUpdate(@ModelAttribute("student") @Valid Student student, BindingResult bindingResult,
                         @PathVariable("creditBook") int creditBook) {

        //Валидация по заданым полям
        editStudentValidator.validate(student,bindingResult);

        //Валидация по аннотации полей
        if (bindingResult.hasErrors())
            return "students/edit";

        //Обновление данных в БД
        studentsService.update(creditBook, student);
        return "redirect:/students";
    }

    //DELETE

    //Чтение всех существующих сущностей для удаления
    @GetMapping("/delete")
    public String getDelete(Model model) {

        model.addAttribute("students", studentsService.findAll());

        return "students/delete";
    }

    //Удаление конкретной сущности
    @DeleteMapping("/{creditBook}")
    public String delete(@PathVariable("creditBook") int creditBook) {

        //Удаление заданной сущности
        studentsService.delete(creditBook);

        return "redirect:/students";
    }
}