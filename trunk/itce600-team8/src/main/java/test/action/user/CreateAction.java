package test.action.user;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import test.model.domain.User;
import test.model.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
public class CreateAction extends ActionSupport {

	Logger logger = Logger.getLogger(CreateAction.class);

	@Autowired
	private UserService userService;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String execute() {

		userService.create(user);

		return SUCCESS;
	}
}