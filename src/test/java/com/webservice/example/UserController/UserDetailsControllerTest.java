package com.webservice.example.UserController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.webservice.example.UserService.UserDetailsService;


@RunWith(MockitoJUnitRunner.class)
public class UserDetailsControllerTest {

	@InjectMocks
	UserDetailsController userController;
	
	@Mock
	UserDetailsService userService;
	
	ResponseEntity<String> response_OK = new ResponseEntity<String>(HttpStatus.OK);
	
	@Test
	public void getUsers_test() throws JSONException {
		
		when (userService.getAllUsers()).thenReturn(response_OK);
		ResponseEntity<String> response = userController.getUsers();
	assertEquals(response_OK.getStatusCode(), response.getStatusCode());
	}
}
