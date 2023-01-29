package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.repositories;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends JpaRepository<Student, Integer> {
}
