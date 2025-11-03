package com.utn.tareas.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class MensajeDevService implements MensajeService {
    @Override
    public String mostrarBienvenida() {
        return "[DEV] Bienvenido. (Modo DEV activado)";
    }

    @Override
    public String mostrarDespedida() {
        return "[DEV] Adiós. Apagando los motores de desarrollo.";
    }

}
