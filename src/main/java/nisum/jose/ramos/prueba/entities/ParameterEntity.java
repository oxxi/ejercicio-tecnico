package nisum.jose.ramos.prueba.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Table(name = "PARAMETER")
@Entity
@Getter
@Setter
public class ParameterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NonNull
    private String name;
    @NonNull
    private String pattern;


}
