package nz.co.solnet.service.impl;

/**
 * Service layer implementation for CRUD operations for Tasks 
 *
 * @author Venkata Narendra
 */

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nz.co.solnet.model.Tasks;
import nz.co.solnet.repo.TasksRepository;
import nz.co.solnet.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	TasksRepository tasksRepository;

	@Override
	public List<Tasks> getAllTasks() {
		return tasksRepository.findAll();
	}

	@Override
	public List<Tasks> getAllOverdueTasks() {
		return tasksRepository.getAllOverdueTasks();
	}

	@Override
	public Tasks getTaskById(Long id) {
		Optional<Tasks> optTask = tasksRepository.findById(id);
		if (optTask.isPresent())
			return optTask.get();
		else
			return null;

	}

	@Override
	public Tasks addTask(Tasks task) {
		return tasksRepository.save(task);

	}

	@Override
	public Tasks modifyTask(Tasks task) {
		if (tasksRepository.existsById(task.getId())) {
			return tasksRepository.save(task);
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteTaskById(Long id) {
		if (tasksRepository.existsById(id)) {
			tasksRepository.deleteById(id);
			return true;
		} else {
			return false;
		}

	}

}
