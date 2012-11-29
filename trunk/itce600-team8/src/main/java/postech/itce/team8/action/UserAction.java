package postech.itce.team8.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import postech.itce.team8.model.domain.User;
import postech.itce.team8.model.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	Logger logger = Logger.getLogger(UserAction.class);

	@Autowired
	private UserService userService;

	// bean(s)
	private User user;

	// getters & setters
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// actions
	public String login() {
		if (user == null)
			return ERROR;

		if (userService.isUserExisted(user)) {
			System.out.println("LOGIN : SUCCESS");

			return SUCCESS;
		} else {
			System.out.println("LOGIN : FAILED");
			return ERROR;
		}
	}
}
