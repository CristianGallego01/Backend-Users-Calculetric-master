package com.irojas.demojwt.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> listarUsuarios() {
        return userService.listarUsuarios();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')") // Solo ADMIN puede eliminar usuarios
    public String eliminarUsuario(@PathVariable Integer id) {
        return userService.eliminarUsuario(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserDTO> actualizarUsuario(@PathVariable Integer id, @RequestBody UpdateUserRequest request) {
        UserDTO userActualizado = userService.actualizarUsuario(id, request);
        return ResponseEntity.ok(userActualizado);
    }
}
