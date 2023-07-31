package nisum.jose.ramos.prueba.controller;

import io.swagger.v3.oas.annotations.Operation;
import nisum.jose.ramos.prueba.models.RequestModel;
import nisum.jose.ramos.prueba.models.ResponseModel;
import nisum.jose.ramos.prueba.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserServiceImpl _userService;
    public UserController(UserServiceImpl userService) {
        _userService = userService;
    }


    @Operation(summary = "Usuario", description = "Devuelve la información del usuario")
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseModel> getUserInfo(@PathVariable UUID userId) {
        var result = _userService.GetUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseModel> createUser(@Validated @RequestBody RequestModel model){
        var result = _userService.CreateUser(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ResponseModel> updateUser(@PathVariable UUID userId, @Validated @RequestBody RequestModel model){
        var result =  _userService.UpdateUser(userId,model);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/delete/{userId}")
    public void delete(@PathVariable UUID userId) {
        _userService.Delete(userId);
    }
}
