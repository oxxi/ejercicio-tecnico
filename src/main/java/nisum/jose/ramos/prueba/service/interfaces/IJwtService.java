package nisum.jose.ramos.prueba.service.interfaces;

public interface IJwtService {

    String generateToken(String email);
    boolean validateToken(String token);

    String getClaim(String token,String name);
}
