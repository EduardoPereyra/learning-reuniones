package com.learning.springboot.reuniones.async;

import com.learning.springboot.reuniones.models.Persona;
import com.learning.springboot.reuniones.models.Reunion;
import com.learning.springboot.reuniones.services.PersonaService;
import com.learning.springboot.reuniones.services.ReunionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

@Component
public class BuscaListener {
    private static final Logger LOG = LoggerFactory.getLogger(BuscaListener.class);
    private final ObjectMapper mapper;
    private final PersonaService personaService;
    private final ReunionService reunionService;

    public BuscaListener(ObjectMapper mapper, PersonaService personaService, ReunionService reunionService) {
        this.mapper = mapper;
        this.personaService = personaService;
        this.reunionService = reunionService;
    }

    public void recibirMensaje(String mensaje) {
        try {
            InfoBusca info = mapper.readValue(mensaje, InfoBusca.class);
            Optional<Persona> persona = personaService.getById(info.getIdAsistente());
            if (persona.isEmpty()) {
                LOG.warn("Mensaje recibido, pero la persona {} no existe", info.getIdAsistente());
            }
            Optional<Reunion> reunion = reunionService.getById(info.getIdAsistente());
            if (reunion.isEmpty()) {
                LOG.warn("Mensaje recibido, pero la reunion {} no existe", info.getIdReunion());
            }
            if (persona.isPresent() && reunion.isPresent()) {
                LOG.info("{} {} tiene una reunion a las {}",
                        persona.get().getNombre(),
                        persona.get().getApellidos(),
                        reunion.get().getFecha());
            }
        } catch (JacksonException e) {
            LOG.warn("Mensaje incorrecto", e);
        }
    }
}
