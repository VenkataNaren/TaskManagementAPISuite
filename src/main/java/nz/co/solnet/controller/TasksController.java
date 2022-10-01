package nz.co.solnet.controller;

/**
 * REST Controller which serves as an entry-point for requests for Task Management API Suite
 *
 * @author Venkata Narendra
 */

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nz.co.solnet.model.Tasks;
import nz.co.solnet.service.TaskService;

@RestController
@RequestMapping("/api")
public class TasksController {

	Logger logger = LoggerFactory.getLogger(TasksController.class);

	@Autowired
	TaskService taskService;

	/**
	 * API Operation that returns all All the Tasks stored in Db
	 *
	 */
	@Operation(summary = "This is to fetch All the Tasks stored in Db")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Details of All the Tasks", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Page not found", content = @Content) })
	@RequestMapping(value = "/allTasks", method = RequestMethod.GET)
	public ResponseEntity<List<Tasks>> getAllTasks() {
		logger.info("Getting all tasks...");
		try {
			List<Tasks> _tasks = new ArrayList<Tasks>();

			_tasks = taskService.getAllTasks();

			if (_tasks.isEmpty()) {
				return new ResponseEntity<List<Tasks>>(_tasks, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Tasks>>(_tasks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * API Operation that returns all All the Overdue Tasks stored in Db
	 *
	 */
	@Operation(summary = "This is to fetch All the Overdue Tasks stored in Db")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Details of All the Overdue Tasks", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Page not found", content = @Content) })
	@RequestMapping(value = "/allOverdueTasks", method = RequestMethod.GET)
	public ResponseEntity<List<Tasks>> getAllOverdueTasks() {
		logger.info("Getting all overdue tasks...");
		try {
			List<Tasks> _tasks = new ArrayList<Tasks>();

			_tasks = taskService.getAllOverdueTasks();

			if (_tasks.isEmpty()) {
				return new ResponseEntity<List<Tasks>>(_tasks, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Tasks>>(_tasks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * API Operation that stores task record in db
	 *
	 */
	@Operation(summary = "This is to add  the Tasks in the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = " Task details saved in database", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = " Page Not Found", content = @Content) })
	@RequestMapping(value = "/addTask", method = RequestMethod.POST)
	public ResponseEntity<Tasks> addTask(@RequestBody Tasks _task) {
		logger.info("Adding new task...");
		try {

			Tasks __tasks = taskService.addTask(_task);

			return new ResponseEntity<Tasks>(__tasks, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * API Operation that returns Task whose id is supplied
	 *
	 */
	@Operation(summary = "This is to get  the details of particular  Task in the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = " Task details fetched from database", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = " Page Not Found", content = @Content) })
	@RequestMapping(value = "/taskById/{id}", method = RequestMethod.GET)
	public ResponseEntity<Tasks> getTaskById(@PathVariable("id") Long id) {
		logger.info("get task by id... " + id);
		try {

			Tasks task = taskService.getTaskById(id);
			if (task == null) {
				return new ResponseEntity<Tasks>(task, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<Tasks>(task, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * API Operation that update the Task in the database whose id is supplied
	 *
	 */
	@Operation(summary = "This is to update  the Task in the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = " Task details updated in database", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = " Page Not Found", content = @Content) })
	@RequestMapping(value = "/modifyTask", method = RequestMethod.PUT)
	public ResponseEntity<Tasks> modifyTask(@RequestBody Tasks _task) {
		logger.info("modify task...");
		try {

			_task = taskService.modifyTask(_task);
			if (_task == null) {
				return new ResponseEntity<Tasks>(_task, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<Tasks>(_task, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * API Operation that delete the Task in the database whose id is supplied
	 *
	 */
	@Operation(summary = "This is to delete  the Task in the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = " Task deleted from the database", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = " Page Not Found", content = @Content) })
	@RequestMapping(value = "/deleteById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
		logger.info("delete task by id... " + id);
		try {

			boolean task = taskService.deleteTaskById(id);
			if (task) {
				return new ResponseEntity<Boolean>(task, HttpStatus.OK);
			} else {
				return new ResponseEntity<Boolean>(task, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
