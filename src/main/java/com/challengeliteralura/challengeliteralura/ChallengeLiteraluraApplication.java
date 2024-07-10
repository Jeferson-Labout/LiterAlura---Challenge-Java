package com.challengeliteralura.challengeliteralura;

import com.challengeliteralura.challengeliteralura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.challengeliteralura.challengeliteralura.client.ClienteLiteralura;
import com.challengeliteralura.challengeliteralura.repository.LivroRepository;

@SpringBootApplication
public class ChallengeLiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LivroRepository livroRepositorio;
	@Autowired
	private AutorRepository autorRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		ClienteLiteralura client = new ClienteLiteralura(livroRepositorio, autorRepositorio);
		client.menu();
	}

}
