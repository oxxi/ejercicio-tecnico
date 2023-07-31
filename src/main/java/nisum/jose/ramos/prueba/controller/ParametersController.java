package nisum.jose.ramos.prueba.controller;

import nisum.jose.ramos.prueba.models.ParameterModel;
import nisum.jose.ramos.prueba.service.interfaces.IParameterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("parameter")
public class ParametersController {
    private final IParameterService _iparameterService;
    public ParametersController(IParameterService iparameterService) {
        _iparameterService = iparameterService;
    }

    @GetMapping()
    public Collection<ParameterModel> getAll() {
        return _iparameterService.GetAll();
    }

    @PatchMapping("/change/{paramId}")
    public ParameterModel updateParameters(@PathVariable UUID paramId, @Validated @RequestBody ParameterModel model) {
        return _iparameterService.update(paramId,model);
    }
}
