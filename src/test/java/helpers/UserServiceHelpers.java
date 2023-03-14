package helpers;

import PojoClass.CreateEmployeePojo;
import constants.EndPoints;
import constants.SourcePath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import test.util.Utils;

public class UserServiceHelpers {
public static String getBasrUri() {
	Utils ob= new Utils();
	ob.loadFile("applicationDataProperties");
	String baseuri=ob.getPropertyData("baseuri");
	System.out.println(baseuri);
	return baseuri;
	
}
public static Response getEmployees() {
	Response response =RestAssured
		     .given()
		     .when()
		        .get(EndPoints.GET_DATA);
	return response;
}
public static Response createData() {
	
	CreateEmployeePojo pojo= new CreateEmployeePojo();
	Utils ob=new Utils();
	ob.loadFile("applicationDataProperties");
	String empName=ob.getPropertyData("empName");
	int empAge=Integer.parseInt( ob.getPropertyData("empAge"));         
	
	int empSalary= Integer.parseInt( ob.getPropertyData("empSalary"));
	pojo.setEmployeeName(empName);
	
	pojo.setEmployeeAge(empAge);
	pojo.setEmployeeSalary(empSalary);
	pojo.setProfileImage("");
	Response response =RestAssured
	     .given()
	        .contentType(ContentType.JSON)
	        .body(pojo)
	     .when()
			  .post(EndPoints.CREATE_DATA); 
	
	return response;
	
}
public static Response deleteData(int id) {
	//String id1=
	Response response =RestAssured
	      .given()
	      .when()
	          .delete(EndPoints.DELETE_DATA+id);
	return response;
	
	
}
public static Response getEmployeeData() {
	Response response =RestAssured
		     .given()
		     .when()
		         .get(EndPoints.GET_EMP_DATA);
	return response;
	
}
public static Response getInvalidData() {
	Response response =RestAssured
		      .given()
		      .when()
		          .delete(EndPoints.DELETE_DATA+"0");
		return response;
}
public static void main(String[] args) {
	getBasrUri();
}
}
