package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Professor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;

@Service
@PropertySource("classpath:application.properties")
public class Report {

    private final Environment env;
    private final ProfessorsService professorsService;
    private final LearnsService learnsService;

    @Autowired
    public Report(Environment env, ProfessorsService professorsService, LearnsService learnsService) {
        this.env = env;
        this.professorsService = professorsService;
        this.learnsService = learnsService;
    }

    //Составляем отчёт
    public void writeIntoExcel() throws FileNotFoundException, IOException{

        //Путь к файлу
        String file = env.getProperty("file.report.xlsx.path");

        //Подключаемся к xlsx файлу
        Workbook book = new XSSFWorkbook();

        //Создаём новый лист
        Sheet sheet = book.createSheet("Отчёт");

        //Строка заголовков для столбцов
        Row title = sheet.createRow(0);

        //Оформляем оглавление столбцов таблицы
        Cell nameTitle = title.createCell(0);
        nameTitle.setCellValue("ФИО профессора");

        Cell allStudentsTitle = title.createCell(1);
        allStudentsTitle.setCellValue("Всего студентов");

        Cell middleFinalMarkTitle = title.createCell(2);
        middleFinalMarkTitle.setCellValue("Средняя успеваемость студентов");

        //Коллекция сущностей профессоров
        List<Professor> professors = professorsService.findAll();

        //Итератор для коллекции
        int i = 0;

        //Поочерёдно записываем профессоров в отчёт
        while (i < professors.size()) {

            //Записываем со 2й строки чтобы не перезаписывать оглавление (начинается c 0)
            Row row = sheet.createRow(i + 1);

            //Выбираем сущность из коллекции с индексом совпадающим с итерацией
            Professor professor = professors.get(i);

            //Записываем ФИО
            Cell name = row.createCell(0);
            name.setCellValue(professor.getName());

            //Записываем коллчество студентов на курсе
            Cell allStudents = row.createCell(1);
            allStudents.setCellValue(professorsService.countStudents(professor.getId()));

            //Записываем среднюю успеваемость
            Cell middleFinalMark = row.createCell(2);
            middleFinalMark.setCellValue(learnsService.middleFinalMark(professor.getId()));

            i++;
        }

        //Записываем всё в файл
        book.write(new FileOutputStream(file));

        //Закрываем соединение с файлом
        book.close();
    }
}
