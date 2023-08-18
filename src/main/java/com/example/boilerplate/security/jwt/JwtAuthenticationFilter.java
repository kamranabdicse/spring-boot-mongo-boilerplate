package com.example.boilerplate.security.jwt;

import com.example.boilerplate.security.service.UserDetailsServiceImpl;
import com.example.boilerplate.security.utils.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenManager jwtTokenManager;

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestURI = request.getRequestURI();

        if (requestURI.contains(SecurityConstants.LOGIN_REQUEST_URI) || requestURI.contains(SecurityConstants.REGISTRATION_REQUEST_URI) ){
            filterChain.doFilter(request, response);
            return;
        }

        final String header = request.getHeader(SecurityConstants.HEADER_STRING);
        String username = null;
        String authToken = null;
        if (Objects.nonNull(header) && header.contains(SecurityConstants.TOKEN_PREFIX)){

            authToken = header.replace(SecurityConstants.TOKEN_PREFIX, "");
            try{
                // extract username
                username = jwtTokenManager.getUsernameFromToken(authToken);
            }
            catch (Exception e){
                logger.debug("Authentication Exception : {}");
            }

            final SecurityContext securityContext = SecurityContextHolder.getContext();

            if(Objects.nonNull(username) && Objects.isNull(securityContext.getAuthentication())){

                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtTokenManager.validateToken(authToken, userDetails.getUsername())){
                    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    System.out.println("Authentication successful. Logged in username"+ username);
                    securityContext.setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        }
    }
}
