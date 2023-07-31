package nisum.jose.ramos.prueba.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;


@Table(name = "PHONE")
@Entity
@Getter
@Setter
public class PhoneEntity {


    public PhoneEntity(){}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NonNull
    @Column(nullable = false)
    private String number;
    @NonNull
    @Column(nullable = false)
    private String cityCode;
    @NonNull
    @Column(nullable = false)
    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "register_id")
    private UserEntity userEntity;
}
