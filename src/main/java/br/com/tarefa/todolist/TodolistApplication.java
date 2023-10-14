package br.com.tarefa.todolist;

import br.com.tarefa.todolist.user.IUserRepository;
import br.com.tarefa.todolist.user.UserModel;
import br.com.tarefa.todolist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodolistApplication implements CommandLineRunner {

	@Autowired
	private IUserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserModel userModel = new UserModel();
		userModel.setName("Administrador");
		userModel.setUsername("admin");
		userModel.setPassword(Utils.getConvertPassword("admin"));
		repository.save(userModel);
	}
}
