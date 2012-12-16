package postech.itce.team8;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import postech.itce.team8.model.domain.Doctor;
import postech.itce.team8.model.service.DoctorService;
import postech.itce.team8.util.AeSimpleMD5;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DoctorTest {

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	@Qualifier("dataSource")
	private BasicDataSource dataSource;
	

	@Before
	public void connectionTest() throws SQLException {
		
		dataSource.getConnection();
	}
	
//	@Test
//	//testFindDoctorList
//	public void testFindDoctorList(){
//		System.out.println("testFindDoctorList");
//		
//		//System.out.println(doctorService == null);
//		
//		List<Doctor> list = doctorService.findDoctorList();
//		Iterator<Doctor> iter = list.iterator();
//		
//		while (iter.hasNext()){
//			Doctor doctor = iter.next();
//			System.out.println(doctor.getId() + "\t" + doctor.getFullName() + "\t"
//					+ doctor.getUserName() + "\t" + doctor.getPassword());
//		}
//		
//		System.out.println("DONE !");
//	}
	
//	@Test
//	//testFindDoctorUserNameList
//	public void testFindDoctorUserNameList(){
//		System.out.println("testFindDoctorUserNameList");
//		
//		List<String> list = doctorService.findDoctorUserNameList();
//		Iterator<String> iter = list.iterator();
//		
//		while (iter.hasNext()){
//			System.out.println(iter.next());
//		}
//		
//		System.out.println("DONE !");
//	}
	
	
//	@Test
//	//testFindDoctorByUserName
//	public void testFindDoctorByUserName(){
//		System.out.println("testFindDoctorByUserName");
//		
//		Doctor doctor = doctorService.findDoctorByUserName("def");
//		System.out.println(doctor.getId() + "\t" + doctor.getFullName() + "\t"
//					+ doctor.getUserName() + "\t" + doctor.getPassword() + "\t" + doctor.getLastLoginId());
//		
//		System.out.println("DONE !");
//	}
	
	
//	@Test
//	//testIsDoctorExisted
//	public void testIsDoctorExisted(){
//		System.out.println("testIsDoctorExisted");
//		
//		System.out.println(doctorService.isDoctorExisted("hiep"));
//	}
//	
	
//	@Test
//	//testIsDoctorLoginExisted
//	public void testIsDoctorLoginExisted(){
//		System.out.println("testIsDoctorLoginExisted");
//		
//		Doctor doctor = new Doctor();
//		doctor.setUserName("hiepnh");
//		try {
//			doctor.setPassword(AeSimpleMD5.MD5("hiepnh"));
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(doctorService.isDoctorLoginExisted(doctor));
//	}
	
	
	
//	@Test
//	//testInsertDoctor
//	public void testInsertDoctor(){
//		System.out.println("testInsertDoctor");
//		
//		Doctor doctor = new Doctor("hiep ga", "hiep", "ga");
//		
//		doctorService.insertDoctor(doctor);
//		
//		System.out.println("DONE");
//		
//		
//	}
	
	
//	@Test
//	//testUpdateDoctor
//	public void testUpdateDoctor(){
//		System.out.println("testUpdateDoctor");
//		
//		Doctor doctor = new Doctor("abc", "def", "gih", 1);
//		
//		doctorService.updateDoctor(doctor);
//		
//		System.out.println("DONE");
//		
//	}
	
	/**
	 * @param args
	 */
	/*
	public static void main(String[] args) {
//		//Get the System Classloader
//        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
//
//        //Get the URLs
//        URL[] urls = ((URLClassLoader)sysClassLoader).getURLs();
//
//        for(int i=0; i< urls.length; i++)
//        {
//            System.out.println(urls[i].getFile());
//        }    
		
		//ApplicationContext context = new ClassPathXmlApplicationContext("./applicationContext.xml");
        
        ApplicationContext context = new FileSystemXmlApplicationContext("target\\classes\\postech\\itce\\team8\\applicationContext.xml");
		
		
		new DoctorTest().testFindDoctorList();

	}
	*/

}
