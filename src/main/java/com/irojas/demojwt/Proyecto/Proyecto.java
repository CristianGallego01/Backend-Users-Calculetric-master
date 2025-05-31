package com.irojas.demojwt.Proyecto;

import com.irojas.demojwt.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Lob // Para guardar strings grandes como JSON
    private String datos;

    @Lob // También es un string largo
    private String trafoInfo;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;
}