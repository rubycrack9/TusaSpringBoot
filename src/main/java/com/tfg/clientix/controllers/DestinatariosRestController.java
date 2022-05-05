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
import org.springframework.util.StringUtils;
import com.tfg.clientix.errorCharger.CodigosErrorRest;
import com.tfg.clientix.errorCharger.ErrorRest;
import com.tfg.clientix.models.entity.Clientes;
import com.tfg.clientix.models.entity.Destinatarios;
import com.tfg.clientix.services.IDestinatariosServices;
import com.tfg.clientix.validations.Validaciones;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("ProyectoFinal/webapi")
public class DestinatariosRestController {
	
	Map<String, Object> response = new HashMap<>();
	
	@Autowired
	private IDestinatariosServices destinatariosService;
	
	//CONSULTAR TODOS LOS DESTINATARIOS
	@GetMapping("/destinatarios")
	public List<Destinatarios> getDestinatarios() {
		return destinatariosService.getAllDestinatarios();

	}
	//INSERTAR UN CLIENTE
	@PostMapping("/insertarDestinatario")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> insert(@RequestBody Destinatarios d) {

		Destinatarios destinatarioNuevo = null;
		ErrorRest error = new ErrorRest();
		Map<String, Object> response = new HashMap<>();

		
		error = Validaciones.validarDestinatario(d, destinatariosService);

		if (error.getCodError().equals(CodigosErrorRest.COD_ERROR_CERO)
				&& error.getLitError().equals(CodigosErrorRest.LIT_ERROR_SUCCESS) && error.isValidado()) {
			destinatarioNuevo = destinatariosService.insertDestinatarios(d);
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
		 

		return new ResponseEntity<Destinatarios>(d, HttpStatus.CREATED);

	}
	//CONSULTAR UN DESTINATARIO POR ID
	@GetMapping("/destinatarios/{id}")
	public ResponseEntity<?> mostrarporId(@PathVariable Integer id) {

		Destinatarios d = null;
		Map<String, Object> response = new HashMap<>();

		try {
			d = destinatariosService.readDestinatarios(id);
		} catch (DataAccessException e) {
			response.put("Mensaje", "Error al realizar la consulta en la base de datos");
			response.put("Código de error:",CodigosErrorRest.COD_ERROR_DEFECTO);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (d == null) {
			response.put("Mensaje",
					"El cliente con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Destinatarios>(d, HttpStatus.OK);

	}

	// ACTUALIZAR DESTINATARIO
	@PutMapping("/actualizarDestinatario/{id}")
	public ResponseEntity<?> update(@RequestBody Destinatarios d, @PathVariable Integer id) {

		Destinatarios destinatario_actual = destinatariosService.readDestinatarios(id);
		Destinatarios destinatario_actualizado = null;

		if (destinatario_actual == null) {
			response.put("Mensaje", "No se pudo editar, el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos")));
			response.put("Código de error:", CodigosErrorRest.COD_ERROR_CLIENTE_NO_ENCONTRADO);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			ErrorRest error = new ErrorRest();

			error = Validaciones.validarDestinatario(d, destinatariosService);

			if (error.getCodError().equals(CodigosErrorRest.COD_ERROR_CERO)
					&& error.getLitError().equals(CodigosErrorRest.LIT_ERROR_SUCCESS) && error.isValidado()) {
				destinatario_actual.setDNINIF(d.getDNINIF());
				destinatario_actual.setDireccionCompleta(d.getDireccionCompleta());
				destinatario_actual.setNombreDestinatario(d.getNombreDestinatario());
				destinatario_actual.setCliente(d.getCliente());
				destinatario_actual.setCodigoPostal(d.getCodigoPostal());
				destinatario_actualizado = destinatariosService.insertDestinatarios(destinatario_actual);
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
			response.put("Cliente:", destinatario_actualizado);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		
	}

	// METODO BORRAR POR ID
	@DeleteMapping("borrarDestinario/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		destinatariosService.deletebyId(id);
		response.put("Mensaje", "Cliente borrado con éxito");
		response.put("Código de Error", CodigosErrorRest.COD_ERROR_CERO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
