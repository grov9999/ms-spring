package com.synopsis.infraestructura.keycloak_adapter.service;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.UrlJwkProvider;

@Service
public class JwtService {

    @Value("${keycloak.jwk-set-uri}")
    private String jwksUrl;

    @Value("${keycloak.certs-id}")
    private String certsId;

    @Cacheable(value = "jwkCache")
    public Jwk getJwk() throws Exception {
        URL url = new URL(jwksUrl);
        UrlJwkProvider urlJwkProvider = new UrlJwkProvider(url);
        Jwk get = urlJwkProvider.get(certsId.trim());
        return get;
    }
}
