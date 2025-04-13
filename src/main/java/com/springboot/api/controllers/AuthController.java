package com.springboot.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.api.beans.UserBean;
import com.springboot.api.entities.User;
import com.springboot.api.jpas.UserJPA;
import com.springboot.api.response.LoginRes;
import com.springboot.api.response.Response;
import com.springboot.api.response.UserRes;
import com.springboot.api.services.ImageServices;
import com.springboot.api.services.ParseError;
import com.springboot.api.utils.JwtUtil;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	UserJPA userJPA;

	@Autowired
	ParseError parseError;

	@Autowired
	ImageServices imageServices;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/users")
	public ResponseEntity<Response> getAllUsers(){
		Response res = new Response();
		try{
			List<User> entityUserList = userJPA.findAll();

			List<UserRes> userRes = new ArrayList<>();

			for(User user : entityUserList){
				UserRes userResEntity = new UserRes();
				userResEntity.setId(user.getId());
				userResEntity.setUsername(user.getUsername());
				userResEntity.setName(user.getName());
				userResEntity.setGender(user.getGender());
				userResEntity.setAvatar(user.getAvatar() != null ? String.format("images/%s", user.getAvatar()) : "");
				userResEntity.setRole(user.getRole());

				userRes.add(userResEntity);
			}

			res.setData(userRes);
			res.setStatus(1);
			res.setMessage("Success!");
			return ResponseEntity.ok().body(res);

		}catch(Exception e){
			res.setData(null);
			res.setStatus(0);
			res.setMessage("Error!");
			return ResponseEntity.badRequest().body(res);
		}
	}

	@PostMapping("/add-user")
	public ResponseEntity<Response> addUser(@Valid UserBean userBean, Errors errors){
		Response res = new Response();
		try{
			List<String> fields = new ArrayList<>();
			fields.add("username");
			fields.add("password");
			fields.add("name");

			parseError.parseError(errors, fields);

			User user = new User();
			user.setUsername(userBean.getUsername());
			String encodedPassword = passwordEncoder.encode(userBean.getPassword());
      user.setPassword(encodedPassword);
			user.setName(userBean.getName());
			user.setGender(userBean.getGender());
			user.setRole(1);

			String fileName = imageServices.saveImage(userBean.getAvatar());

			user.setAvatar(fileName);

			userJPA.save(user);

			res.setData(null);
			res.setStatus(1);
			res.setMessage("Success!");

			return ResponseEntity.status(HttpStatus.CREATED).body(res);

		}catch(Exception e){
			res.setData(null);
			res.setStatus(0);
			res.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}

	@PutMapping("/update-user")
	public ResponseEntity<Response> updateUser(@Valid UserBean userBean, Errors errors){
		Response res = new Response();
		try{
			List<String> fields = new ArrayList<>();
			fields.add("username");
			fields.add("password");
			fields.add("name");

			parseError.parseError(errors, fields);

			User user = new User();
			user.setId(userBean.getId().isPresent() ? userBean.getId().get() : null);
			user.setUsername(userBean.getUsername());

			String encodedPassword = passwordEncoder.encode(userBean.getPassword());
      user.setPassword(encodedPassword);

			user.setName(userBean.getName());
			user.setGender(userBean.getGender());
			user.setRole(1);

			String fileName = imageServices.saveImage(userBean.getAvatar());

			if(fileName != null){
				user.setAvatar(fileName);
			}

			userJPA.save(user);

			res.setData(null);
			res.setStatus(1);
			res.setMessage("Success!");

			return ResponseEntity.status(HttpStatus.OK).body(res);

		}catch(Exception e){
			res.setData(null);
			res.setStatus(0);
			res.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}

	@DeleteMapping("/delete-user")
	public ResponseEntity<Response> updateUser(@RequestParam int id){
		Response res = new Response();
		try{
			userJPA.deleteById(id);
			res.setData(null);
			res.setStatus(1);
			res.setMessage("Success!");

			return ResponseEntity.status(HttpStatus.OK).body(res);
		}catch(Exception e){
			res.setData(null);
			res.setStatus(0);
			res.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}

	@GetMapping("/user")
	public ResponseEntity<Response> getUsers(@RequestParam int id){
		Response res = new Response();
		try{
			Optional<User> userOptional = userJPA.findById(id);

			if(userOptional.isPresent()){
				User user = userOptional.get();
				UserRes userResEntity = new UserRes();
				userResEntity.setId(user.getId());
				userResEntity.setUsername(user.getUsername());
				userResEntity.setName(user.getName());
				userResEntity.setGender(user.getGender());
				userResEntity.setAvatar(user.getAvatar() != null ? String.format("images/%s", user.getAvatar()) : "");
				userResEntity.setRole(user.getRole());

				res.setData(userResEntity);
				res.setStatus(1);
				res.setMessage("Success!");
				return ResponseEntity.ok().body(res);
			}

			throw new Exception();

		}catch(Exception e){
			res.setData(null);
			res.setStatus(0);
			res.setMessage("Error!");
			return ResponseEntity.badRequest().body(res);
		}
	}


	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestParam String username, @RequestParam String password) {
    Response res = new Response();
    try {
			System.out.println("Login attempt with username: " + username);
			System.out.println("Login attempt with password: " + password);
			
			// Authenticate the user
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password)
			);

			// Generate JWT token
			String role = authentication.getAuthorities().iterator().next().getAuthority();
			String token = jwtUtil.generateToken(username, role);

			Optional<User> userOptional = userJPA.findByUsername(username);

			LoginRes loginRes = new LoginRes();
			if(userOptional.isPresent()){
				User user = userOptional.get();
				loginRes.setId(user.getId());
				loginRes.setRole(user.getRole());
			}
			loginRes.setToken(token);

			// Prepare response
			res.setData(loginRes);
			res.setStatus(1);
			res.setMessage("Login successful!");
			return ResponseEntity.ok().body(res);

    } catch (Exception e) {
			e.printStackTrace();
			res.setData(null);
			res.setStatus(0);
			res.setMessage("Invalid username or password!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }
}
}
