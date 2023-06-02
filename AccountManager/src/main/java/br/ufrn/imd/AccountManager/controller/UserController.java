package br.ufrn.imd.AccountManager.controller;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.AccountManager.dto.Login;
import br.ufrn.imd.AccountManager.exception.UserNotFoundException;
import br.ufrn.imd.AccountManager.model.User;
import br.ufrn.imd.AccountManager.repository.UserRepository;
//import br.ufrn.imd.AccountManager.repository.UserRepository;
import br.ufrn.imd.AccountManager.service.TokenService;

@RestController
public class UserController {
	
	private final UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired 
	private TokenService tokenService;
	
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;

	}
	
	@PostMapping("/login")
	public String login(@RequestBody Login login){
		UsernamePasswordAuthenticationToken  usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken(login.login(), login.password());
		Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		
		var user = (User) authenticate.getPrincipal();
		
		return tokenService.gerarToken(user);
	
	}
	
	
	

//	@GetMapping("/listartodos")
//	public ResponseEntity<List<User>> getAllUsers() {
//		return ResponseEntity.ok(userRepository.findAll());
//	}
//
//	@GetMapping("/{id}")
//	public User getUserById(@PathVariable String id) {
//		return userRepository.findById(id)
//				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
//	}
//
//	@GetMapping("/validatePass")
//	public ResponseEntity<Boolean> validatePass(@RequestParam String login, @RequestParam String password) {
//		Optional<User> optUser = userRepository.findByLogin(login);
//		if (optUser.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
//		}
//		User user = optUser.get();
//		return userService.validatePass(user, password);
//	}
//
//	@PostMapping("/createuser")
//	public User createUser(@RequestBody User user) {
//		return userService.createUser(user);
//	}
	
	
	

//	@PutMapping("/update/{id}")
//	public User updateUser(@PathVariable String id, @RequestBody User updatedUser) {
//		return userService.updateUser(id, updatedUser);
//	}
//
//	@DeleteMapping("/delete/{id}")
//	public void deleteUser(@PathVariable String id) {
//		userRepository.deleteById(id);
//	}
	
}
