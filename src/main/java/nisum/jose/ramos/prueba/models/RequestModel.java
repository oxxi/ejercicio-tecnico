package nisum.jose.ramos.prueba.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;


@Getter
@Setter
public class RequestModel {

        @NotNull(message = "name de no puede ser nulo")
        @NotEmpty(message = "el nombre es obligatorio")
        private String name;

        @NotNull(message = "email de no puede ser nulo")
        @NotEmpty(message = "el email es obligatorio")
        private String email;

        @NotNull(message = "password de no puede ser nulo")
        @NotEmpty(message = "el password es obligatorio")
        private String password;

        @Valid
        private ArrayList<PhoneModel> phones;

}


