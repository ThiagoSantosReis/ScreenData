package br.com.thisantos.screendata;

import br.com.thisantos.screendata.services.MediaAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreendataApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreendataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try{
			var mediaApi = new MediaAPI();
			var jsonData = mediaApi.getData("https://www.omdbapi.com/?t=the+flash&Season=1&apikey=19b2c158");
			System.out.println(jsonData);
		}catch (RuntimeException e){
			System.out.println(e.getMessage());
		}
	}
}
