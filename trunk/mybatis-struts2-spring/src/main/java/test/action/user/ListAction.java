package test.action.user;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import test.model.domain.User;
import test.model.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
public class ListAction extends ActionSupport {

	Logger logger = Logger.getLogger(ListAction.class);

	@Autowired
	private UserService userService;

	private List userList;

	public List getUserList() {
		return userList;
	}

	public String execute() throws Exception {

		this.userList = userService.findUserList();

		return SUCCESS;
	}

}