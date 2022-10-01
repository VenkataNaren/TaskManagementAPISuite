package nz.co.solnet.tests;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nz.co.solnet.model.Tasks;
import nz.co.solnet.service.TaskService;
import org.hamcrest.Matchers;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

//41
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskManagementAPISuiteTest {

	Logger logger = LoggerFactory.getLogger(TaskManagementAPISuiteTest.class);

	@Test
	public void contextLoads() {
	}

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	TaskService taskService;

	private MockMvc mockMvc;

	@Test
	public void testTaskById_API() throws Exception {

		logger.info("test case - testTaskById started");

		Tasks task = new Tasks("title", "description", new java.sql.Date(Calendar.getInstance().getTime().getTime()),
				"status", new java.sql.Date(Calendar.getInstance().getTime().getTime()));

		Tasks _task = taskService.addTask(task);

		logger.info("Just saved id : " + _task.getId());

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		mockMvc.perform(get("/api/taskById/" + _task.getId())).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.title").value("title"))
				.andExpect(jsonPath("$.description").value("description"))
				.andExpect(jsonPath("$.status").value("status"));

	}

	@Test
	public void testModifyTask_API() throws Exception {

		logger.info("test case - testModifyTask_API started");

		Tasks task = new Tasks("title", "description", new java.sql.Date(Calendar.getInstance().getTime().getTime()),
				"status", new java.sql.Date(Calendar.getInstance().getTime().getTime()));

		Tasks _task = taskService.addTask(task);

		_task.setTitle("Mock-title");

		_task.setDescription("Mock-description");

		_task.setStatus("Mock-status");

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();

		String taskJson = gson.toJson(_task);

		logger.info(taskJson);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		mockMvc.perform(put("/api/modifyTask").contentType("application/json").content(taskJson))
				.andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Mock-title"))
				.andExpect(jsonPath("$.description").value("Mock-description"))
				.andExpect(jsonPath("$.status").value("Mock-status"));

	}

	@Test
	public void testAddTask_API() throws Exception {

		logger.info("test case - testAddTask_API started");

		Tasks task = new Tasks("title", "description", new java.sql.Date(Calendar.getInstance().getTime().getTime()),
				"status", new java.sql.Date(Calendar.getInstance().getTime().getTime()));

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();

		String taskJson = gson.toJson(task);

		logger.info(taskJson);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		mockMvc.perform(post("/api/addTask").contentType("application/json").content(taskJson))
				.andExpect(status().isOk()).andExpect(jsonPath("$.title").value("title"))
				.andExpect(jsonPath("$.description").value("description"))
				.andExpect(jsonPath("$.status").value("status"));

	}
	
	@Test
	public void testGetAllTask_API() throws Exception {

		logger.info("test case - testGetAllTask_API started");

		Tasks task_1 = new Tasks("title_1", "description_1", new java.sql.Date(Calendar.getInstance().getTime().getTime()),
				"status_1", new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		
		taskService.addTask(task_1);
		
		int allTaskSize = taskService.getAllTasks().size();
		
		Tasks _task = taskService.getAllTasks().get(0);


		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		mockMvc
	    .perform(MockMvcRequestBuilders.get("/api/allTasks"))
	    .andExpect(MockMvcResultMatchers.status().isOk())
	    .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(allTaskSize))
	    .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(_task.getTitle()))
	    .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(_task.getDescription()))
	    .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value(_task.getStatus()));

	}
	
	@Test
	public void testGetAllOverdueTask_API() throws Exception {

		logger.info("test case - testGetAllOverdueTask_API started");

		Tasks task_1 = new Tasks("title_1", "description_1", new java.sql.Date(Calendar.getInstance().getTime().getTime()),
				"status_1", new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		
		taskService.addTask(task_1);
		
		int allTaskSize = taskService.getAllOverdueTasks().size();
		
		Tasks _task = taskService.getAllOverdueTasks().get(0);


		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		mockMvc
	    .perform(MockMvcRequestBuilders.get("/api/allOverdueTasks"))
	    .andExpect(MockMvcResultMatchers.status().isOk())
	    .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(allTaskSize))
	    .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(_task.getTitle()))
	    .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(_task.getDescription()))
	    .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value(_task.getStatus()));

	}
	
	@Test
	public void testDeleteTask_API() throws Exception {

		logger.info("test case - testDeleteTask_API started");

		Tasks task_1 = new Tasks("title_1", "description_1", new java.sql.Date(Calendar.getInstance().getTime().getTime()),
				"status_1", new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		
		Tasks _task = taskService.addTask(task_1);
		

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		mockMvc
	    .perform(MockMvcRequestBuilders.delete("/api/deleteById/"+_task.getId()))
	    .andExpect(MockMvcResultMatchers.status().isOk());

	}

}
