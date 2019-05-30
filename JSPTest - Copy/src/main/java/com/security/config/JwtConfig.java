package com.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="jwt")
public class JwtConfig {

	private Long tokenExpirationTimeMs;
	private String tokenIssuer;
	private String tokenSigningKey;
	private Long refreshTokenExpTimeMs;
	private Integer cookieMaxAge;
	
	public Long getTokenExpirationTimeMs() {
		return tokenExpirationTimeMs;
	}
	public void setTokenExpirationTimeMs(Long tokenExpirationTimeMs) {
		this.tokenExpirationTimeMs = tokenExpirationTimeMs;
	}
	public String getTokenIssuer() {
		return tokenIssuer;
	}
	public void setTokenIssuer(String tokenIssuer) {
		this.tokenIssuer = tokenIssuer;
	}
	public String getTokenSigningKey() {
		return tokenSigningKey;
	}
	public void setTokenSigningKey(String tokenSigningKey) {
		this.tokenSigningKey = tokenSigningKey;
	}
	public Long getRefreshTokenExpTimeMs() {
		return refreshTokenExpTimeMs;
	}
	public void setRefreshTokenExpTimeMs(Long refreshTokenExpTimeMs) {
		this.refreshTokenExpTimeMs = refreshTokenExpTimeMs;
	}
	public Integer getCookieMaxAge() {
		return cookieMaxAge;
	}
	public void setCookieMaxAge(Integer cookieMaxAge) {
		this.cookieMaxAge = cookieMaxAge;
	}
}
