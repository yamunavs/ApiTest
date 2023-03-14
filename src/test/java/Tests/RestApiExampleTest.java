package Tests;

import static org.testng.Assert.assertEquals;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import helpers.UserServiceHelpers;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import test.util.Utils;

public class RestApiExampleTest {
	int id;
	Utils ob;
	
	@BeforeMethod
	public void init() {
		RestAssured.baseURI = UserServiceHelpers.getBasrUri();
	}
	@Test(priority = 0)
	public void getEmployees() {
		Response response = UserServiceHelpers.getEmployees();
		response.prettyPrint();
		
		
		//EmployeeDataPojo[] data=response.as(EmployeeDataPojo[].class);
		
		//System.out.println("The Number of Data = "+data.length);
		
		 response.then().statusCode(200);
		 response.then().body("status",Matchers.is("success"));
	     int noOfRecords = response.body().jsonPath().getInt("data.size()");
	     System.out.println("The number of records is "+noOfRecords);

		
		        
	}
	@Test(priority = 1)
	public void createEmployeeData() {
		
		ob=new Utils();
		ob.loadFile("applicationDataProperties");
		String empName=ob.getPropertyData("empName");
		int empAge=Integer.parseInt( ob.getPropertyData("empAge"));         
		
		int empSalary= Integer.parseInt( ob.getPropertyData("empSalary"));
		
		Response response=UserServiceHelpers.createData();
		response.prettyPrint();
		response.then().statusCode(200);
		response.then().body("status",Matchers.is("success"));
		String empName1=response.body().jsonPath().getString("data.employee_name");
		int salary=response.body().jsonPath().getInt("data.employee_salary");
		int age=response.body().jsonPath().getInt("data.employee_age");
		Assert.assertEquals(empName1, empName);
		//assertEquals(empName, "yj1");
		assertEquals(salary, empSalary);
		assertEquals(age, empAge);
	    id=response.body().jsonPath().getInt("data.id");
		System.out.println(id);
	}
	@Test(priority = 2)
	public void DeleteEmployeeData() {
		
		Response response=UserServiceHelpers.deleteData(id);
		response.prettyPrint();
		response.then().statusCode(200);
		response.then().body("status",Matchers.is("success"));
		String deletedId=response.body().jsonPath().getString("data");
		assertEquals(id, deletedId);
		
	}
	@Test(priority = 3)
	public void GetEmployeeData() {

		Response response=UserServiceHelpers.getEmployeeData();
		response.prettyPrint();
		response.then().statusCode(200);
		response.then().contentType(ContentType.JSON);
		String empName=response.body().jsonPath().getString("data.employee_name");
		assertEquals(empName, "Garrett Winters");
		int empSalary=response.body().jsonPath().getInt("data.employee_salary"); 
		assertEquals(empSalary, 170750);
		int empAge=response.body().jsonPath().getInt("data.employee_age");
		assertEquals(empAge, 63);
		
		response.then().body("status",Matchers.is("success"));
		
	}
	@Test
	public void deleteInvalidData() {
		Response response= UserServiceHelpers.getInvalidData();
		response.prettyPrint();
		response.then().statusCode(400);
		response.then().body("status",Matchers.is("error"));
		String message=response.body().jsonPath().getString("message");
		System.out.println(message);
		
	}
}
