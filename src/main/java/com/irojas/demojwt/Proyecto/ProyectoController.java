package com.irojas.demojwt.Proyecto;

import com.irojas.demojwt.User.User;
import com.irojas.demojwt.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/proyectos")
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoRepository proyectoRepository;
    private final UserRepository userRepository;

    // 🔹 Guardar nuevo proyecto
    @PostMapping("/crear")
    public ResponseEntity<Proyecto> crearProyecto(@RequestBody Proyecto proyecto,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        proyecto.setUser(user);

        // Opcional: Validar que fecha no sea nula, o asignar fecha actual si es null
        if (proyecto.getFecha() == null) {
            proyecto.setFecha(LocalDate.now());
        }

        Proyecto saved = proyectoRepository.save(proyecto);
        return ResponseEntity.ok(saved);
    }

    // 🔹 Listar proyectos del usuario actual
    @GetMapping("/obtener")
    public ResponseEntity<List<Proyecto>> listarProyectos(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Proyecto> proyectos = proyectoRepository.findByUser(user);
        return ResponseEntity.ok(proyectos);
    }

    // 🔹 Editar un proyecto por ID (solo si pertenece al usuario)
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> editarProyecto(@PathVariable Integer id,
                                                   @RequestBody Proyecto nuevoProyecto,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Proyecto proyecto = proyectoRepository.findById(id)
                .filter(p -> p.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado o no autorizado."));

        proyecto.setNombre(nuevoProyecto.getNombre());
        proyecto.setDatos(nuevoProyecto.getDatos());
        proyecto.setTrafoInfo(nuevoProyecto.getTrafoInfo());
        proyecto.setFecha(nuevoProyecto.getFecha());

        return ResponseEntity.ok(proyectoRepository.save(proyecto));
    }

    // 🔹 Eliminar un proyecto (solo si pertenece al usuario)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProyecto(@PathVariable Integer id,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Proyecto proyecto = proyectoRepository.findById(id)
                .filter(p -> p.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado o no autorizado."));

        proyectoRepository.delete(proyecto);
        return ResponseEntity.ok().build();
    }
}
