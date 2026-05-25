package connection;

import java.sql.Connection;

public interface DbConnection {
    Connection getConnection();
    void close();
}
