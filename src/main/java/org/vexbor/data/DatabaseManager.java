package org.vexbor.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private final String url;

    public DatabaseManager(String path){
      try {
        Path parent = Path.of(path).toAbsolutePath().getParent();
        if (parent != null) Files.createDirectories(parent);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      this.url = "jdbc:sqlite:" + path;
    }

    public Connection getConnection(){
      try {
        Connection con = DriverManager.getConnection(url);
        return con;
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }

    public void init(){
      String categories = """
          CREATE TABLE IF NOT EXISTS categories (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL UNIQUE
          )
          """;

      String products = """
          CREATE TABLE IF NOT EXISTS products (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            price DECIMAL(10, 2) NOT NULL,
            category_id INTEGER NOT NULL,
            FOREIGN KEY (category_id) REFERENCES categories(id)
          )
          """;

      try (Connection con = getConnection();
           Statement statement = con.createStatement()) {
        statement.executeUpdate(categories);
        statement.executeUpdate(products);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
}
