/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix;

/**
 *
 * @author juanm
 */
import java.sql.*;

public class DatabaseManager {

    // ===============================================
    // CONFIGURACIÓN DE LA CONEXIÓN
    // ===============================================
    private static final String URL = "jdbc:mysql://localhost:3306/netfix";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connection;
    private static DatabaseManager instance;

    // ===============================================
    // SINGLETON
    // ===============================================
    private DatabaseManager() {
        try {
            // Cargar el driver JDBC (opcional en Java 9+)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error cargando el driver JDBC", e);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    // ===============================================
    // GESTIÓN DE CONEXIÓN
    // ===============================================
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error cerrando la conexión: " + e.getMessage());
        }
    }

    // ===============================================
    // TRANSACCIONES
    // ===============================================
    public void beginTransaction() throws SQLException {
        getConnection().setAutoCommit(false);
    }

    public void commit() throws SQLException {
        getConnection().commit();
        getConnection().setAutoCommit(true);
    }

    public void rollback() {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Error en rollback: " + e.getMessage());
        }
    }

    // ===============================================
    // CONSULTAS COMUNES
    // ===============================================
    /**
     * Ejecuta INSERT, UPDATE o DELETE
     */
    public int executeUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = prepareStatement(sql, params)) {
            return stmt.executeUpdate();
        }
    }

    /**
     * Ejecuta SELECT y devuelve un ResultSet
     */
    public ResultSet executeQuery(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = prepareStatement(sql, params);
        return stmt.executeQuery(); // El ResultSet debe cerrarse externamente
    }

    // ===============================================
    // PREPARACIÓN DE SENTENCIAS
    // ===============================================
    private PreparedStatement prepareStatement(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        return stmt;
    }

    // ===============================================
    // MÉTODO DE PRUEBA
    // ===============================================
    public static void main(String[] args) {
        DatabaseManager db = DatabaseManager.getInstance();

        try {
            // ============================================
            // Obtener información de columnas dinámicamente
            // ============================================
            try (ResultSet rs = db.executeQuery(
                    "SELECT * "
                    + "FROM netfix.clientes AS t1 "
                    + "INNER JOIN netfix.contratos AS t2 ON t1.dni = t2.dni_cliente "
                    + "INNER JOIN netfix.aparatos AS t3 ON t2.id_contrato = t3.id_contrato;"
            )) {
                // ============================================
                // Obtener información de columnas dinámicamente
                // ============================================
                ResultSetMetaData meta = rs.getMetaData();
                int columnas = meta.getColumnCount();

                // ============================================
                // Imprimir nombres de columnas
                // ============================================
                System.out.println("===== RESULTADO =====");
                for (int i = 1; i <= columnas; i++) {
                    System.out.print(meta.getColumnName(i) + "\t");
                }
                System.out.println("\n----------------------------------------");

                // ============================================
                // Imprimir todas las filas
                // ============================================
                while (rs.next()) {
                    for (int i = 1; i <= columnas; i++) {
                        System.out.print(rs.getString(i) + "\t");
                    }
                    System.out.println();
                }
            }

        } catch (SQLException e) {
        } finally {
            db.closeConnection();

        }
    }
}
