package example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

class DBUtilTest {

    @Mock
    private Connection mockConnection;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getConnectionSuccessfully() throws SQLException {
        try (MockedStatic<DriverManager> dummy = Mockito.mockStatic(DriverManager.class)) {
            dummy.when(() -> DriverManager.getConnection(anyString(),anyString(),anyString()))
                    .thenReturn(mockConnection);
            Connection actualConnection = DBUtil.getConnection();

            dummy.verify(() -> DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/person_db", "root", "Password@123"));
            assertSame(mockConnection, actualConnection);
        }
    }

    @Test
    public void testGetConnectionThrowsSQLException(){
        try (MockedStatic<DriverManager> dummy = Mockito.mockStatic(DriverManager.class)) {
            dummy.when(() -> DriverManager.getConnection(anyString(),anyString(),anyString()))
                    .thenThrow(SQLException.class);

            assertThrows(SQLException.class, DBUtil::getConnection);
        }
    }
}