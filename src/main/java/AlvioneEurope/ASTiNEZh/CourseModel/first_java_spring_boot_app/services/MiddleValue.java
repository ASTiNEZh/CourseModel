package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.services;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Learn;
import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Mark;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiddleValue {

    //Вычислить средний бал для изученных курсов
    public float middleFinalMark(List<Learn> learns){

        //Средний бал за курс
        float middleMark;

        //Сумма всех оценнок
        float sum = 0;

        //Колличество оценнок
        int i = 0;

        //Суммирование значений всех итоговых оценнок
        while (learns.size() > i){
            if(learns.get(i).getFinalMark() > 0) {
                sum += learns.get(i).getFinalMark();
            }
            i++;
        }

        //Вычисление среднего арифметического (вынесенно из цикла для исключения постоянного переопределения переменной
        // которая должна быть вычеслена 1 раз)
        middleMark = sum / i;
        return middleMark;
    }

    //Вычислить средний бал для изучаемых курсов
    public float middleMark(List<Mark> marks) {

        //Средний бал за курс
        float middleMark;

        //Сумма всех оценнок
        float sum = 0;

        //Колличество оценнок
        int i = 0;

        //Суммирование значений всех итоговых оценнок
        while (marks.size() > i){
            if(marks.get(i).getMarkValue() > 0) {
                sum += marks.get(i).getMarkValue();
            }
            i++;
        }

        //Вычисление среднего арифметического (вынесенно из цикла для исключения постоянного переопределения переменной
        // которая должна быть вычеслена 1 раз)
        return middleMark = sum / i;
    }
}
