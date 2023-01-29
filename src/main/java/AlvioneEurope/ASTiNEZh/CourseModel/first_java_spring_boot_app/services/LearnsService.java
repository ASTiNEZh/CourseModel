package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.dao.LearnDAO;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Learn;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.LearnKey;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.repositories.LearnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LearnsService {

    //Подключение репозитория прохождений курсов
    private final LearnsRepository learnsRepository;

    //Подключение сервисов других сущностей для взаимодействия с ними на уровне бизнес-логики
    private final StudentsService studentsService;
    private final CoursesService coursesService;
    private final MiddleValue middleValue;


    //Подключение data assets objects(DAO) прохождения курса
    private final LearnDAO learnDAO;

    //Внедрения подходящих бинов в конструктор
    @Autowired
    public LearnsService(LearnsRepository learnsRepository, StudentsService studentsService,
                         CoursesService coursesService, LearnDAO learnDAO, MiddleValue middleValue) {
        this.learnsRepository = learnsRepository;
        this.studentsService = studentsService;
        this.coursesService = coursesService;
        this.learnDAO = learnDAO;
        this.middleValue = middleValue;
    }

    //Вывести составной первичный ключ
    private LearnKey getLearnKey(int creditBook, int number) {
        Learn learn = new Learn(studentsService.getOne(creditBook),coursesService.getById(number));
        return learn.getLearnKey();
    }

    //Возвращает список прохождений курсов для профессоров. Исключение пройденых курсов.
    public List<Learn> findAllForProfessors(int number) {
        return learnDAO.findAllForProfessors(number);
    }

    //Вывод только не пройденых курсов
    public List<Learn> noFinished(int creditBook) {
        return learnDAO.noFinished(creditBook);
    }

    //Вывод только пройденых курсов
    public List<Learn> findFinished(int creditBook) {
        return learnDAO.findFinished(creditBook);
    }

    //Чтение из БД данных о прохождении
    public Learn getOne(int creditBook, int number) {
        return learnsRepository.getReferenceById(getLearnKey(creditBook, number));
    }

    //Вычисление среднего текущего бала
    public float getMiddleMark(int creditBook, int number) {

        //Вызов необхоимых сущностей
        Learn learn = learnsRepository.getReferenceById(getLearnKey(creditBook, number));

        //Вычесляем среднее значение
        float middleMark = middleValue.middleMark(learn.getMarks());

        //Внедрение вычесленного значения в поле и записываем значения в БД
        learn.setMiddleMark(middleMark);

        return middleMark;
    }

    //Связка с другими сущностями и сохранение в БД
    @Transactional
    public void save(int creditBook, int number) {
        Learn learn = new Learn(getLearnKey(creditBook, number));
        learnsRepository.save(learn);
    }

    //Удаление сущности
    @Transactional
    public void delete(Learn learn) {
        learnsRepository.delete(learn);
    }

    //Итоговая оценка выставляется как среднее арифметическое всех оценнок за курс и заносится в БД
    @Transactional
    public void finalCourse(int creditBook, int number) {

        //Высчитываем оценку
        float finalMark = getMiddleMark(creditBook, number);

        //Вызываем курс из БД
        Learn learn = getOne(creditBook,number);

        //Сохраняем оценку в БД
        learn.setFinalMark(finalMark);
        learnsRepository.save(learn);
    }

    //Вычисляем средний бал студентов которым преподаёт конкретный профессор
    public float middleFinalMark(int id) {

        //Объявляем переменную
        float middleFinalMark;

        //Вызываем сущности изучения курсов
        List<Learn> learns = learnDAO.middleFinalMark(id);

        //Вычисляем значение
        middleFinalMark = middleValue.middleFinalMark(learns);

        return middleFinalMark;
    }
}
