package nisum.jose.ramos.prueba.exception;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GlobalException {
    private String message;
}
