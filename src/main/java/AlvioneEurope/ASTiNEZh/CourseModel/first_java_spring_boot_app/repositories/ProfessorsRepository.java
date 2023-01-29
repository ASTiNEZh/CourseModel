package AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.repositories;

import AlvioneEurope.ASTiNEZh.CourseModel.first_java_spring_boot_app.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorsRepository extends JpaRepository<Professor, Integer> {
}
