package br.com.fiap.livemusic.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.com.fiap.livemusic.user.User;
import br.com.fiap.livemusic.user.UserService;

@Service
public class TaskService {

    @Autowired
    TaskRepository repository;

    @Autowired
    UserService userService;

    public List<Task> findAll() {
        return repository.findAll();
    }

    public boolean delete(Long id) {
        var task = repository.findById(id);

        if (task.isEmpty())
            return false;

        repository.deleteById(id);
        return true;
    }

    public void save(Task task) {
        repository.save(task);
    }

    public void decrement(Long id) {
        
        var optional = repository.findById(id);

        if (optional.isEmpty())
            throw new RuntimeException("musica não encontrada");

        var task = optional.get();

        if (task.getStatus() == null || task.getStatus() <= 0)
            throw new RuntimeException("musica não pode ter status negativo");

        task.setStatus(task.getStatus() - 10);

        // salvar
        repository.save(task);
    }

    public void increment(Long id) {
        
        var optional = repository.findById(id);

        if (optional.isEmpty())
            throw new RuntimeException("musica não encontrada");

        var task = optional.get();

        if (task.getStatus() == null)
            task.setStatus(0);

        if (task.getStatus() == 100) {
            throw new RuntimeException("musica não pode ter status maior que 100");
        }

        task.setStatus(task.getStatus() + 10);

        if (task.getStatus() == 100){
            var user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.addScore(User.convert(user) , task.getScore());
        }

        // salvar
        repository.save(task);
    }

    public void cacthTask(Long id, OAuth2User user) {
        var optional = repository.findById(id);

        if (optional.isEmpty())
            throw new RuntimeException("musica não encontrada");

        var task = optional.get();

        if (task.getUser() != null)
            throw new RuntimeException("musica já atribuída");

        task.setUser(User.convert(user));

        repository.save(task);

    }

    public void dropTask(Long id, OAuth2User user) {
        var optional = repository.findById(id);

        if (optional.isEmpty())
            throw new RuntimeException("musica não encontrada");

        var task = optional.get();

        if (task.getUser() == null)
            throw new RuntimeException("musica não atribuída");
            
        if (!task.getUser().equals(User.convert(user)))
            throw new RuntimeException("não pode adicionar musica de outra pessoa");

        task.setUser(null);

        repository.save(task);
    }

}