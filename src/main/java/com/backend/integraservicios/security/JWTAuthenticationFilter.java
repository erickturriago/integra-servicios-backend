package com.backend.integraservicios.security;

import com.backend.integraservicios.dto.entrada.usuario.UsuarioLoginEntradaDto;
import com.backend.integraservicios.dto.salida.usuario.UsuarioSalidaDto;
import com.backend.integraservicios.service.impl.UsuarioService;
import com.backend.integraservicios.utils.JsonPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    UsuarioService usuarioService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        System.out.println("attemptAuthentication");
        AuthCredentials authCredentials = new AuthCredentials();

        try{
            authCredentials = new ObjectMapper().readValue(request.getReader(),AuthCredentials.class);
        }catch (IOException e){

        }

        System.out.println(authCredentials);

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getContraseña()
        );

        System.out.println(usernamePAT);

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token = TokenUtils.createToken(userDetails.getNombre(),userDetails.getUsername());

        System.out.println("Token JWT generado: " + token);

        // Configurar el encabezado de autorización
        response.addHeader("Authorization", "Bearer " + token);

        // Construir el cuerpo de la respuesta
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("nombre", userDetails.getNombre());
        responseBody.put("cedula", userDetails.getCedula());
        responseBody.put("email", userDetails.getUsername());
        responseBody.put("rol", userDetails.getAuthorities().iterator().next().getAuthority());
        // Agrega más información si es necesario

        // Convertir el cuerpo de la respuesta a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBodyJson = objectMapper.writeValueAsString(responseBody);

        // Configurar el encabezado de respuesta para indicar el tipo de contenido
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Escribir el cuerpo de la respuesta en el HttpServletResponse
        response.getWriter().write(responseBodyJson);
        response.getWriter().flush();

        System.out.println("successfulAuthentication");

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
