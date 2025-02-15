package com.synopsis.infraestructura.keycloak_adapter.controller;

import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwk.Jwk;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.synopsis.infraestructura.keycloak_adapter.exception.BussinesRuleException;
import com.synopsis.infraestructura.keycloak_adapter.service.JwtService;
import com.synopsis.infraestructura.keycloak_adapter.service.KeycloakRestService;

@RestController
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private KeycloakRestService restService;

    @Autowired
    private JwtService jwtService;

    @SuppressWarnings("unchecked")
    @GetMapping("/roles")
    public ResponseEntity<?> getRoles(@RequestHeader("Authorization") String authHeader) throws BussinesRuleException {
        try {
            DecodedJWT jwt = JWT.decode(authHeader.replace("Bearer", "").trim());

            // check JWT is valid
            Jwk jwk = jwtService.getJwk();
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);

            algorithm.verify(jwt);

            // check JWT role is correct
            @SuppressWarnings("rawtypes")
            List<String> roles = ((List) jwt.getClaim("realm_access").asMap().get("roles"));

            // check JWT is still active
            Date expiryDate = jwt.getExpiresAt();
            if (expiryDate.before(new Date())) {
                throw new Exception("token is expired");
            }
            // all validation passed
            @SuppressWarnings("rawtypes")
            HashMap HashMap = new HashMap();
            for (String str : roles) {
                HashMap.put(str, str.length());
            }
            return ResponseEntity.ok(HashMap);
        } catch (Exception e) {
            logger.error("exception : {} ", e.getMessage());
            throw new BussinesRuleException("01", e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping("/valid")
    public ResponseEntity<?> valid(@RequestHeader("Authorization") String authHeader) throws BussinesRuleException {
        try {
            restService.checkValidity(authHeader);
            return ResponseEntity.ok(new HashMap() {
                {
                    put("is_valid", "true");
                }
            });
        } catch (Exception e) {
            logger.error("token is not valid, exception : {} ", e.getMessage());
            throw new BussinesRuleException("is_valid", "False", HttpStatus.FORBIDDEN);

        }
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(String username, String password) {
        String login = restService.login(username, password);
        return ResponseEntity.ok(login);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout(@RequestParam(value = "refresh_token", name = "refresh_token") String refreshToken)
            throws BussinesRuleException {
        try {
            restService.logout(refreshToken);
            return ResponseEntity.ok(new HashMap() {
                {
                    put("logout", "true");
                }
            });
        } catch (Exception e) {
            logger.error("unable to logout, exception : {} ", e.getMessage());
            throw new BussinesRuleException("logout", "False", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refresh(@RequestParam(value = "refresh_token", name = "refresh_token") String refreshToken)
            throws BussinesRuleException {
        try {
            return ResponseEntity.ok(restService.refresh(refreshToken));
        } catch (Exception e) {
            logger.error("unable to refresh, exception : {} ", e.getMessage());
            throw new BussinesRuleException("refresh", "False", HttpStatus.FORBIDDEN);
        }
    }
}
