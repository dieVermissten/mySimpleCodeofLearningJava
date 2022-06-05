import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

public class QueryDatabase {
    private static Properties properties;
    private static String databasepath;

    public QueryDatabase() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("database.properties"));
            Class.forName(properties.getProperty("database_name"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        databasepath = properties.getProperty("database_route") + properties.getProperty("database_path");
    }
    public String query() {
        StringBuffer stringBuffer = new StringBuffer();
        try (Connection connection =
                     DriverManager.getConnection(databasepath);
             PreparedStatement statement =
                     connection.prepareStatement(
                             "select * from company;");
             ResultSet resultSet = statement.executeQuery()
        ) {
            for (int i = 0; resultSet.next(); i++) {
                stringBuffer.append(resultSet.getString(1))
                        .append(" ")
                        .append(resultSet.getString(2))
                        .append(" ")
                        .append(resultSet.getString(3))
                        .append(" ")
                        .append(resultSet.getString(4))
                        .append(" </br> ");

            }
            return stringBuffer.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }
    public boolean update(HashMap<String,String> paraMap) {
        String sqlupdate="INSERT INTO company(ID,NAME,AGE,ADDRESS) VALUES (null,\"%s\",%s,\"%s\");";
        sqlupdate=String.format(sqlupdate,
                paraMap.get("NAME"),
                paraMap.get("AGE"),
                paraMap.get("ADDRESS"));
        System.out.println(sqlupdate);
        StringBuffer stringBuffer = new StringBuffer();
        try (Connection connection =
                     DriverManager.getConnection(databasepath);
             PreparedStatement statement =
                     connection.prepareStatement(sqlupdate);
        ) {
            if(statement.execute()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
