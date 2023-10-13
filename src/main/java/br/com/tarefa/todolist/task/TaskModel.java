package br.com.tarefa.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(length = 150)
    @NotNull
    @Size(min=1, max=150, message = "O campo deve conter no máximo 150 caracteres")
    private String description;
    @Column(length = 50)
    @NotNull
    @Size(min=1, max=50, message = "O campo deve conter no máximo 50 caracteres")
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    @NotNull
    @NotBlank
    private String priority;
    @CreatedBy
    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
