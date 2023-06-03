package br.ufrn.imd.AccountManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.ufrn.imd.AccountManager.config.Views;
import br.ufrn.imd.AccountManager.dto.Login;
import br.ufrn.imd.AccountManager.exception.UserNotFoundException;
import br.ufrn.imd.AccountManager.model.User;
import br.ufrn.imd.AccountManager.repository.UserRepository;
import br.ufrn.imd.AccountManager.security.TokenService;
import br.ufrn.imd.AccountManager.service.UserService;

@RestController
public class UserController {

	private final UserRepository userRepository;
	private final UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	public UserController(UserRepository userRepository, UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;

	}

	@PostMapping("/login")
	public String login(@RequestBody Login login) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				login.login(), login.password());
		Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		var user = (User) authenticate.getPrincipal();

		return tokenService.gerarToken(user);

	}

	// ListarTodos apenas se autenticado
	@GetMapping("/users")
	@JsonView(Views.Admin.class)
	public List<User> getUsers() {
		return (List<User>) userRepository.findAll();
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable String id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
	}

	@PostMapping("/register")
	@JsonView(Views.Public.class)
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@PutMapping("/update/{id}")
	public User updateUser(@PathVariable String id, @RequestBody User updatedUser) {
		return userService.updateUser(id, updatedUser);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable String id) {
		userRepository.deleteById(id);
	}

}
