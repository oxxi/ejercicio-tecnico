package nisum.jose.ramos.prueba.repositories;

import nisum.jose.ramos.prueba.entities.ParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface IParametersRepository extends JpaRepository<ParameterEntity, UUID> {

    Optional<ParameterEntity> findByName(String name);

}
