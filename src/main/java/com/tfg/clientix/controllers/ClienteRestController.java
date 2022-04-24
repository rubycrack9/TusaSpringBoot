package com.tfg.clientix.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.tfg.clientix.validations.ValidarClientes;
import org.springframework.util.StringUtils;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("ProyectoFinal/webapi")
public class ClienteRestController {

	@Autowired
	private IClienteServices clienteServices;

	@GetMapping("/clientes")
	public List<Clientes> getClientes() {
		return clienteServices.getClientes();

	}

	@PostMapping("/insertarcliente")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> insert(@RequestBody Clientes c) {

		Clientes cliente_nuevo = null;
		ErrorRest error = new ErrorRest();
		Map<String, Object> response = new HashMap<>();
		try {

			error = ValidarClientes.validarCliente(c);

			if (error.getCodError().equals(CodigosErrorRest.COD_ERROR_CERO)
					&& error.getLitError().equals(CodigosErrorRest.LIT_ERROR_SUCCESS) && error.isValidado()) {
				cliente_nuevo = clienteServices.insert(c);
			} else {
				if(StringUtils.isEmpty(error.getLitError())){
					response.put("Mensaje", "Por favor revise los valores enviados");
					response.put("Código de error:",CodigosErrorRest.COD_ERROR_DEFECTO);
				}else {
					response.put("Mensaje", "Por favor revise los valores enviados");
					response.put("Código de error:",CodigosErrorRest.COD_ERROR_UNO);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}		
			}

		} catch (DataAccessException e) {
			response.put("mensaje", error.getLitError() + " CÓDIGO DE ERROR: " + error.getCodError());
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return null;
			// new ResponseEntity<Clientes>(), HttpStatus.CREATED);
		}
		return null;

	}

	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> mostrarporId(@PathVariable Integer id) {

		Clientes c = null;
		Map<String, Object> response = new HashMap<>();

		/*
		 * if(id.getClass().getSimpleName() != "Integer"){ response.put("mensaje",
		 * "Error al realizar la consulta en la base de datos"); //response.put("error",
		 * e.getMessage().concat(e.getMostSpecificCause().getMessage())); return new
		 * ResponseEntity<Map<String,Object>>(response,
		 * HttpStatus.INTERNAL_SERVER_ERROR); }
		 */

		try {
			c = clienteServices.getClienteid(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (c == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Clientes>(c, HttpStatus.OK);

	}

	@PutMapping("/actualizarCliente/{id}")
	public ResponseEntity<?> update(@RequestBody Clientes c, @PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		Clientes cliente_actual = clienteServices.getClienteid(id);
		Clientes cliente_actualizado = null;

		if (cliente_actual == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			cliente_actual.setCIFNIF(c.getCIFNIF());
			cliente_actual.setDireccionFacturacion(c.getDireccionFacturacion());
			cliente_actual.setNombreCliente(c.getNombreCliente());
			cliente_actualizado = clienteServices.insert(cliente_actual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("cliente", cliente_actualizado);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}