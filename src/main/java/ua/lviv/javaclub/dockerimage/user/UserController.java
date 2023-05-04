package ua.lviv.javaclub.dockerimage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
public class UserController {

    final private UserService userService;

    @GetMapping("/users")
    public List<UserEntity> findAll() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public UserEntity findById(@PathVariable Long id) {
        return userService.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found."));
    }
}
