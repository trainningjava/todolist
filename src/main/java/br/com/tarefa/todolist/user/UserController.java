package br.com.tarefa.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.tarefa.todolist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Modificador
 * public
 * private
 * protected
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    /**
     * String (texto)
     * Integer (int) numeros inteiros
     * Double (double) Números 0.0000
     * Float (float) Números 0.0000
     * char (A C)
     * Date (data)
     * void
     */
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        UserModel user = userRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            // Mensagem de erro
            // Status Code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        var passwordHashred = Utils.getConvertPassword(userModel.getPassword());


        userModel.setPassword(passwordHashred);

        var userCreated = userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }


}
