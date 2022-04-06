package com.tfg.clientix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.clientix.models.entity.Clientes;
import com.tfg.clientix.services.IClienteServices;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("ProyectoFinal/webapi")
public class ClienteRestController {
	
	@Autowired
	private IClienteServices clienteServices;
	
	@GetMapping("/clientes")
	public List<Clientes>getClientes()
	{
		return clienteServices.getClientes();
		
	}
	
	@PostMapping("/insertarclientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Clientes insert(@RequestBody Clientes c) {
		// TODO Auto-generated method stub
		return clienteServices.insert(c);
	}
}
