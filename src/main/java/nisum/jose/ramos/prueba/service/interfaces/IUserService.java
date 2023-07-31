package nisum.jose.ramos.prueba.service.interfaces;

import nisum.jose.ramos.prueba.entities.UserEntity;
import nisum.jose.ramos.prueba.models.RequestModel;
import nisum.jose.ramos.prueba.models.ResponseModel;

import java.util.UUID;

public interface IUserService {

    ResponseModel CreateUser(RequestModel user);
    ResponseModel UpdateUser(UUID uuid, RequestModel user);

    ResponseModel GetUser(UUID uuid);
    void Delete(UUID uuid);

}
