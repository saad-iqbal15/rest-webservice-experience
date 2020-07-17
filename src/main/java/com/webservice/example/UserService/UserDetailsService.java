package com.webservice.example.UserService;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.webservice.example.UserController.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;


@Service
public class UserDetailsService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${user.urlAll}")
	String AllUsersUrl;
	
	@Value("${user.urlById}")
	String getUserUrl;
	
	@Value("${user.postUrl}")
	String postUserUrl;
	
	@Value("${user.updateUrl}")
	String updateUserUrl;
	
	@Value("${user.removeUrl}")
	String removeUserUrl;
	
	@Value("${user.errorMessage.internal_server_error}")
	String errorMssg_internalServer;
	
	@Value("${getAllUsers.errorMessage.notFound}")
	String allUsersErrorMssg_NotFound;
	
	@Value("${userById.errorMessage.notFound}")
	String userByIdErrorMssg_NotFound;	
	
	@Value("${postUser.errorMessage.badRequest}")
	String postUserErrorMssg_badRequest;
		
	
	@Value("${modifyUser.errorMessage.badRequest}")
	String modifyUserErrorMssg_badRequest;
	
	@Value("${removeUser.errorMessage.notFound}")
	String removeUserErrorMssg_notFound;
	
	public ResponseEntity<String> getAllUsers () {
		
		JSONObject jsonObj = new JSONObject();
		
		try{
		
			
			HttpHeaders headers = new HttpHeaders();
			HttpEntity <String> entity = new HttpEntity<String> (headers);
		ResponseEntity<String> response = restTemplate.exchange(AllUsersUrl, HttpMethod.GET, entity, String.class);
			//String responseString = restTemplate.getForObject(AllUsersUrl, String.class);
		
				jsonObj.put("Message", response.getBody()); 
				return new ResponseEntity<>(response.getBody(), response.getStatusCode());
		
		}
		catch (Exception ex) {
			if (ex.getMessage().contains("NOT_FOUND")) {
				return new ResponseEntity<>(allUsersErrorMssg_NotFound, HttpStatus.NOT_FOUND);
			}
			else {
			return new ResponseEntity<>(errorMssg_internalServer, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			}
			
		} 
			
public ResponseEntity<String> userById (Long value) {
		
	try {
		String url = getUserUrl +value;
				
		HttpHeaders headers = new HttpHeaders();
		HttpEntity <String> entity = new HttpEntity<String> (headers);
			ResponseEntity<String> response= restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			
			return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
			
		}
		catch (Exception ex) {
			if (ex.getMessage().contains("NOT_FOUND")) {
			return new ResponseEntity<>(userByIdErrorMssg_NotFound, HttpStatus.NOT_FOUND);
			}
			else {
				return new ResponseEntity<>(errorMssg_internalServer, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		} 

public ResponseEntity<String> postUser (User requestBody) {
	
	try {
		
	String url = postUserUrl;
	ResponseEntity<String> response = restTemplate.postForEntity(url, requestBody, String.class);
	return response;
	}
	catch (Exception ex) {
		if (ex.getMessage().contains("BAD_REQUEST")) {
		return new ResponseEntity<>(postUserErrorMssg_badRequest, HttpStatus.BAD_REQUEST);
		}
		else {
			
		return new ResponseEntity<>(errorMssg_internalServer, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
		 	
	} 

public ResponseEntity<String> modifyUser (User request , Long id) {
	try {
	String url = updateUserUrl + id;
	HttpHeaders headers = new HttpHeaders();
	HttpEntity <Object> entity = new HttpEntity<Object> (request, headers);
		ResponseEntity<String> response= restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
		
		 return response;
	}
	catch (Exception ex) {
		if (ex.getMessage().contains("BAD_REQUEST")) {
			return new ResponseEntity<>(modifyUserErrorMssg_badRequest, HttpStatus.BAD_REQUEST);
			}
			else {
				return new ResponseEntity<>(errorMssg_internalServer, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
}

public ResponseEntity<String> removeUser (Long id) {
	
	
	try {
		
	String url = removeUserUrl + id;
	HttpHeaders headers = new HttpHeaders();
	HttpEntity <Object> entity = new HttpEntity<Object> (headers);
		ResponseEntity<String> response= restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
		JSONObject obj = new JSONObject();
		obj.put("Message", "User deleted successfully");
		 return new ResponseEntity<>(obj.toString(), response.getStatusCode());
		
	}
	     catch(Exception ex) {
	    	 if (ex.getMessage().contains("NOT_FOUND")) {
					return new ResponseEntity<>(removeUserErrorMssg_notFound, HttpStatus.NOT_FOUND);
				}
	    	 else {
				return new ResponseEntity<>(errorMssg_internalServer, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	     }
	
	     }
		
}
		