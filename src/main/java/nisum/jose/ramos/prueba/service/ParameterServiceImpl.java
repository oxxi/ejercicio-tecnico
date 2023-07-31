package nisum.jose.ramos.prueba.service;

import nisum.jose.ramos.prueba.entities.ParameterEntity;
import nisum.jose.ramos.prueba.models.ParameterModel;
import nisum.jose.ramos.prueba.repositories.IParametersRepository;
import nisum.jose.ramos.prueba.service.interfaces.IParameterService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.UUID;


@Service
public class ParameterServiceImpl implements IParameterService {

    private final ModelMapper _modelMapper;
    private final IParametersRepository _parameterService;

    public ParameterServiceImpl(IParametersRepository parameters) {

        _parameterService = parameters;

        _modelMapper = new ModelMapper();
    }

    @Override
    public Collection<ParameterModel> GetAll() {
       var entity = _parameterService.findAll();
       return entity.stream().map(e->toModel(e)).toList();
    }

    @Override
    public ParameterModel update(UUID paramId, ParameterModel model) {

       var parameter =  _parameterService.findById(paramId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("UUID: %s no existe",paramId)));
       //update
        parameter.setPattern(model.getPattern());
        _parameterService.save(parameter);
        return toModel(parameter);
    }


    @Override
    public String GetParamValue(String name) {

       var parameter = _parameterService.findByName(name).orElseThrow(()-> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,String.format("Valor no configurado para: %s",name)));
       return parameter.getPattern();
    }


    private ParameterModel toModel(ParameterEntity entity) {
        return  _modelMapper.map(entity, ParameterModel.class);
    }

    private ParameterEntity toEntity(ParameterModel model) {
        return  _modelMapper.map(model, ParameterEntity.class);
    }
}
