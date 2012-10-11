package test.model.mapper;

import java.util.List;

import test.model.domain.User;

public interface UserMapper {

  List findUserList();
  
  void create(User user);
  
}