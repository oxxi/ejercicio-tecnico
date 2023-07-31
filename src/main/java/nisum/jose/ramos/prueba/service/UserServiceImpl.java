package nisum.jose.ramos.prueba.service;

import nisum.jose.ramos.prueba.entities.PhoneEntity;
import nisum.jose.ramos.prueba.entities.UserEntity;
import nisum.jose.ramos.prueba.models.PhoneModel;
import nisum.jose.ramos.prueba.models.RequestModel;
import nisum.jose.ramos.prueba.models.ResponseModel;
import nisum.jose.ramos.prueba.repositories.IUserRepository;
import nisum.jose.ramos.prueba.service.interfaces.IJwtService;
import nisum.jose.ramos.prueba.service.interfaces.IParameterService;
import nisum.jose.ramos.prueba.service.interfaces.IUserService;
import nisum.jose.ramos.prueba.utils.PARAMETERS;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;



@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository _userRepo;
    private final ModelMapper _modelMapper;
    private final IJwtService _jwtService;
    private final IParameterService _parameters;

    public UserServiceImpl(IUserRepository userRepo, IJwtService jwtService, IParameterService parameters) {
        _userRepo = userRepo;
        _jwtService = jwtService;
        _parameters = parameters;
        _modelMapper = new ModelMapper();
    }

    @Override
    public ResponseModel CreateUser(RequestModel user) {

        if(!emailExist(user.getEmail().trim(),null)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El correo ya fue registrado");
        validate(user);
        var entity = toEntity(user);
        entity.getPhones().forEach(phoneEntity -> phoneEntity.setUserEntity(entity));
        //generate token
        var token  = _jwtService.generateToken(entity.getEmail());
        entity.setToken(token);
        entity.setModified(null);
        var response = _userRepo.save(entity);

        return toResponse(response);
    }

    @Override
    public ResponseModel UpdateUser(UUID uuid, RequestModel user) {
        //user exist
        var entity = _userRepo.findById(uuid).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("UUID: %s no encontrado", uuid)));
        //
        validate(user);
        //validate email
        if(!emailExist(user.getEmail(), Optional.of(uuid))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El correo ya fue registrado");
        }
        entity.setEmail(user.getEmail());
        entity.setName(user.getName());
        entity.setPassword(user.getPassword());
        entity.setModified(LocalDateTime.now());
        //Get new phones
        List<PhoneEntity> listOfPhones = user.getPhones().stream().map(x->toPhoneEntity(x)).toList();
        listOfPhones.forEach(phoneEntity -> phoneEntity.setUserEntity(entity));
        //remove all phones
        entity.getPhones().clear();
        //add new
        entity.getPhones().addAll(listOfPhones);

        var response = _userRepo.save(entity);
        return toResponse(response);
    }

    @Override
    public ResponseModel GetUser(UUID uuid) {
        var entity = _userRepo.findById(uuid).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("UUID: %s no encontrado", uuid)));
        return toResponse(entity);
    }

    @Override
    public void Delete(UUID uuid) {
       var entity = _userRepo.findById(uuid).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("UUID:  %s no encontrado ",uuid)));
       _userRepo.delete(entity);
    }


    private ResponseModel toResponse(UserEntity entity) {
       return _modelMapper.map(entity, ResponseModel.class);
    }

    private UserEntity toEntity(RequestModel model) {
        return  _modelMapper.map(model,UserEntity.class);
    }

    private PhoneEntity toPhoneEntity(PhoneModel phoneModel){
        return _modelMapper.map(phoneModel, PhoneEntity.class);
    }

    private void validate(RequestModel model) {
        var regexEmail = _parameters.GetParamValue(PARAMETERS.PATTERN_EMAIL.toString());
        var regexPassword = _parameters.GetParamValue(PARAMETERS.PATTERN_PASSWORD.toString());

        Pattern emailPattern = Pattern.compile(regexEmail,Pattern.CASE_INSENSITIVE);
        Pattern passwordPattern = Pattern.compile(regexPassword,Pattern.CASE_INSENSITIVE);
        //validate email pattern
        var val = emailPattern.matcher(model.getEmail()).matches();
         if(!emailPattern.matcher(model.getEmail()).matches()) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST,String.format("El email no cumple con el patrón: %s", regexEmail));
         }
        //validate phone pattern
        if(!passwordPattern.matcher(model.getPassword()).matches()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,String.format("El password no cumple con el patrón: %s", regexPassword));
        }

    }

    private boolean emailExist(String email, Optional<UUID> userID) {
       var exist = _userRepo.findByEmail(email);
       if(userID == null || userID.isEmpty()) {
           return exist.isEmpty();
       }
       var user = exist.get();
       return user.getId().equals(userID.get());
    }

}
