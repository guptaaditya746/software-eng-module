import de.buw.se.Domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testGettersInitiallyNull() {
        assertNull(user.getName());
        assertNull(user.getPassword());
        assertNull(user.getPhoneNumber());
        assertNull(user.getMail());
    }

    @Test
    void testSettersAndGetters() {
        user.setName("John Doe");
        assertEquals("John Doe", user.getName());

        user.setPassword("password123");
        assertEquals("password123", user.getPassword());

        user.setPhoneNumber("123-456-7890");
        assertEquals("123-456-7890", user.getPhoneNumber());

        user.setMail("john.doe@example.com");
        assertEquals("john.doe@example.com", user.getMail());
    }

    @Test
    void testToString() {
        user.setName("John Doe");
        user.setPassword("password123");
        user.setPhoneNumber("123-456-7890");
        user.setMail("john.doe@example.com");

        String expected = "name: John Doe, password:password123, phone:123-456-7890, mail:john.doe@example.com";
        assertEquals(expected, user.toString());
    }

    @Test
    void testSettersWithNull() {
        user.setName(null);
        assertNull(user.getName());

        user.setPassword(null);
        assertNull(user.getPassword());

        user.setPhoneNumber(null);
        assertNull(user.getPhoneNumber());

        user.setMail(null);
        assertNull(user.getMail());
    }

    @Test
    void testToStringWithNullValues() {
        user.setName(null);
        user.setPassword(null);
        user.setPhoneNumber(null);
        user.setMail(null);

        String expected = "name: null, password:null, phone:null, mail:null";
        assertEquals(expected, user.toString());
    }

    @Test
    void testSettersWithMaxLength() {
        // Assuming maximum lengths for fields are defined as per application requirements
        String maxName = "JohnDoeJohnDoeJohnDoeJohnDoeJohnDoeJohnDoeJohnDoeJohnDoe";
        String maxPassword = "password123password123password123password123password123";
        String maxPhoneNumber = "12345678901234567890";
        String maxMail = "a".repeat(320) + "@example.com"; // 320 characters

        user.setName(maxName);
        assertEquals(maxName, user.getName());

        user.setPassword(maxPassword);
        assertEquals(maxPassword, user.getPassword());

        user.setPhoneNumber(maxPhoneNumber);
        assertEquals(maxPhoneNumber, user.getPhoneNumber());

        user.setMail(maxMail);
        assertEquals(maxMail, user.getMail());
    }

    @Test
    void testEquality() {
        User user1 = new User();
        user1.setName("John Doe");
        user1.setPassword("password123");
        user1.setPhoneNumber("1234567890");
        user1.setMail("john.doe@example.com");

        User user2 = new User();
        user2.setName("John Doe");
        user2.setPassword("password123");
        user2.setPhoneNumber("1234567890");
        user2.setMail("john.doe@example.com");

        User user3 = new User();
        user3.setName("Jane Smith");
        user3.setPassword("password456");
        user3.setPhoneNumber("9876543210");
        user3.setMail("jane.smith@example.com");

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
    }

    @Test
    void testEqualsWithNull() {
        User user1 = new User();
        User user2 = null;

        assertNotEquals(user1, user2);
    }

    @Test
    void testEqualsWithDifferentObjectTypes() {
        User user1 = new User();
        user1.setName("John Doe");
        user1.setPassword("password123");
        user1.setPhoneNumber("1234567890");
        user1.setMail("john.doe@example.com");

        assertNotEquals(user1, "John Doe");
    }

    @Test
    void testEqualsWithSameReference() {
        User user1 = new User();
        User user2 = user1;

        assertEquals(user1, user2);
    }

    @Test
    void testEqualsWithDifferentMail() {
        User user1 = new User();
        user1.setName("John Doe");
        user1.setPassword("password123");
        user1.setPhoneNumber("1234567890");
        user1.setMail("john.doe@example.com");

        User user2 = new User();
        user2.setName("John Doe");
        user2.setPassword("password123");
        user2.setPhoneNumber("1234567890");
        user2.setMail("j.doe@example.com");

        assertNotEquals(user1, user2);
    }

    @Test
    void testEqualsWithDifferentPhoneNumber() {
        User user1 = new User();
        user1.setName("John Doe");
        user1.setPassword("password123");
        user1.setPhoneNumber("1234567890");
        user1.setMail("john.doe@example.com");

        User user2 = new User();
        user2.setName("John Doe");
        user2.setPassword("password123");
        user2.setPhoneNumber("1234567899");
        user2.setMail("john.doe@example.com");

        assertNotEquals(user1, user2);
    }

}
