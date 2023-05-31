package br.ufrn.imd.AccountManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import br.ufrn.imd.AccountManager.config.MongoConfig;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = "br.ufrn.imd.AccountManager")
@EnableMongoRepositories(basePackages = "br.ufrn.imd.AccountManager.repository.UserRepository")
@EnableMongoAuditing
@Import(MongoConfig.class)
public class AccountManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountManagerApplication.class, args);
	}

}
