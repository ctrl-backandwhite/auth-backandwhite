package com.backandwhite.core.application.usecase.secutiry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorsConfigTest {

    @Mock
    private CorsRegistry registry;

    @Mock
    private CorsRegistration registration;

    @InjectMocks
    private CorsConfig corsConfig;

    @Test
    void addCorsMappings_shouldConfigureExpectedCorsPolicy() {

        when(registry.addMapping("/**")).thenReturn(registration);
        when(registration.allowedOrigins("http://localhost:4200")).thenReturn(registration);
        when(registration.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")).thenReturn(registration);
        when(registration.allowedHeaders("*")).thenReturn(registration);
        when(registration.allowCredentials(true)).thenReturn(registration);
        when(registration.maxAge(3600)).thenReturn(registration);
        when(registration.exposedHeaders("X-total-Count")).thenReturn(registration);

        corsConfig.addCorsMappings(registry);

        verify(registry, times(1)).addMapping("/**");
        verify(registration, times(1)).allowedOrigins("http://localhost:4200");
        verify(registration, times(1)).allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
        verify(registration, times(1)).allowedHeaders("*");
        verify(registration, times(1)).allowCredentials(true);
        verify(registration, times(1)).maxAge(3600);
        verify(registration, times(1)).exposedHeaders("X-total-Count");

        verifyNoMoreInteractions(registry);
        verifyNoMoreInteractions(registration);
    }

}