package nz.co.solnet.repo;

/**
 * JPA Repository for CRUD operations for Tasks 
 *
 * @author Venkata Narendra
 */

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import nz.co.solnet.model.Tasks;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {

	@Query("from Tasks t where t.dueDate < CURRENT_TIMESTAMP")
	List<Tasks> getAllOverdueTasks();

}
