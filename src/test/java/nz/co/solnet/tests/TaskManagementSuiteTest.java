package nz.co.solnet.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import nz.co.solnet.helper.DatabaseHelper;
import nz.co.solnet.model.Tasks;
import nz.co.solnet.service.TaskService;

//41
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ComponentScan("nz.co.solnet")
public class TaskManagementSuiteTest {

	Logger logger = LoggerFactory.getLogger(TaskManagementSuiteTest.class);

	@Autowired
	TaskService taskService;

	@Test
	public void contextLoads() {
	}

	/**
	 * This method gets invoked when the servlet context is initialised.
	 */
	@Before
	public void setup() throws Exception {

		DatabaseHelper.initialiseDB();
		logger.info("DB initialised successfully");
	}

	/**
	 * This method gets invoked when the servlet context is destroyed.
	 */
	@After
	public void tearDown() throws Exception {

		DatabaseHelper.cleanupDB();
		logger.info("DB shutdown successfully");
	}

	@Test
	public void should_find_tasks_if_repo_is_not_empty() {
		logger.info("test case - should_find_tasks_if_repo_is_not_empty started");
		Iterable<Tasks> itrTasks = taskService.getAllTasks();

		assertThat(itrTasks).isNotEmpty();
	}

	@Test
	public void should_add_a_task() {
		logger.info("test case - should_add_a_task started");
		Tasks task = taskService.addTask(
				new Tasks("title", "description", new java.sql.Date(Calendar.getInstance().getTime().getTime()),
						"status", new java.sql.Date(Calendar.getInstance().getTime().getTime())));

		assertThat(task).hasFieldOrPropertyWithValue("title", "title");
		assertThat(task).hasFieldOrPropertyWithValue("description", "description");
		assertThat(task).hasFieldOrPropertyWithValue("status", "status");
	}

	@Test
	public void should_find_task_by_id() {
		logger.info("test case - should_find_task_by_id started");
		Tasks task_1 = new Tasks("title-1", "description-1",
				new java.sql.Date(Calendar.getInstance().getTime().getTime()), "status-1",
				new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		taskService.addTask(task_1);

		Tasks task_2 = new Tasks("title-2", "description-2",
				new java.sql.Date(Calendar.getInstance().getTime().getTime()), "status-2",
				new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		taskService.addTask(task_2);

		Tasks _task = taskService.getTaskById(task_2.getId());

		assertThat(_task.getTitle()).isEqualTo(task_2.getTitle());
		assertThat(_task.getTitle()).isNotEqualTo(task_1.getTitle());
	}

	@Test
	public void should_find_count_overdue_task() {
		logger.info("test case - should_find_count_overdue_task started");

		List<Tasks> listTasks = taskService.getAllOverdueTasks();

		Tasks task_1 = new Tasks("title-1", "description-1",
				new java.sql.Date(Calendar.getInstance().getTime().getTime()-1), "status-1",
				new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		taskService.addTask(task_1);

		Tasks task_2 = new Tasks("title-2", "description-2",
				new java.sql.Date(Calendar.getInstance().getTime().getTime()-1), "status-2",
				new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		taskService.addTask(task_2);

		Tasks task_3 = new Tasks("title-3", "description-3",
				new java.sql.Date(Calendar.getInstance().getTime().getTime()-1), "status-3",
				new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		taskService.addTask(task_3);

		assertThat(listTasks).hasSize(3);

	}

	@Test
	public void should_modify_a_task_by_id() {
		logger.info("test case - should_modify_a_task_by_id started");
		Tasks task = taskService.addTask(
				new Tasks("title", "description", new java.sql.Date(Calendar.getInstance().getTime().getTime()),
						"status", new java.sql.Date(Calendar.getInstance().getTime().getTime())));

		Tasks modifiedTask = taskService.modifyTask(new Tasks(task.getId(), "modifiedTitle", "modifiedDescription",
				new java.sql.Date(Calendar.getInstance().getTime().getTime()), "modifiedStatus",
				new java.sql.Date(Calendar.getInstance().getTime().getTime())));
		
		assertThat(modifiedTask).hasFieldOrPropertyWithValue("title", "modifiedTitle");
		assertThat(modifiedTask).hasFieldOrPropertyWithValue("description", "modifiedDescription");
		assertThat(modifiedTask).hasFieldOrPropertyWithValue("status", "modifiedStatus");
	}

	@Test
	public void should_delete_a_task_by_id() {

		logger.info("test case - should_delete_a_task_by_id started");

		Tasks task = taskService.addTask(
				new Tasks("title", "description", new java.sql.Date(Calendar.getInstance().getTime().getTime()),
						"status", new java.sql.Date(Calendar.getInstance().getTime().getTime())));

		Boolean deleted = taskService.deleteTaskById(task.getId());

		Boolean alreadyDeleted = taskService.deleteTaskById(task.getId());

		assertTrue(deleted);

		assertFalse(alreadyDeleted);

	}

}
