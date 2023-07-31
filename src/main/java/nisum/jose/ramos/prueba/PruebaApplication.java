package nisum.jose.ramos.prueba;

import nisum.jose.ramos.prueba.entities.ParameterEntity;
import nisum.jose.ramos.prueba.repositories.IParametersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PruebaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaApplication.class, args);
	}


	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	//seeder
	@Bean
	CommandLineRunner init(IParametersRepository parametersRepository){
		return (args) -> {
			//Create Entity
			ParameterEntity params = new ParameterEntity();
			params.setName("PATTERN_EMAIL");
			params.setPattern("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$");
			parametersRepository.save(params);

			ParameterEntity paramsPass = new ParameterEntity();
			paramsPass.setName("PATTERN_PASSWORD");
			//paramsPass.setPattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
			paramsPass.setPattern("^[a-zA-Z0-9]*$");
			parametersRepository.save(paramsPass);

		};
	}
}


