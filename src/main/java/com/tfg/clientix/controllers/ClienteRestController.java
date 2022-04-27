package com.tfg.clientix.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.clientix.errorCharger.CodigosErrorRest;
import com.tfg.clientix.errorCharger.ErrorRest;
import com.tfg.clientix.models.entity.Clientes;
import com.tfg.clientix.services.IClienteServices;
import com.tfg.clientix.validations.Validaciones;
import org.springframework.util.StringUtils;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("ProyectoFinal/webapi")
public class ClienteRestController {
	
	Map<String, Object> response = new HashMap<>();
	
	@Autowired
	private IClienteServices clienteServices;
	
	//CONSULTAR TODOS LOS CLIENTES
	@GetMapping("/clientes")
	public List<Clientes> getClientes() {
		return clienteServices.getClientes();

	}
	//INSERTAR UN CLIENTE
	@PostMapping("/insertarcliente")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> insert(@RequestBody Clientes c) {

		Clientes cliente_nuevo = null;
		ErrorRest error = new ErrorRest();
		Map<String, Object> response = new HashMap<>();

		error = Validaciones.validarCliente(c,clienteServices);

		if (error.getCodError().equals(CodigosErrorRest.COD_ERROR_CERO)
				&& error.getLitError().equals(CodigosErrorRest.LIT_ERROR_SUCCESS) && error.isValidado()) {
			cliente_nuevo = clienteServices.insert(c);
		} else {
			if (StringUtils.isEmpty(error.getLitError())) {
				response.put("Mensaje", "Por favor revise los valores enviados");
				response.put("Código de error:", CodigosErrorRest.COD_ERROR_DEFECTO);
			} else {
				response.put("Mensaje", error.getLitError());
				response.put("Código de error:", CodigosErrorRest.COD_ERROR_UNO);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<Clientes>(c, HttpStatus.CREATED);

	}
	//CONSULTAR UN CLIENTE POR SU ID
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> mostrarporId(@PathVariable Integer id) {

		Clientes c = null;
		Map<String, Object> response = new HashMap<>();

		try {
			c = clienteServices.getClienteid(id);
		} catch (DataAccessException e) {
			response.put("Mensaje", "Error al realizar la consulta en la base de datos");
			response.put("Código de error:",CodigosErrorRest.COD_ERROR_DEFECTO);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (c == null) {
			response.put("Mensaje",
					"El cliente con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Clientes>(c, HttpStatus.OK);

	}
	//Esto va a ser la paginación hay que hacerla en algun momento
	/*
	@GetMapping("/clientes/{registroInicial},{numRegistros}")
	public List<Clientes> getClientesPaginacion(@PathVariable Integer registroInicial, @PathVariable Integer numRegistros){
		
	}*/
	//ACTUALIZAR CLIENTE
	@PutMapping("/actualizarCliente/{id}")
	public ResponseEntity<?> update(@RequestBody Clientes c, @PathVariable Integer id) {
		
		Clientes cliente_actual = clienteServices.getClienteid(id);
		Clientes cliente_actualizado = null;

		if (cliente_actual == null) {
			response.put("Mensaje", "No se pudo editar, el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos")));
			response.put("Código de error:", CodigosErrorRest.COD_ERROR_CLIENTE_NO_ENCONTRADO);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}else {
			ErrorRest error = new ErrorRest();

			error = Validaciones.validarCliente(c,clienteServices);

			if (error.getCodError().equals(CodigosErrorRest.COD_ERROR_CERO)
					&& error.getLitError().equals(CodigosErrorRest.LIT_ERROR_SUCCESS) && error.isValidado()) {
				cliente_actual.setCIFNIF(c.getCIFNIF());
				cliente_actual.setDireccionFacturacion(c.getDireccionFacturacion());
				cliente_actual.setNombreCliente(c.getNombreCliente());
				cliente_actualizado = clienteServices.insert(cliente_actual);
			} else {
				if (StringUtils.isEmpty(error.getLitError())) {
					response.put("Mensaje", "Por favor revise los valores enviados");
					response.put("Código de error:", CodigosErrorRest.COD_ERROR_DEFECTO);
				} else {
					response.put("Mensaje", error.getLitError());
					response.put("Código de error:", CodigosErrorRest.COD_ERROR_UNO);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			response.put("Mensaje", "El cliente ha sido actualizado con éxito!");
			response.put("Cliente:", cliente_actualizado);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}	
	}
	
	//METODO BORRAR POR ID
	@DeleteMapping("borrarCliente/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id)
	{
		clienteServices.deletebyId(id);
		response.put("Mensaje", "Cliente borrado con éxito");
		response.put("Código de Error", CodigosErrorRest.COD_ERROR_CERO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	

}
