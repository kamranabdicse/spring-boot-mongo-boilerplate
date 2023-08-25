package com.example.boilerplate.security.jwt;

import com.example.boilerplate.exceptions.UserAuthenticationException;
import com.example.boilerplate.security.service.UserDetailsServiceImpl;
import com.example.boilerplate.security.dto.AuthenticatedUserDto;
import com.example.boilerplate.security.utils.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenManager jwtTokenManager;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String requestURI = request.getRequestURI();

        if (requestURI.contains(SecurityConstants.LOGIN_REQUEST_URI) || requestURI.contains(SecurityConstants.REGISTRATION_REQUEST_URI) ){
            filterChain.doFilter(request, response);
            return;
        }
        final String header = request.getHeader(SecurityConstants.HEADER_STRING);
        String username = null;
        String authToken = null;
        int switchValue = -1;

        if (Objects.nonNull(header)) {
            if (header.startsWith(SecurityConstants.JWT_PREFIX)) {
                switchValue = 0;
            }
        }

        switch (switchValue) {
            case 0:
                authToken = header.replace(SecurityConstants.JWT_PREFIX, "");
                try {
                    username = jwtTokenManager.getUsernameFromToken(authToken);
                } catch (Exception e) {
                    throw new UserAuthenticationException("Authentication Exception");
                }

                final SecurityContext securityContext = SecurityContextHolder.getContext();

                if (Objects.nonNull(username) && Objects.isNull(securityContext.getAuthentication())) {

                    final AuthenticatedUserDto authenticatedUserDto = userDetailsService.loadUserByUsername(username);

                    if (jwtTokenManager.validateToken(authToken, authenticatedUserDto.getUsername())) {
                        Authentication authentication = new AuthenticationImpl(authenticatedUserDto);
                        log.info("Authentication successful. Logged in username: {}", username);
                        securityContext.setAuthentication(authentication);
                    }
                }
                filterChain.doFilter(request, response);
                break;
            default:
                filterChain.doFilter(request, response);
                break;
        }
    }
}
