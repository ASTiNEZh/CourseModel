package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.controllers;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Professor;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services.Report;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services.ProfessorsService;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.util.ProfessorValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Controller
@RequestMapping("/professors")
public class ProfessorsController {

    //Сервис (логика приложения)
    private final ProfessorsService professorsService;

    //Валидатор
    private final ProfessorValidator professorValidator;

    //Микросервис для отчётности
    private final Report report;

    //Внедрение подходящих бинов в конструктор
    @Autowired//Явное указание
    public ProfessorsController(ProfessorValidator professorValidator, ProfessorsService professorsService,
                                Report report) {
        this.professorValidator = professorValidator;
        this.professorsService = professorsService;
        this.report = report;
    }

    //Форма для создания новой сущности
    @GetMapping("/new")
    public String getNew(@ModelAttribute("professor") Professor professor) {
        return "professors/new";
    }

    //Сохранение сущности в БД
    @PostMapping()
    public String postNew(@ModelAttribute("professor") @Valid Professor professor, BindingResult bindingResult) {

        //Валидация по заданым полям
        professorValidator.validate(professor,bindingResult);

        //Валидация по аннотации полей
        if (bindingResult.hasErrors())
            return "professors/new";

        //Сохранение в БД
        professorsService.save(professor);
        return "redirect:/professors";
    }

    //Чтение всех существующих сущностей
    @GetMapping()
    public String getAll(Model model) {

        //Запрос в БД и отправка данных всех сущностей в представление посредством Model
        model.addAttribute("professors", professorsService.findAll());

        return "professors/index";
    }
    
    //Чтение конкрентного сущности
    @GetMapping("/{id}")
    public String getOne(@PathVariable("id") int id, Model model) {

        //Запрос в БД и отправка данных конкретной сущности в представление посредством Model
        model.addAttribute("professor", professorsService.getOne(id));

        return "professors/show";
    }

    //Создание / обновление отчёта
    @GetMapping("/report")
    public String report() {
        try {
            report.writeIntoExcel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/professors";
    }

    //Чтение данных для редактирования конкретной сущности
    @GetMapping("/{id}/edit")
    public String getUpdate(Model model, @PathVariable("id") int id) {

        //Запрос в БД и отправка данных конкретной сущности в представление посредством Model
        model.addAttribute("professor", professorsService.getOne(id));

        return "professors/edit";
    }
    
    //Обновление сущности
    @PatchMapping("/{id}")
    public String postUpdate(@ModelAttribute("professor") @Valid Professor professor, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        //Валидация по заданым полям
        professorValidator.validate(professor,bindingResult);

        //Валидация по аннотации полей
        if (bindingResult.hasErrors())
            return "professors/edit";

        //Обновление данных в БД
        professorsService.update(professor);

        return "redirect:/professors";
    }

    //Удаление конкретной сущности
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        //Удаление заданной сущности
        professorsService.delete(id);

        return "redirect:/professors";
    }
}
