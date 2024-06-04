package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Learn;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Mark;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MarksService {

    //Репозитории реализущие элементарные запросы в БД
    private final MarksRepository marksRepository;

    private final LearnsService learnsService;

    //Внедрение подходящих бинов в конструктор
    @Autowired
    public MarksService(MarksRepository marksRepository, LearnsService learnsService) {
        this.marksRepository = marksRepository;
        this.learnsService = learnsService;
    }

    //Сохранить сущность
    public void save(Mark mark, int creditBook, int number) {
        mark.setLearn(learnsService.getOne(creditBook,number));
        marksRepository.save(mark);
    }

}
