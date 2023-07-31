package nisum.jose.ramos.prueba.service.interfaces;

import nisum.jose.ramos.prueba.models.ParameterModel;

import java.util.Collection;
import java.util.UUID;

public interface IParameterService {

    Collection<ParameterModel> GetAll();

    ParameterModel update(UUID paramId, ParameterModel model);
    String GetParamValue(String name);
}
