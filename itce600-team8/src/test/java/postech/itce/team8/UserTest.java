package postech.itce.team8;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import postech.itce.team8.model.domain.User;
import postech.itce.team8.model.service.UserService;
import postech.itce.team8.util.AeSimpleMD5;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UserTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	@Qualifier("dataSource")
	private BasicDataSource dataSource;
	
	
	//
//	@Test
//	public void testMD5(){
//		System.out.println("testMD5");
//		try {
//			System.out.println(AeSimpleMD5.MD5("abc"));
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//	}
	
	
//	@Test
//	public void testIsUserExisted(){
//		System.out.println("testIsUserExisted");
//		
//		boolean result = userService.isUserExisted(new User("hiepnh", "hiepnh"));
//		
//		System.out.println("result="+result);
//	}
}
