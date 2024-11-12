package edu.badpals.empresa;

import java.sql.*;

public class DatabaseMetadatos {
    private final static String URL = "jdbc:mysql://localhost:3306/bdempresa";
    private final static String USER = "root";
    private final static String PASSWORD = "root";
    private static Connection connection = null;
    public static Statement statement = null;

    public static void conectarDB(){
        try{
            if(connection == null){
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }if (statement == null){
                statement = connection.createStatement();
            }
        }catch (SQLException e){
            System.out.println("Error al conectar la base de datos");
            e.printStackTrace();
        }
    }

    public static void desconectarDB(){
        if(statement != null){
            try{
                statement.close();
            }catch (SQLException e){
                System.out.println("Error al cerrar el statement");
            }finally{
                statement = null;
            }
        }if(connection != null){
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println("Error al cerrar la base de datos.");
            }finally{
                connection = null;
            }
        }
    }

    public static void mostrarInformacionBasica(){
        try{
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println("Nombre del SGBD: " + databaseMetaData.getDatabaseProductName());
            System.out.println("Versión del SGBD: " + databaseMetaData.getDatabaseProductVersion());
            System.out.println("Número de versión principal del SGBD: " + databaseMetaData.getDatabaseMajorVersion());
            System.out.println("Número de versión secundario del SGBD: " + databaseMetaData.getDatabaseMinorVersion());
            System.out.println("Conectador principal del SGBD: " + databaseMetaData.getDriverName());
            System.out.println("Número versión del conectador del SGBD: " + databaseMetaData.getDriverVersion());
            System.out.println("Número versión del conectador del SGBD: " + databaseMetaData.getDriverVersion());
            System.out.println("Número versión principal del conectador del SGBD: " + databaseMetaData.getDriverMajorVersion());
            System.out.println("Número versión secundario del conectador del SGBD: " + databaseMetaData.getDriverMinorVersion());
            System.out.println("Url de la BD: " + databaseMetaData.getURL());
            System.out.println("Nombre usuario conectado: " + databaseMetaData.getUserName());
            System.out.println(databaseMetaData.isReadOnly()? "Es de solo lectura": "No es de solo lectura");

        }catch (SQLException e){
            System.out.println("Error al cerrar la base de datos.");
        }
    }

}
