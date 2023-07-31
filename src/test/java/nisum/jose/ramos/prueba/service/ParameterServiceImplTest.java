package nisum.jose.ramos.prueba.service;

import jakarta.persistence.Entity;
import nisum.jose.ramos.prueba.entities.ParameterEntity;
import nisum.jose.ramos.prueba.models.ParameterModel;
import nisum.jose.ramos.prueba.repositories.IParametersRepository;
import nisum.jose.ramos.prueba.service.interfaces.IParameterService;
import nisum.jose.ramos.prueba.utils.PARAMETERS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ParameterServiceImplTest {


    @Mock
    private IParametersRepository _paramRepo;

    @InjectMocks
    private ParameterServiceImpl parameterService;

    @Test
    void getAll() {
        Collection<ParameterEntity> params = new ArrayList<>();
        params.add(getEntity());
        params.add(getEntity());

        Mockito.when(_paramRepo.findAll()).thenReturn((List)params);

        var result = parameterService.GetAll();

        Mockito.verify(_paramRepo).findAll();
        Assertions.assertNotNull(result);
    }

    @Test
    void update() {
        var model = getModel();
        var entity = getEntity();
        entity.setName(model.getName().toString());
        Mockito.when(_paramRepo.findById(model.getId())).thenReturn(Optional.of(entity));

        var result = parameterService.update(model.getId(), model);

        Mockito.verify(_paramRepo).findById(model.getId());
        Mockito.verify(_paramRepo).save(entity);

        Assertions.assertEquals(model.getPattern(),result.getPattern());
    }

    @Test
    void getParamValue() {

        Mockito.when(_paramRepo.findByName(PARAMETERS.PATTERN_EMAIL.toString())).thenReturn(Optional.of(getEntity()));

        var result = parameterService.GetParamValue(PARAMETERS.PATTERN_EMAIL.toString());
        Mockito.verify(_paramRepo).findByName(PARAMETERS.PATTERN_EMAIL.toString());
        Assertions.assertNotNull(result);
    }

    private ParameterModel getModel() {
        var model = new ParameterModel();
        model.setId(UUID.randomUUID());
        model.setName(PARAMETERS.PATTERN_EMAIL);
        model.setPattern("AAA");
        return model;
    }
    private ParameterEntity getEntity() {
        ParameterEntity entity = new ParameterEntity();
        entity.setId(UUID.randomUUID());
        entity.setName("PARAMETER_" + Math.floor(Math.random() + 1));
        entity.setPattern("AAA" + + Math.floor(Math.random() + 1));

        return entity;
    }
}