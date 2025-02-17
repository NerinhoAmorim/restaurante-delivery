package dev.wakandaacademy.desafio2.restautante_delivery.cliente.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/public/v1/cliente")
public interface ClienteAPI {
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	ClienteResponse postCliente(@Valid @RequestBody ClienteRequest clienteRequest);

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	List<ClienteListResponse> getTodosClientes();

	@GetMapping("/{idCliente}")
	@ResponseStatus(code = HttpStatus.OK)
	ClienteDetalhadoResponse getClientePorId(@PathVariable UUID idCliente);

	@PatchMapping("/{idCliente}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	void patchClientePorId(@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable UUID idCliente, @RequestBody ClienteAlteracaoRequest clienteAlteracaoRequest);

	@DeleteMapping("/{idCliente}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	void deleteClientePorId(@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable UUID idCliente);
}
