package com.learning.springboot.busca;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Scanner;

@Component
public class BuscaRunner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    private ConfigurableApplicationContext context;

    public BuscaRunner(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Scanner s = new Scanner(System.in)){
            System.out.println("Id de la reunion: ");
            long idReunion = Long.parseLong(s.next());
            System.out.println("Id de la persona: ");
            long idPersona = Long.parseLong(s.next());
            InfoBusca info = new InfoBusca();
            info.setIdReunion(idReunion);
            info.setIdAsistente(idPersona);
            rabbitTemplate.convertAndSend("servicios",
                    "reuniones.busca.personas",
                    objectMapper.writeValueAsString(info));
            context.close();
        }

    }
}
