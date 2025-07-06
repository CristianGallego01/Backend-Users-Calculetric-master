package com.irojas.demojwt.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // ✅ Inyectamos el codificador de contraseñas

    public List<UserDTO> listarUsuarios() {
        List<User> usuarios = userRepository.findAll();
        return usuarios.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getCountry()
                ))
                .toList();
    }

    public String eliminarUsuario(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("El usuario con ID: " + id + " no existe");
        }
        userRepository.deleteById(id);
        return "Usuario eliminado correctamente con ID: " + id;
    }

    public UserDTO actualizarUsuario(Integer id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setCountry(request.getCountry());

        // ✅ Codifica y actualiza la contraseña solo si viene en el request
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getCountry()
        );
    }
}
