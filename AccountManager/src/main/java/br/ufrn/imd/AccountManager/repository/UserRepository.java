package br.ufrn.imd.AccountManager.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.AccountManager.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	public Optional<User> findByLogin (String login);
}
