package nisum.jose.ramos.prueba.service;

import nisum.jose.ramos.prueba.entities.UserEntity;
import nisum.jose.ramos.prueba.models.PhoneModel;
import nisum.jose.ramos.prueba.models.RequestModel;
import nisum.jose.ramos.prueba.repositories.IUserRepository;
import nisum.jose.ramos.prueba.service.interfaces.IJwtService;
import nisum.jose.ramos.prueba.service.interfaces.IParameterService;
import nisum.jose.ramos.prueba.utils.PARAMETERS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {



    @Mock
    private IUserRepository _useRepository;
    @Mock
    private IJwtService _jIJwtService;


    @Mock
    private IParameterService _paraService;


    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_return_newUser() {
        RequestModel model = new RequestModel();
        model.setEmail("hola@hola.com");
        model.setPassword("1235abc");
        model.setName("test");
        model.setPhones(getPhoneModels());

        //mock email exist
        Mockito.when(_useRepository.findByEmail(model.getEmail())).thenReturn(Optional.empty());

        //mock validation field
        Mockito.when(_paraService.GetParamValue(PARAMETERS.PATTERN_EMAIL.toString())).thenReturn("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$");
        Mockito.when(_paraService.GetParamValue(PARAMETERS.PATTERN_PASSWORD.toString())).thenReturn("^[a-zA-Z0-9]*$");
        Mockito.when(_jIJwtService.generateToken(model.getEmail())).thenReturn("123456789_token");
        Mockito.when(_useRepository.save(Mockito.any())).thenReturn(getUserEntity());
        var result = userService.CreateUser(model);

        Mockito.verify(_jIJwtService).generateToken(model.getEmail());
        Mockito.verify(_paraService).GetParamValue(PARAMETERS.PATTERN_PASSWORD.toString());


        Assertions.assertNotNull(result);

    }

    @Test
    void updateUser_return_userModified() {
        RequestModel model = new RequestModel();
        model.setEmail("hola@hola.com");
        model.setPassword("1235abc");
        model.setName("test");
        model.setPhones(getPhoneModels());
        var entity = getUserEntity();
        UUID id = UUID.randomUUID();
        entity.setId(id);
        //find user
        Mockito.when(_useRepository.findById(id)).thenReturn(Optional.of(entity));


        //mock email exist
        Mockito.when(_useRepository.findByEmail(model.getEmail())).thenReturn(Optional.of(entity));

        //mock validation field
        Mockito.when(_paraService.GetParamValue(PARAMETERS.PATTERN_EMAIL.toString())).thenReturn("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$");
        Mockito.when(_paraService.GetParamValue(PARAMETERS.PATTERN_PASSWORD.toString())).thenReturn("^[a-zA-Z0-9]*$");

        Mockito.when(_useRepository.save(Mockito.any())).thenReturn(entity);
        var result = userService.UpdateUser(id,model);


        Mockito.verify(_paraService,Mockito.times(2)).GetParamValue(Mockito.any());
        Mockito.verify(_useRepository).findById(id);

        Assertions.assertNotNull(result);
    }

    @Test
    void updateUser_whenIdIsNotValid() {
        UUID id = UUID.randomUUID();
        RequestModel model = new RequestModel();
        model.setEmail("hola@hola.com");
        model.setPassword("1235abc");
        model.setName("test");
        model.setPhones(getPhoneModels());

        Mockito.when(_useRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,()->{
            userService.UpdateUser(id,model);
        });

        Mockito.verify(_useRepository).findById(id);

    }

    @Test
    void getUser_return_validUser() {
        UUID id = UUID.randomUUID();
        UserEntity entity = new UserEntity();
        entity.setId(id);

        Mockito.when(_useRepository.findById(id)).thenReturn(Optional.of(entity));
        var result = userService.GetUser(id);

        Mockito.verify(_useRepository).findById(id);
        Assertions.assertEquals(id,result.getId());
    }

    @Test
    void delete() {
        var entity = getUserEntity();
        UUID id = UUID.randomUUID();
        Mockito.when(_useRepository.findById(id)).thenReturn(Optional.of(entity));

        userService.Delete(id);

        Mockito.verify(_useRepository).findById(id);
        Mockito.verify(_useRepository).delete(entity);
    }


    private ArrayList<PhoneModel> getPhoneModels() {
        ArrayList<PhoneModel> pho = new ArrayList<>();
        PhoneModel p = new PhoneModel();
        p.setCityCode("1");
        p.setNumber("123");
        p.setCountryCode("555");
        pho.add(p);
        return pho;
    }

    private UserEntity getUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setModified(LocalDateTime.now());
        entity.setCreated(LocalDateTime.now());
        entity.setName("test");
        entity.setEmail("hola@hola.com");
        entity.setPassword("1235abc");
        entity.setToken("123456789_token");
        entity.setPhones(new HashSet<>());

        return  entity;
    }
}