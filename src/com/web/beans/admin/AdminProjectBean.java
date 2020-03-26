package com.web.beans.admin;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.database.entities.Project;
import com.database.entities.User;
import com.database.managers.JpaUtil;
import com.database.managers.ProjectManager;
import com.database.managers.UserManager;
import com.web.Session;
import com.web.containers.ProjectContainer;
import com.web.containers.UserContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class AdminProjectBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int lastMonths = 7;

	private ProjectContainer project = null;
	private String managerUsername;
	private String addUser;

	@PostConstruct
	public void init() {
		String id = Session.getInstance().getRequestMap().get("projectId");

		if (id == null)
			id = "" + Session.getInstance().getSessionAtribute("projectId");

		try {
			if (id != null) {
				Project p = ProjectManager.findById(Integer.parseInt(id));
				if (p != null) {
					project = new ProjectContainer(p);
					managerUsername = project.getManager().getUsername();
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Couldn't parse " + id);
			e.printStackTrace();
		}
	}

	public void saveDetails(ActionEvent event) {
		boolean valid = true;

		try {
			User manager = UserManager.getUserByUsername(managerUsername);
			if (project.getDeadline().before(project.getSubscriptionDeadline())) {
				valid = false;
				sendMessageToComponent(":saveForm:deadline", "Deadline must be after subscription deadline");
			}
			if (project.getMaxMembers() < project.getParticipants().size()) {
				valid = false;
				sendMessageToComponent(":saveForm:maxMembers",
						"Max members must be higher then the number of participants");
			}
			if (manager == null) {
				valid = false;
				sendMessageToComponent(":saveForm:mananger", "User doesn't exist");
			} else {
				project.getParticipants().remove(project.getManager());
				project.setManager(new UserContainer(manager));
				project.getParticipants().add(project.getManager());
			}

			if (valid) {
				ProjectManager.updateProject(project);
				sendMessageToComponent(":saveForm", "Project updated with success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Function that adds a User to the project
	 * 
	 * @param e
	 */
	public void addUsers() {
		User user = UserManager.getUserByUsername(addUser);

		if (project.getMaxMembers() > project.getParticipants().size() && user != null) {
			ProjectManager.addParticipant(user.getId(), project.getId());
			project = new ProjectContainer(project.getId());
		}
	}

	/**
	 * 
	 * Function that removes Users
	 * 
	 * @param e
	 */
	public void removeUsers(ActionEvent e) {
		int userId = (int) e.getComponent().getAttributes().get("removeUserId");

		ProjectManager.removeParticipant(userId, project.getId());
		project = new ProjectContainer(project.getId());
	}

	public double getProjectBudget() {
		return JpaUtil.executeQuery(
				"Select sum(m.quantity * m.price) from Project p join p.material m where p.id = " + project.getId(),
				Double.class).get(0);
	}

	public List<Long> getLastMonthsViews() {
		LocalDateTime later = LocalDateTime.now().minusMonths(lastMonths - 1);
		List<Long> months = new ArrayList<>();
		for (int i = 0; i < lastMonths; i++) {
			months.add(JpaUtil.executeQuery(
					"Select count(*) from Project p join p.views v where p.id = " + project.getId()
							+ " and YEAR(v.date) = " + later.getYear() + "and MONTH(v.date) = " + later.getMonthValue(),
					Long.class).get(0));
			later = later.plusMonths(1);
		}
		return months;
	}

	public List<Long> getLastMonthsLikes() {
		LocalDateTime later = LocalDateTime.now().minusMonths(lastMonths - 1);
		List<Long> months = new ArrayList<>();
		for (int i = 0; i < lastMonths; i++) {
			months.add(JpaUtil.executeQuery(
					"Select count(*) from Project p join p.likes v where p.id = " + project.getId()
							+ " and YEAR(v.date) = " + later.getYear() + "and MONTH(v.date) = " + later.getMonthValue(),
					Long.class).get(0));
			later = later.plusMonths(1);
		}
		return months;
	}

	/**
	 * Sends a message to component
	 * 
	 * @param componentId
	 * @param message
	 */
	private void sendMessageToComponent(String componentId, String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		context.addMessage(componentId, msg);
	}

}
