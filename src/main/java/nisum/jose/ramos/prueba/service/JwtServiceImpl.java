package nisum.jose.ramos.prueba.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import nisum.jose.ramos.prueba.service.interfaces.IJwtService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class JwtServiceImpl implements IJwtService {
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JwtServiceImpl(){
        algorithm = Algorithm.HMAC512("TestSecret");
        verifier = JWT.require(algorithm).withIssuer("Nisum").build();

    }
    @Override
    public String generateToken(String email) {
        UUID uuid = UUID.randomUUID();
        Instant now = Instant.now();
        try{

            return JWT.create()
                    .withJWTId(uuid.toString())
                    .withIssuer("Nisum")
                    .withClaim("name",email)
                    .withIssuedAt(now)
                    .withExpiresAt(now.plus(1, ChronoUnit.HOURS))
                    .sign(algorithm);


        }catch (JWTCreationException ex){
            throw new RuntimeException("Error al crear el token");
        }
    }

    @Override
    public boolean validateToken(String token) {

        try {
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException ex) {
            throw new JWTVerificationException("Invalid Token.");
        }
    }

    @Override
    public String getClaim(String token, String name) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            Claim claim = jwt.getClaim(name);
            return claim.asString();
        }catch (Exception ex){
            throw new RuntimeException("error al extraer claim");
        }
    }
}
