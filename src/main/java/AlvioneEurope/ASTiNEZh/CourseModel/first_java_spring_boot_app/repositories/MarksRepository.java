package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.repositories;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarksRepository extends JpaRepository<Mark, Integer> {
}
