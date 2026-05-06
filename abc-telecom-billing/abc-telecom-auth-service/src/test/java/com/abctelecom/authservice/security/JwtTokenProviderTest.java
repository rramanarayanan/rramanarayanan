package com.abctelecom.authservice.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JWT Token Provider Tests")
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private static final String TEST_SECRET = "MySecretKeyForABCTelecomBillingSystemWithAtLeast256BitsOfRandomData";
    private static final long TEST_EXPIRATION = 86400000; // 24 hours

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", TEST_SECRET);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", TEST_EXPIRATION);
    }

    @Test
    @DisplayName("Should generate valid JWT token")
    void testGenerateToken() {
        // Act
        String token = jwtTokenProvider.generateToken("testuser", "CUSTOMER");

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    @DisplayName("Should extract username from token")
    void testGetUsernameFromToken() {
        // Arrange
        String token = jwtTokenProvider.generateToken("testuser", "CUSTOMER");

        // Act
        String username = jwtTokenProvider.getUsernameFromToken(token);

        // Assert
        assertEquals("testuser", username);
    }

    @Test
    @DisplayName("Should extract role from token")
    void testGetRoleFromToken() {
        // Arrange
        String token = jwtTokenProvider.generateToken("testuser", "ADMIN");

        // Act
        String role = jwtTokenProvider.getRoleFromToken(token);

        // Assert
        assertEquals("ADMIN", role);
    }

    @Test
    @DisplayName("Should validate valid token")
    void testValidateValidToken() {
        // Arrange
        String token = jwtTokenProvider.generateToken("testuser", "CUSTOMER");

        // Act
        boolean isValid = jwtTokenProvider.validateToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should invalidate malformed token")
    void testValidateMalformedToken() {
        // Arrange
        String malformedToken = "malformed.token.here";

        // Act
        boolean isValid = jwtTokenProvider.validateToken(malformedToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should invalidate empty token")
    void testValidateEmptyToken() {
        // Act
        boolean isValid = jwtTokenProvider.validateToken("");

        // Assert
        assertFalse(isValid);
    }
}
