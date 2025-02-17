package dev.wakandaacademy.desafio2.restautante_delivery.pedido.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import dev.wakandaacademy.desafio2.restautante_delivery.pedido.application.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Validated
@RestController
@Log4j2
@RequiredArgsConstructor
public class PedidoController implements PedidoAPI {

	private final PedidoService pedidoService;

	@Override
	public PedidoResponse postClienteRealizaPedidoCriandoEndereco(String token, UUID idCliente,
			PedidoRequestCriandoEndereco pedidoRequest) {
		log.info("[inicia] PedidoController - postClienteRealizaPedidoCriandoEndereco");
		PedidoResponse pedido = pedidoService.clienteRealizaPedidoCriandoEndereco(token, idCliente, pedidoRequest);
		log.info("[finaliza] PedidoController - postClienteRealizaPedidoCriandoEndereco");
		return pedido;
	}

	@Override
	public PedidoResponse postClienteRealizaPedido(String token, UUID idCliente, PedidoRequest pedidoRequest) {
		log.info("[inicia] PedidoController - postClienteRealizaPedido");
		PedidoResponse pedido = pedidoService.clienteRealizaPedido(token, idCliente, pedidoRequest);
		log.info("[finaliza] PedidoController - postClienteRealizaPedido");
		return pedido;
	}

	@Override
	public List<PedidoListCliente> getTodosPedidosDoCliente(String token, UUID idCliente) {
		log.info("[inicia] PedidoController - getTodosPedidosDoCliente");
		List<PedidoListCliente> pedidos = pedidoService.buscaTodosPedidosDoCliente(token, idCliente);
		log.info("[finaliza] PedidoController - getTodosPedidosDoCliente");
		return pedidos;
	}

	@Override
	public PedidoDetalhadoCliente getPedidoDoClientePorId(String token, UUID idCliente, UUID idPedido) {
		log.info("[inicia] PedidoController - getPedidoDoClientePorId");
		PedidoDetalhadoCliente pedido = pedidoService.buscaPedidoDoClientePorId(token, idCliente, idPedido);
		log.info("[finaliza] PedidoController - getPedidoDoClientePorId");
		return pedido;
	}

	@Override
	public void patchEntregaPedidoDoCliente(String token, UUID idCliente, UUID idPedido) {
		log.info("[inicia] PedidoController - patchEntregaPedidoDoCliente");
		pedidoService.entregaPedidoDoCliente(token, idCliente, idPedido);
		log.info("[finaliza] PedidoController - patchEntregaPedidoDoCliente");

	}

	@Override
	public void patchRetiraEntregaPedidoDoCliente(String token, UUID idCliente, UUID idPedido) {
		log.info("[inicia] PedidoController - patchRetiraEntregaPedidoDoCliente");
		pedidoService.retiraEntregaPedidoDoCliente(token, idCliente, idPedido);
		log.info("[finaliza] PedidoController - patchRetiraEntregaPedidoDoCliente");

	}

	@Override
	public void patchPedidoDoClientePorId(String token, UUID idCliente, UUID idPedido,
			PedidoAlteracaoRequest pedidoAlteracaoRequest) {
		log.info("[inicia] PedidoController - patchPedidoDoClientePorId");
		pedidoService.alteraPedidoDoClientePorId(token, idCliente, idPedido, pedidoAlteracaoRequest);
		log.info("[finaliza] PedidoController - patchPedidoDoClientePorId");

	}

	@Override
	public void deletePedidoDoClientePorId(String token, UUID idCliente, UUID idPedido) {
		log.info("[inicia] PedidoController - deletePedidoDoClientePorId");
		pedidoService.deletaPedidoDoClientePorId(token, idCliente, idPedido);
		log.info("[finaliza] PedidoController - deletePedidoDoClientePorId");
	}

}
