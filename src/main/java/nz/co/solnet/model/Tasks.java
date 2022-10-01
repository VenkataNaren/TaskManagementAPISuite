package nz.co.solnet.model;

/**
 * Entity bean for Task 
 *
 * @author Venkata Narendra
 */

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.google.gson.annotations.Expose;

@Entity
public class Tasks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false, length = 256)
	private String title;

	@Column(name = "description", nullable = true, length = 1024)
	private String description;

	@Expose(serialize = false, deserialize = false)
	@Column(name = "due_date", nullable = true)
	private Date dueDate;

	@Column(name = "status", nullable = true, length = 10)
	private String status;

	@Expose(serialize = false, deserialize = false)
	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	public Tasks() {
		super();
	}

	public Tasks(String title, String description, Date dueDate, String status, Date creationDate) {
		super();
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.status = status;
		this.creationDate = creationDate;
	}

	public Tasks(Long id, String title, String description, Date dueDate, String status, Date creationDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.status = status;
		this.creationDate = creationDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "Tasks [id=" + getId() + ", title=" + title + ", description=" + description + ", dueDate=" + dueDate
				+ ", status=" + status + ", creationDate=" + creationDate + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
