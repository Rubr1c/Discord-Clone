package dev.rubric.discord.service;

import dev.rubric.discord.collections.User;
import dev.rubric.discord.exceptions.*;
import dev.rubric.discord.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserIdByUsername_Success() {
        String username = "testUser";
        Long expectedUserId = 1L;
        User mockUser = new User();
        mockUser.setUserId(expectedUserId);
        mockUser.setUsername(username);

        when(userRepository.getUserByUsername(username)).thenReturn(Optional.of(mockUser));

        Long userId = userService.getUserIdByUsername(username);

        assertEquals(expectedUserId, userId);
        verify(userRepository, times(1)).getUserByUsername(username);
    }

    @Test
    void testGetUserIdByUsername_UserNotFound() {
        String username = "nonexistentUser";
        when(userRepository.getUserByUsername(username)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () ->
                userService.getUserIdByUsername(username));
        assertEquals("User with username 'nonexistentUser' does not exist", ex.getMessage());
        verify(userRepository, times(1)).getUserByUsername(username);
    }

    @Test
    void testGetUsernameByUserId_Success() {
        Long userId = 1L;
        String expectedUsername = "testUser";
        User mockUser = new User();
        mockUser.setUserId(userId);
        mockUser.setUsername(expectedUsername);

        when(userRepository.getUserByUserId(userId)).thenReturn(Optional.of(mockUser));

        String username = userService.getUsernameByUserId(userId);

        assertEquals(expectedUsername, username);
        verify(userRepository, times(1)).getUserByUserId(userId);
    }

    @Test
    void testGetUsernameByUserId_UserNotFound() {
        Long userId = 1L;
        when(userRepository.getUserByUserId(userId)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () ->
                userService.getUsernameByUserId(userId));
        assertEquals("User with userId '1' does not exist", ex.getMessage());
        verify(userRepository, times(1)).getUserByUserId(userId);
    }

    @Test
    void testGetDisplayNameByUsername_Success() {
        String username = "testUser";
        String expectedDisplayName = "test user";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setDisplayName(expectedDisplayName);

        when(userRepository.getUserByUsername(username)).thenReturn(Optional.of(mockUser));

        String displayName = userService.getDisplayNameByUsername(username);

        assertEquals(expectedDisplayName, displayName);
        verify(userRepository, times(1)).getUserByUsername(username);
    }

    @Test
    void testGetDisplayNameByUsername_UserNotFound() {
        String username = "testUser";
        when(userRepository.getUserByUsername(username)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () ->
                userService.getDisplayNameByUsername(username));

        assertEquals("User with username 'testUser' does not exist", ex.getMessage());
        verify(userRepository, times(1)).getUserByUsername(username);
    }

    @Test
    void testGetDisplayNameByUserId_Success() {
        Long userId = 1L;
        String expectedDisplayName = "test user";
        User mockUser = new User();
        mockUser.setUserId(userId);
        mockUser.setDisplayName(expectedDisplayName);

        when(userRepository.getUserByUserId(userId)).thenReturn(Optional.of(mockUser));

        String displayName = userService.getDisplayNameByUserId(userId);

        assertEquals(expectedDisplayName, displayName);
        verify(userRepository, times(1)).getUserByUserId(userId);
    }

    @Test
    void testGetDisplayNameByUserId_UserNotFound() {
        Long userId = 1L;
        when(userRepository.getUserByUserId(userId)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () ->
                userService.getDisplayNameByUserId(userId));

        assertEquals("User with userId '1' does not exist", ex.getMessage());
        verify(userRepository, times(1)).getUserByUserId(userId);
    }

    @Test
    void testGenerateUniqueUserId() {
        when(userRepository.getUserByUserId(anyLong())).thenReturn(Optional.empty());
        Long userId = userService.generateUniqueUserId();

        assertEquals(18, String.valueOf(userId).length());
        verify(userRepository, times(1)).getUserByUserId(anyLong());
    }

    @Test
    void testIsValidPassword_Success() {
        String password = "Az2200418!";

        assertTrue(userService.isValidPassword(password));
    }

    @Test
    void testIsValidPassword_Short() {
        String password = "Az2!";

        assertFalse(userService.isValidPassword(password));
    }

    @Test
    void testIsValidPassword_Lower() {
        String password = "az2200418!";

        assertFalse(userService.isValidPassword(password));
    }

    @Test
    void testIsValidPassword_Char() {
        String password = "Az2200418";

        assertFalse(userService.isValidPassword(password));
    }

    @Test
    void testIsValidEmail_Success() {
        String email = "alizahloul64@gmail.com";

        assertTrue(userService.isValidEmail(email));
    }

    @Test
    void testIsValidEmail_Null() {
        assertFalse(userService.isValidEmail(null));
    }

    @Test
    void testIsValidEmail_Empty() {
        String email = "";

        assertFalse(userService.isValidEmail(email));
    }

    @Test
    void testIsValidEmail_IncorrectRegex() {
        String[] emails = {"123432.com", "23412", "huaduyahd", "com.cold@test"};

        for (String email : emails) {
            assertFalse(userService.isValidEmail(email));
        }
    }


}

