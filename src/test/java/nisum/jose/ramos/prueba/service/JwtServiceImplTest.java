package nisum.jose.ramos.prueba.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceImplTest {

    @InjectMocks
    private JwtServiceImpl jwtService = new JwtServiceImpl();

    @Test
    void generateToken() {

       var token = jwtService.generateToken("hola@hola.com");
        Assertions.assertNotNull(token);
    }

    @Test
    void validateToken() {
        var token = jwtService.generateToken("hola@hola.com");

        var isValid =  jwtService.validateToken(token);

        Assertions.assertTrue(isValid);
    }

    @Test
    void getClaim() {
        var expected = "hola@hola.com";
        var token = jwtService.generateToken("hola@hola.com");
        var claim = jwtService.getClaim(token,"name");

        Assertions.assertEquals(expected,claim);


    }
}