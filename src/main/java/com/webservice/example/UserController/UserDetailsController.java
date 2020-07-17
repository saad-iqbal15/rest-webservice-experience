package com.webservice.example.UserController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webservice.example.CustomException;
import com.webservice.example.ExcepHandler;
import com.webservice.example.UserService.UserDetailsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RefreshScope
@RequestMapping ("/userdetails")
@Api(value = "User controller", tags= "User data experience API")
public class UserDetailsController {
	
	@Autowired
	private UserDetailsService userService;
	
	@Autowired
	ExcepHandler excepHandler;
	
	@GetMapping(value = "/users", produces = "application/json")
	@ApiOperation(value = "Get details of all users")
	@ApiResponses(value= {
	@ApiResponse(code = 500, message = "Connection failure with facade server"),
	@ApiResponse(code = 404, message = "Data is not present"),
	@ApiResponse(code = 200, message = "OK")
	        }
			)
	public ResponseEntity<String> getUsers() throws JSONException {
		
		try {	
		ResponseEntity<String> userDetailsResponse= userService.getAllUsers();
	if (userDetailsResponse.getStatusCode()!=HttpStatus.OK) {
		throw new CustomException(userDetailsResponse.getBody(),
				userDetailsResponse.getStatusCode());
	}
	else {
		return new ResponseEntity<>(userDetailsResponse.getBody(), HttpStatus.OK);
	}
		}
		
	catch (CustomException ex) {
		return excepHandler.exceptionMessage(ex);
	}
		}
	
	@GetMapping(value = "/userById", produces = "application/json")
	@ApiOperation(value = "Get details of single user")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Connection failure with facade server"),
			@ApiResponse(code = 404, message = "User not found")    
			        }
					)
	@ApiImplicitParam(name = "id", dataType = "String",
                     required = true, 
		        	  defaultValue = "1"
		        	  )
	
    public ResponseEntity<String> getUserById(@RequestParam (value="id") Long id) throws JSONException {
		try {
		ResponseEntity<String> userDetailsResponse= userService.userById(id);
		if (userDetailsResponse.getStatusCode()!=HttpStatus.OK) {
			
				throw new CustomException(userDetailsResponse.getBody(),
						userDetailsResponse.getStatusCode());
			}
		else {
			return new ResponseEntity<>(userDetailsResponse.getBody().toString(),
					userDetailsResponse.getStatusCode());
		}
		
		}
		catch (CustomException ex) {
		
			return excepHandler.exceptionMessage(ex);
			}
		}
		
	
	
	@PostMapping(value = "/addUser", produces = "application/json")
	@ApiOperation(value = "Add user")
	@ApiResponses(value= {
			@ApiResponse(code = 500, message = "Connection failure with facade server"),
			@ApiResponse(code = 202, message = "User has been added"),
			@ApiResponse(code = 400, message = "Request body not accepted")
			        }
					)
	public ResponseEntity<String> addUser(@RequestBody User user) throws JSONException{
		
		try {
		ResponseEntity<String> userDetailsResponse= userService.postUser(user);
		if (userDetailsResponse.getStatusCode()!=HttpStatus.ACCEPTED) {
			throw new CustomException(userDetailsResponse.getBody(),
					userDetailsResponse.getStatusCode());	
		}
		else {
		
		return userDetailsResponse;
		}
		}
		catch (CustomException ex) {
			return excepHandler.exceptionMessage(ex);
		}
	}
	
	@PutMapping(value = "/updateUser/{id}", produces = "application/json")
	@ApiOperation(value = "Update existing user")
	@ApiResponses(value= {
			@ApiResponse(code = 400, message = "Request body not accepted"),
			@ApiResponse(code = 500, message = "Connection failure with facade server"),
			@ApiResponse(code = 200, message = "Success")
			
			        }
					)
	public ResponseEntity<String> updateUser(@RequestBody User userObject, 
			@ApiParam(name = "id", type = "String",
		    required = true, 
			  value = "Id of the user to be updated",
			  defaultValue = "1"
			  ) 
			@PathVariable Long id) throws JSONException{
	
		try {	
		ResponseEntity<String> userDetailsResponse= userService.modifyUser(userObject, id);
		if(userDetailsResponse.getStatusCode()!=HttpStatus.OK) {
			throw new CustomException(userDetailsResponse.getBody(),
					userDetailsResponse.getStatusCode());
		} 
		else {
			return userDetailsResponse;
		}
		}
		catch (CustomException ex) {
			return excepHandler.exceptionMessage(ex);
		}
		
	}
	
	@DeleteMapping(value = "/deleteUser/{id}", produces = "application/json")
	@ApiOperation(value = "Remove user")
	@ApiResponses(value= {
			@ApiResponse(code = 404, message = "User cannot be delete at the moment"),
			@ApiResponse(code = 500, message = "User cannot be delete at the moment"),
			@ApiResponse(code = 200, message = "Success")
			        }
					)
	
	public ResponseEntity<String> deleteUser(@ApiParam(name = "id", type = "String",
		    required = true, 
			  value = "Id of the user to be deleted",
			  defaultValue = "1"
			  )
			@PathVariable Long id) throws JSONException{
	
		try {	
		ResponseEntity<String> userResponse= userService.removeUser(id);
		if (userResponse.getStatusCode()!=HttpStatus.OK) {
			throw new CustomException(userResponse.getBody(),
					userResponse.getStatusCode());
		}
		else {
			return userResponse;
		}
		}
		catch (CustomException ex) {
			return excepHandler.exceptionMessage(ex);
		}
		
	}
}
