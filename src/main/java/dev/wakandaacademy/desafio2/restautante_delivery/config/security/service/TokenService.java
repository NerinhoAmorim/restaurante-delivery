package dev.wakandaacademy.desafio2.restautante_delivery.config.security.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import dev.wakandaacademy.desafio2.restautante_delivery.credencial.domain.Credencial;
import dev.wakandaacademy.desafio2.restautante_delivery.handler.APIException;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TokenService {
//	@Value("${api.security.token.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		return gerarToken((Credencial) authentication.getPrincipal());
	}

	public String gerarToken(Credencial credencial) {
		try {
			log.info("[inicia] TokenService - criação de token");
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create().withIssuer("api-restaurante-delivery").withSubject(credencial.getUsername())
					.withExpiresAt(genExpirationDate()).sign(algorithm);
			log.info("[finaliza] TokenService - criação de token");
			return token;
		} catch (JWTCreationException ex) {
			throw APIException.build(HttpStatus.BAD_REQUEST, "Erro ao gerar o token.");
		}

	}

	private Instant genExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}

	public String validarToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String tokenExtraido = token.substring(7, token.length());
			return JWT.require(algorithm).withIssuer("api-restaurante-delivery").build().verify(tokenExtraido)
					.getSubject();
		} catch (JWTVerificationException e) {
			throw APIException.build(HttpStatus.FORBIDDEN, "O Token enviado está inválido. Tente novamente.");
		}
	}

	public String getSubject(String token) {
		try {
			var algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer("api-restaurante-delivery").build().verify(token).getSubject();
		} catch (JWTVerificationException e) {
			e.printStackTrace();
			throw APIException.build(HttpStatus.FORBIDDEN, "O token está inválido ou expirado.");
		}
	}

	public Optional<String> getEmailByBearerToken(String bearerToken) {
		log.info("[inicia] TokenService - getEmailByBearerToken");
		String token = bearerToken.substring(7, bearerToken.length());
		log.info(token);
		log.info("[finaliza] TokenService - getEmailByBearerToken");
		return Optional.ofNullable(this.getSubject(token));
	}

	public void consultaAdmin(String token) {
		if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(admin -> admin.getAuthority().equals("ADMIN"))) {
			throw APIException.build(HttpStatus.UNAUTHORIZED, "Acesso negado: você precisa ser um administrador.");
		}
		getEmailByBearerToken(token)
				.orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Admin não encontrado."));
	}

}
