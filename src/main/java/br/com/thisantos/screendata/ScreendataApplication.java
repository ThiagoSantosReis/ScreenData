package br.com.thisantos.screendata;

import br.com.thisantos.screendata.principal.Principal;
import br.com.thisantos.screendata.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class ScreendataApplication implements CommandLineRunner {

	@Autowired
	private SeriesRepository seriesRepository;

	public static void main(String[] args) {
		SpringApplication.run(ScreendataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Locale.setDefault(Locale.US);
		Principal principal = new Principal(seriesRepository);
		principal.showMenu();

	}
}
