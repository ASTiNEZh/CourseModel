package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.repositories;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository extends JpaRepository<Course, Integer> {
}
