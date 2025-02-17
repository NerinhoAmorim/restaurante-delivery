package dev.wakandaacademy.desafio2.restautante_delivery.config.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.wakandaacademy.desafio2.restautante_delivery.credencial.application.repository.CredencialRepository;
import dev.wakandaacademy.desafio2.restautante_delivery.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class AutenticacaoSecurityService implements UserDetailsService {
	@Autowired
	private final CredencialRepository credencialRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("[inicia] AutenticacaoSecurityService - buscando credencial pelo usuario");
		var credencial = credencialRepository.buscaCredencialPorEmail(email);
		log.info("[finaliza] AutenticacaoSecurityService - buscando credencial pelo usuario");
		return Optional.ofNullable(credencial).orElseThrow(
				() -> APIException.build(HttpStatus.NOT_FOUND, "Não existe credencial para o Email informado!"));
	}

}
