package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

//Thymeleaf не мог сопоставить данные на выходе из контроллера, и входе в представление (не распознавал объекты и
// переменные в представлении и т.д.). Из application.properties настройка не давала результата, поэтому было решено
// прибегнуть к ручной настройке Thymeleaf

@Configuration
@PropertySource("classpath:application.properties")
public class SpringConfig implements WebMvcConfigurer {

    //applicationContext внедряем из Spring
    private final ApplicationContext appContext;

    //Внедрение подходящих бинов в конструктор
    @Autowired
    public SpringConfig(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    //Бин настройки Thymeleaf
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();

        //Внедрение среды приложения (из Spring'а)
        templateResolver.setApplicationContext(appContext);
        // Пути к представлениям
        templateResolver.setPrefix("classpath:/templates/");
        //Расширение файлов
        templateResolver.setSuffix(".html");
        //Кодировка
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;
    }
}
