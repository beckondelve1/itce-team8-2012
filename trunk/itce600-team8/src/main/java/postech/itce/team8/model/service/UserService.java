package postech.itce.team8.model.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import postech.itce.team8.model.domain.User;
import postech.itce.team8.model.mapper.UserMapper;
import postech.itce.team8.util.AeSimpleMD5;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	//
	public boolean isUserExisted(User user){
		String hashedPassword = "";
		
		try {
			hashedPassword = AeSimpleMD5.MD5(user.getPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return userMapper.isUserExisted(new User(user.getUserName(), hashedPassword)) > 0;
	}
}
