package br.ufrn.imd.AccountManager.controller;

import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.AccountManager.exception.UserNotFoundException;
import br.ufrn.imd.AccountManager.model.User;
import br.ufrn.imd.AccountManager.repository.UserRepository;
import br.ufrn.imd.AccountManager.service.UserService;

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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserRepository userRepository;
	private final UserService userService;

	public UserController(UserRepository UserRepository, UserService UserService) {
		this.userRepository = UserRepository;
		this.userService = UserService;
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userRepository.findAll());
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable String id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
	}

	@GetMapping("/validatePass")
	public ResponseEntity<Boolean> validatePass(@RequestParam String login, @RequestParam String password) {
		Optional<User> optUser = userRepository.findByLogin(login);
		if (optUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}
		User user = optUser.get();
		return userService.validatePass(user, password);
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable String id, @RequestBody User updatedUser) {
		return userService.updateUser(id, updatedUser);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable String id) {
		userRepository.deleteById(id);
	}
}
