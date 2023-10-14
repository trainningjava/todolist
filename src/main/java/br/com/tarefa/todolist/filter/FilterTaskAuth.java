package br.com.tarefa.todolist.filter;

import br.com.tarefa.todolist.user.IUserRepository;
import br.com.tarefa.todolist.user.UserModel;
import br.com.tarefa.todolist.utils.Utils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/") || servletPath.startsWith("/users/")) {
            // Pegar a autenticação (usuário e senha)
            var authorization = request.getHeader("Authorization");

            if (authorization == null) {
                response.sendError(401);
                return;
            }

            String[] credentials = Utils.getCredential(authorization);

            String username = credentials[0];
            String password = credentials[1];

            // Validar usuário
            UserModel user = userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401);
            } else {
                // Validar senha
                var passwordVerify = Utils.isPasswordValid(password, user.getPassword());
                if (passwordVerify) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }

            }
        } else {
            filterChain.doFilter(request, response);
        }

    }
}
