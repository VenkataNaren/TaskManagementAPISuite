package nz.co.solnet.service;

/**
 * Service layer for CRUD operations for Tasks 
 *
 * @author Venkata Narendra
 */

import java.util.List;

import nz.co.solnet.model.Tasks;

public interface TaskService {

	public List<Tasks> getAllTasks();

	public List<Tasks> getAllOverdueTasks();

	public Tasks getTaskById(Long id);

	public Tasks addTask(Tasks task);

	public Tasks modifyTask(Tasks task);

	public boolean deleteTaskById(Long id);

}
