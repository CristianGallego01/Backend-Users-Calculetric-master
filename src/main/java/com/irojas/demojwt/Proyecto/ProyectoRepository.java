package com.irojas.demojwt.Proyecto;

import com.irojas.demojwt.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    List<Proyecto> findByUser(User user);

}
