package com.tfg.clientix.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("ProyectoFinal/webapi")
public class EnviosRestController {
	
	Map<String, Object> response = new HashMap<>();

}
