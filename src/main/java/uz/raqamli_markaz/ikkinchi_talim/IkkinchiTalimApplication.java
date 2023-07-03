package uz.raqamli_markaz.ikkinchi_talim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import uz.raqamli_markaz.ikkinchi_talim.service.Utils;

@SpringBootApplication
@EnableJpaRepositories
public class IkkinchiTalimApplication implements CommandLineRunner {

	@Autowired
	private Utils utils;

	public static void main(String[] args) {
		SpringApplication.run(IkkinchiTalimApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		utils.saveDiplomaSerial();
	}
}
