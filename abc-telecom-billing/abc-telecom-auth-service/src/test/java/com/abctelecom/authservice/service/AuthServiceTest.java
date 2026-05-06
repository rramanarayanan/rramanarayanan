package com.abctelecom.authservice.service;

import com.abctelecom.authservice.dto.AuthResponse;
import com.abctelecom.authservice.dto.LoginRequest;
import com.abctelecom.authservice.dto.RegisterRequest;
import com.abctelecom.authservice.entity.Role;
import com.abctelecom.authservice.entity.User;
import com.abctelecom.authservice.repository.UserRepository;
import com.abctelecom.authservice.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Auth Service Unit Tests")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User testUser;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setConfirmPassword("password123");
        registerRequest.setRole(Role.CUSTOMER);

        loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("testuser");
        loginRequest.setPassword("password123");

        testUser = User.builder()
                .userId(1L)
                .username("testuser")
                .email("test@example.com")
                .passwordHash("hashedPassword")
                .role(Role.CUSTOMER)
                .build();
    }

    @Test
    @DisplayName("Should successfully register a new user")
    void testRegisterSuccess() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(jwtTokenProvider.generateToken("testuser", "CUSTOMER")).thenReturn("jwtToken");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        AuthResponse response = authService.register(registerRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("CUSTOMER", response.getRole());
        assertEquals("User registered successfully", response.getMessage());

        // Verify
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should fail to register when passwords do not match")
    void testRegisterPasswordMismatch() {
        // Arrange
        registerRequest.setConfirmPassword("wrongPassword");

        // Act
        AuthResponse response = authService.register(registerRequest);

        // Assert
        assertNull(response.getToken());
        assertEquals("Passwords do not match", response.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should fail to register when username already exists")
    void testRegisterUsernameExists() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // Act
        AuthResponse response = authService.register(registerRequest);

        // Assert
        assertNull(response.getToken());
        assertEquals("Username already exists", response.getMessage());
    }

    @Test
    @DisplayName("Should fail to register when email already exists")
    void testRegisterEmailExists() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // Act
        AuthResponse response = authService.register(registerRequest);

        // Assert
        assertNull(response.getToken());
        assertEquals("Email already exists", response.getMessage());
    }

    @Test
    @DisplayName("Should successfully login user with valid credentials")
    void testLoginSuccess() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123", "hashedPassword")).thenReturn(true);
        when(jwtTokenProvider.generateToken("testuser", "CUSTOMER")).thenReturn("jwtToken");

        // Act
        AuthResponse response = authService.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("CUSTOMER", response.getRole());
        assertEquals("Login successful", response.getMessage());
    }

    @Test
    @DisplayName("Should fail login when user not found")
    void testLoginUserNotFound() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Act
        AuthResponse response = authService.login(loginRequest);

        // Assert
        assertNull(response.getToken());
        assertEquals("Invalid credentials", response.getMessage());
    }

    @Test
    @DisplayName("Should fail login when password is incorrect")
    void testLoginInvalidPassword() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123", "hashedPassword")).thenReturn(false);

        // Act
        AuthResponse response = authService.login(loginRequest);

        // Assert
        assertNull(response.getToken());
        assertEquals("Invalid credentials", response.getMessage());
    }

    @Test
    @DisplayName("Should validate token successfully")
    void testValidateToken() {
        // Arrange
        String token = "validToken";
        when(jwtTokenProvider.validateToken(token)).thenReturn(true);

        // Act
        boolean result = authService.validateToken(token);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should fail to validate invalid token")
    void testValidateInvalidToken() {
        // Arrange
        String token = "invalidToken";
        when(jwtTokenProvider.validateToken(token)).thenReturn(false);

        // Act
        boolean result = authService.validateToken(token);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should extract username from token")
    void testGetUsernameFromToken() {
        // Arrange
        String token = "validToken";
        when(jwtTokenProvider.getUsernameFromToken(token)).thenReturn("testuser");

        // Act
        String username = authService.getUsernameFromToken(token);

        // Assert
        assertEquals("testuser", username);
    }
}
