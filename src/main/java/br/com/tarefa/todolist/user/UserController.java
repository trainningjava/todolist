package br.com.tarefa.todolist.user;

import br.com.tarefa.todolist.task.ITaskRepository;
import br.com.tarefa.todolist.task.TaskModel;
import br.com.tarefa.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @Autowired
    private ITaskRepository taskRepository;

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

    @GetMapping("/")
    public ResponseEntity<List<UserModel>> list() {

        List<UserModel> userList = userRepository.findAll();
        return ResponseEntity.ok(userList);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody UserModel userModel, @PathVariable UUID id, HttpServletRequest request) {

        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrada");
        }

        var idUser = request.getAttribute("idUser");
        if (!user.getId().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não tem permissão para alterar essa tarefa");
        }

        UserModel userDTO = new UserModel();
        if (userModel.getName() != null) {
            userDTO.setName(userModel.getName());
        }
        if (userModel.getPassword() != null) {
            var passwordHashred = Utils.getConvertPassword(userModel.getPassword());
            userDTO.setPassword(passwordHashred);
        }

        Utils.copyNonNullProperties(userDTO, user);

        var taskUpdated = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {

        var task = userRepository.findById(id).orElse(null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrada");
        } else {

            List<TaskModel> byIdUser = taskRepository.findByIdUser(id);
            if (!byIdUser.isEmpty()) {
                taskRepository.deleteByIdUser(id);
            }

            userRepository.deleteById(id);
            return ResponseEntity.ok("Excluido com sucesso o usuário");
        }

    }


}
