package br.com.tarefa.todolist.task;

import br.com.tarefa.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio ou término deve(m) ser maior do que a data atual");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio deve ser menor do que a data de término");
        }

        TaskModel task = taskRepository.save(taskModel);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/")
    public ResponseEntity<List<TaskModel>> list(HttpServletRequest request) {

        var idUser = request.getAttribute("idUser");
        List<TaskModel> tasks = taskRepository.findByIdUser((UUID) idUser);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request) {

        var task = taskRepository.findById(id).orElse(null);

        Utils.copyNonNullProperties(taskModel, task);

        taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskModel);
    }
}
