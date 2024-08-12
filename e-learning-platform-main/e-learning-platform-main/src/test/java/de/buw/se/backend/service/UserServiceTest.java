package de.buw.se.backend.service;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.lang.reflect.Field;
import java.sql.SQLException;


import de.buw.se.backend.db.UserDao;
import de.buw.se.backend.model.User;

public class UserServiceTest {

    @Mock
    private UserDao userDaoMock;
    private UserService userService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        userService = new UserService();
        Field userDaoField = UserService.class.getDeclaredField("userDao");
        userDaoField.setAccessible(true);
        userDaoField.set(userService, userDaoMock);
    }

    @Test
    void testRegisterUser_Success() throws SQLException, ClassNotFoundException {

        Mockito.when(userDaoMock.addUser(Mockito.any(User.class))).thenReturn(true);
        boolean result = userService.registerUser("test@example.com", "testuser", "Test User", "password");
        Assertions.assertTrue(result, "User registration should succeed");
    }

    @Test
    void testRegisterUser_Failure_UsernameTaken() throws SQLException, ClassNotFoundException {

        Mockito.when(userDaoMock.addUser(Mockito.any(User.class))).thenReturn(false);
        boolean result = userService.registerUser("test@example.com", "testuser", "Test User", "password");
        Assertions.assertFalse(result, "User registration should fail due to username or email already taken");
    }


    @Test
    void testRegisterUser_Exception() throws SQLException, ClassNotFoundException {

        Mockito.when(userDaoMock.addUser(Mockito.any(User.class))).thenThrow(SQLException.class);
        boolean result = userService.registerUser("test@example.com", "testuser", "Test User", "password");
        Assertions.assertFalse(result, "User registration should fail due to SQLException");
    }

    @Test
    void testLoginUser_Success() throws SQLException, ClassNotFoundException {
        User mockUser = new User(1, "test@example.com", "testuser", "Test User", "password");
        Mockito.when(userDaoMock.getUserByUsername("testuser")).thenReturn(mockUser);
        LoginResult result = userService.loginUser("testuser", "password");
        Assertions.assertTrue(result.isSuccess(), "User login should succeed");
        Assertions.assertEquals(mockUser, result.getUserData(), "Returned user should match expected user");
    }

    @Test
    void testLoginUser_IncorrectPassword() throws SQLException, ClassNotFoundException {
        User mockUser = new User(1, "test@example.com", "testuser", "Test User", "password");
        Mockito.when(userDaoMock.getUserByUsername("testuser")).thenReturn(mockUser);
        LoginResult result = userService.loginUser("testuser", "wrongpassword");
        Assertions.assertFalse(result.isSuccess(), "User login should fail due to incorrect password");
        Assertions.assertNull(result.getUserData(), "User object should be null on failed login");
    }

    @Test
    void testLoginUser_UserNotFound() throws SQLException, ClassNotFoundException {
        Mockito.when(userDaoMock.getUserByUsername("unknownuser")).thenReturn(null);
        LoginResult result = userService.loginUser("unknownuser", "password");
        Assertions.assertFalse(result.isSuccess(), "User login should fail due to user not found");
        Assertions.assertNull(result.getUserData(), "User object should be null when user not found");
    }


    @Test
    void testLoginUser_Exception() throws SQLException, ClassNotFoundException {
        Mockito.when(userDaoMock.getUserByUsername("testuser")).thenThrow(SQLException.class);
        LoginResult result = userService.loginUser("testuser", "password");
        Assertions.assertFalse(result.isSuccess(), "User login should fail due to SQLException");
        Assertions.assertNull(result.getUserData(), "User object should be null on login exception");
    }


    @Test

    void testEditProfile_Success() throws SQLException, ClassNotFoundException {
        User mockUser = new User(1, "newemail@example.com", "testuser", "New Name", "newpassword");
        Mockito.when(userDaoMock.editProfile(Mockito.any(User.class))).thenReturn(true);
        Mockito.when(userDaoMock.getUserByUsername("testuser")).thenReturn(mockUser);
        LoginResult result = userService.editProfile(1, "newemail@example.com", "testuser", "New Name", "newpassword");
        Assertions.assertTrue(result.isSuccess(), "Profile edit should succeed");
        Assertions.assertEquals("newemail@example.com", result.getUserData().getEmail(), "Updated email should match");
        Assertions.assertEquals("New Name", result.getUserData().getFullName(), "Updated full name should match");
        Assertions.assertEquals("newpassword", result.getUserData().getPassword(), "Updated password should match");
    }

    @Test
    void testEditProfile_UsernameTaken() throws SQLException, ClassNotFoundException {
        Mockito.when(userDaoMock.editProfile(Mockito.any(User.class))).thenReturn(false);
        LoginResult result = userService.editProfile(1, "test@example.com", "testuser", "Test User", "password");
        Assertions.assertFalse(result.isSuccess(), "Profile edit should fail due to username or email taken");
        Assertions.assertNull(result.getUserData(), "User object should be null on failed profile edit");
    }

    @Test
    void testEditProfile_Exception() throws SQLException, ClassNotFoundException {
        Mockito.when(userDaoMock.editProfile(Mockito.any(User.class))).thenThrow(SQLException.class);
        LoginResult result = userService.editProfile(1, "test@example.com", "testuser", "Test User", "password");
        Assertions.assertFalse(result.isSuccess(), "Profile edit should fail due to SQLException");
        Assertions.assertNull(result.getUserData(), "User object should be null on profile edit exception");
    }

    // @Test
    
    // void testRegisterUser_Failure_DueToUsernameTaken() throws SQLException {
    //     // Assume userDaoMock returns true when addUser is called with  a user 
    //     Mockito.when(userDaoMock.addUser(Mockito.any(User.class))).thenReturn(true);

    //     // the method with parameters is called  that would result in failure due to username or email being already  taken
    //     boolean result = userService.registerUser("test@example.com", "testuser", "Test User", "password");

    //     // the registration should fail due to username or email already taken
    //     Assertions.assertFalse(result, "User registration should fail due to username or email already taken");
    // }
}
