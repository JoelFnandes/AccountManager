package br.ufrn.imd.AccountManager.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.ufrn.imd.AccountManager.data.DetailsUserData;
import br.ufrn.imd.AccountManager.model.User;
import br.ufrn.imd.AccountManager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> user = userRepository.findByLogin(username);
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("Usuario ["+ username + "] não encontrado");
		}
		
		
		return new DetailsUserData(user);
	}

}
