package br.com.tarefa.todolist.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    
    List<TaskModel> findByIdUser(UUID idUser);

    @Transactional
    @Modifying
    @Query("delete from tb_tasks t where t.idUser = :idUser")
    int deleteByIdUser(UUID idUser);

}
