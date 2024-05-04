package program.database;

import program.model.metainfo.Consumer;
import program.model.metainfo.Line;
import program.model.metainfo.Source;
import program.utils.Constant;

import java.sql.*;
import java.util.ArrayList;

public class PostgresSecondHandler {
    private Connection connection;
    private static volatile PostgresSecondHandler INSTANCE;

    private PostgresSecondHandler() {
        try {
            this.connection = DriverManager.getConnection(
                    Constant.SECOND_CLIENT_URL, Constant.SECOND_CLIENT_USER, Constant.SECOND_CLIENT_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    public static PostgresSecondHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (PostgresSecondHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PostgresSecondHandler();
                }
            }
        }
        return INSTANCE;
    }

    private void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public ArrayList<Line> readLines() {
        ArrayList<Line> list = new ArrayList<>();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Lines;")) {

            while (rs.next()) {
                list.add(new Line(
                        rs.getInt("ID"),
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getInt("throughput"),
                        rs.getInt("resistance"),
                        rs.getFloat("cost")
                ));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public ArrayList<Source> readSources() {
        ArrayList<Source> list = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Sources;")) {

            while (rs.next()) {
                list.add(new Source(
                        rs.getInt("ID"),
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getInt("max_gen"),
                        rs.getInt("min_gen"),
                        rs.getFloat("price"),
                        rs.getFloat("cost"),
                        rs.getFloat("efficiency")
                ));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public ArrayList<Consumer> readConsumers() {
        ArrayList<Consumer> list = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Consumers;")) {

            while (rs.next()) {
                list.add(new Consumer(
                        rs.getInt("ID"),
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getInt("load")
                ));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
