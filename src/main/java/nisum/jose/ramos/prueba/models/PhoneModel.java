package nisum.jose.ramos.prueba.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class PhoneModel {

    @NotNull(message = "Número de no puede ser nulo")
    private String number;

    @NotNull(message = "citycode de no puede ser nulo")
    @JsonProperty("citycode")
    private String cityCode;

    @NotNull(message = "contrycode de no puede ser nulo")
    @JsonProperty("contrycode")
    private String countryCode;
}
