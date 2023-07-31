package nisum.jose.ramos.prueba.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;



@Table(name = "REGISTER")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class UserEntity {
    public UserEntity() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @Column(nullable = false, updatable = true)
    @UpdateTimestamp
    private LocalDateTime modified;

    @Builder.Default
    private LocalDateTime lastLogin = LocalDateTime.now();

    @Builder.Default
    @Lob
    private String token = "";

    @Builder.Default
    private boolean isActive = true;

   @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
   private Set<PhoneEntity> phones;
}
