package by.training.vashkevichyura.connection;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

public class connection {

    public static void main(String[] args) {
        Logger LOGGER = LogManager.getLogger(Connection.class);
        Statement statement = null;
        ResultSet resultSet = null;


//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement("SELECT *FROM users WHERE name=?")){
//           PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET name=? WHERE id=?");
//
//           //SELECT ALL MALES
//            statement.setString(1,"yura");
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()){
//                LOGGER.info(rs.getRow() + " " + rs.getString("surname"));
//
//            }
//            //changed name by number id
//            updateStatement.setString(1,"Petr");
//            updateStatement.setInt(2,2);
//            updateStatement.executeUpdate();
//
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//  }
    }

    }