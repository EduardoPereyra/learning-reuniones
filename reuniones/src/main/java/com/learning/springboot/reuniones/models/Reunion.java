package com.learning.springboot.reuniones.models;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reunion")
public class Reunion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String asunto;
    private ZonedDateTime fecha;
    @ManyToMany
    @JoinTable(name = "asistente",
                joinColumns = {@JoinColumn(name = "reunion_id")},
                inverseJoinColumns = {@JoinColumn(name = "persona_id")}
    )
    private List<Persona> asistentes = new ArrayList<>();

    public Reunion() {
    }

    public Reunion(long id, String asunto, ZonedDateTime fecha) {
        this.id = id;
        this.asunto = asunto;
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public List<Persona> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(List<Persona> asistentes) {
        this.asistentes = asistentes;
    }

    public void addAssistente(Persona asistente) {
        this.asistentes.add(asistente);
    }

    @Override
    public String toString() {
        return "Reunion{" +
                "id=" + id +
                ", asunto='" + asunto + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
