package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.controllers;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Mark;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services.MarksService;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.util.MarkValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marks")
public class MarksController {

    //Сервис (логика приложения)
    private final MarksService marksService;

    //Валидатор
    private final MarkValidator markValidator;

    //Внедрения подходящих бинов в конструктор
    @Autowired//Явное указание
    public MarksController(MarksService marksService, MarkValidator markValidator) {
        this.marksService = marksService;
        this.markValidator = markValidator;
    }

    //Форма для создания новой сущности
    @GetMapping("/new")
    public String addNew(@ModelAttribute("mark") Mark mark, @RequestParam("creditBook") int creditBook,
                         @RequestParam("number") int number, @RequestParam("middleMark") float middleMark,
                         Model model) {
        model.addAttribute("creditBook", creditBook);
        model.addAttribute("number", number);
        model.addAttribute("middleMark", middleMark);

        return "marks/new";
    }
    
    //Сохранение сущности в БД
    @PostMapping()
    public String postNew(@ModelAttribute("mark") @Valid Mark mark, @RequestParam("creditBook") int creditBook,
                          @RequestParam("number") int number, @RequestParam("middleMark") float middleMark,
                          Model model, BindingResult bindingResult) {

        //Валидация по заданым полям
        markValidator.validate(mark,bindingResult);

        //Валидация по аннотациям полей
        if (bindingResult.hasErrors()) {

            model.addAttribute("creditBook", creditBook);
            model.addAttribute("number", number);
            model.addAttribute("middleMark", middleMark);

            return "marks/new";
        }

        //Сохранение в БД
        marksService.save(mark, creditBook, number);

        return "redirect:/learns/middleMark?creditBook=" + creditBook + "&number=" + number;
    }
}
