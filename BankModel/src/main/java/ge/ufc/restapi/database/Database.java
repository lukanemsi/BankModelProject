package ge.ufc.restapi.database;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
  private static final Logger logger = Logger.getLogger(Database.class);
  private static Database instance;
  private final Connection connection;
  private static final String url;
  private static final String user;
  private static final String password;
  private static final String fileName = "database.properties";

  static {
    try {
      URL resource = Database.class.getClassLoader().getResource(fileName);
      if(resource == null){
        logger.fatal("Properties File not found");
        throw new RuntimeException(new IOException("missing File"));
      }
      URLConnection urlConnection = resource.openConnection();
      try(InputStream inputStream = urlConnection.getInputStream())
      {
        Properties properties = new Properties();
        properties.load(inputStream);
        url = properties.getProperty("url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");
      }
    } catch (IOException e) {
      logger.error(e);
      throw new RuntimeException(e);
    }
  }

  private Database() throws SQLException {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    this.connection = DriverManager.getConnection(url, user, password);
  }

  public Connection getConnection() {
    return connection;
  }

  public static Database getInstance() throws SQLException {
    if (instance == null) {
      instance = new Database();
    } else if (instance.getConnection().isClosed()) {
      instance = new Database();
    }

    return instance;
  }
}