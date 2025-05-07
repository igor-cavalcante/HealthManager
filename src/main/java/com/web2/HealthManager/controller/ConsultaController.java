package com.web2.HealthManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clinica/consulta")
public class ConsultaController {

    @GetMapping
    public String consulta() {
        return "consulta/consultaList";
    }
}
