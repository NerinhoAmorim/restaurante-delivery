package dev.wakandaacademy.desafio2.restautante_delivery.pedido.infra;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import dev.wakandaacademy.desafio2.restautante_delivery.handler.APIException;
import dev.wakandaacademy.desafio2.restautante_delivery.pedido.application.repository.PedidoRepository;
import dev.wakandaacademy.desafio2.restautante_delivery.pedido.domain.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
@RequiredArgsConstructor
public class PedidoInfraRepository implements PedidoRepository {

	private final PedidoMongoSpringRepository pedidoMongoSpringRepository;

	@Override
	public Pedido salvaPedido(Pedido pedido) {
		log.info("[inicia] PedidoInfraRepository - salvaPedido");
		try {
			pedidoMongoSpringRepository.save(pedido);
		} catch (DataIntegrityViolationException e) {
			throw APIException.build(HttpStatus.BAD_REQUEST, "Existem dados duplicados.");
		}
		log.info("[finaliza] PedidoInfraRepository - salvaPedido");
		return pedido;
	}

	@Override
	public List<Pedido> buscaTodosPedidosDoCliente(UUID idCliente) {
		log.info("[inicia] PedidoInfraRepository - buscaTodosPedidosDoCliente");
		List<Pedido> pedidosDoCliente = pedidoMongoSpringRepository.findAllByIdCliente(idCliente);
		log.info("[finaliza] PedidoInfraRepository - buscaTodosPedidosDoCliente");
		return pedidosDoCliente;
	}

	@Override
    public Pedido buscaPedidoDoClientePorId(UUID idCliente, UUID idPedido) {
        log.info("[inicia] PedidoInfraRepository - buscaPedidoDoClientePorId");
        Pedido pedido = pedidoMongoSpringRepository.findByIdClienteAndIdPedido(idCliente, idPedido)
        		.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
        log.info("[finaliza] PedidoInfraRepository - buscaPedidoDoClientePorId");
        return pedido;
    }

	@Override
	public void deletaPedido(Pedido pedido) {
		log.info("[inicia] PedidoInfraRepository - deletaPedido");
        pedidoMongoSpringRepository.delete(pedido);
        log.info("[finaliza] PedidoInfraRepository - deletaPedido");
		
	}

}
