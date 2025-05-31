package com.irojas.demojwt.Proyecto;

import com.irojas.demojwt.User.User;
import com.irojas.demojwt.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final UserRepository userRepository;

    public Proyecto guardarProyecto(String nombre, String datos, String trafoInfo, LocalDate fecha, Authentication auth) {
        String username = ((User) auth.getPrincipal()).getUsername();
        User usuario = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Proyecto proyecto = Proyecto.builder()
                .nombre(nombre)
                .datos(datos)
                .trafoInfo(trafoInfo)
                .fecha(fecha)
                .user(usuario)
                .build();

        return proyectoRepository.save(proyecto);
    }

    public List<Proyecto> obtenerProyectosDelUsuario(Authentication auth) {
        String username = ((User) auth.getPrincipal()).getUsername();
        User usuario = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return proyectoRepository.findByUser(usuario);
    }
}
