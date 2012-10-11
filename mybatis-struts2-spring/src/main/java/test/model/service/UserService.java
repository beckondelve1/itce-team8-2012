package test.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import test.model.domain.User;
import test.model.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public List findUserList() {

		return userMapper.findUserList();

	}

	@Transactional(rollbackFor = java.lang.Exception.class)
	public void create(User user) {
		userMapper.create(user);
	}

}