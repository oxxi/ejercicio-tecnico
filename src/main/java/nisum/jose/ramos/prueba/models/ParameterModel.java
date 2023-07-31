package nisum.jose.ramos.prueba.models;




import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import nisum.jose.ramos.prueba.utils.PARAMETERS;

import java.util.UUID;

@Getter
@Setter

public class ParameterModel {


    private UUID id;

    private PARAMETERS name;
    @NotNull(message = "Expresión no puede ser vacio ")
    private String pattern;
}
