package edu.badpals.empresa;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatabaseManager {
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

    public static void subirSalarioDepartamento(int cantidadSubida, int numDepartamento){
        try{
            int numFilasAfectadas = statement.executeUpdate("update empregado set salario = salario +" + cantidadSubida + " where num_departamento_pertenece ="+numDepartamento+";" );
            if(numFilasAfectadas > 0){
                statement.executeQuery("select nome_departamento from departamento where NUM_DEPARTAMENTO ="+numDepartamento+";");
                ResultSet rs = statement.getResultSet();
                while(rs.next()){
                    String nome_departamento = rs.getString("nome_departamento");
                    System.out.println("Se ha subido el salario de "+numFilasAfectadas+" empleados del departamento "+nome_departamento+" la cantidad de "+cantidadSubida+ "." );
                }
                rs.close();
            }else{
                System.out.println("No se ha subido el salario de ningún trabajador. Es posible que el número de departamento introducido no exista o no tenga empleados asignados.");
            }

        }catch (SQLException e){
            System.out.println("Error al subir el salario");
        }
    }

    public static void crearDepartamento(int numDepartamento, String nomeDepartamento, String nssDirige){
        try{
            ResultSet chkNumDepartamento = statement.executeQuery("select 1 from departamento where NUM_DEPARTAMENTO ="+numDepartamento+";");
            if(chkNumDepartamento.next()){
                    System.out.println("El número de departamento "+numDepartamento+" ya existe.");
                    return;
                }
            chkNumDepartamento.close();

            ResultSet chkNombreDepartamento = statement.executeQuery("select 1 from departamento where NOME_DEPARTAMENTO ='"+nomeDepartamento+"';");
            if(chkNombreDepartamento.next()){
                System.out.println("El nombre de departamento introducido ya existe en la base de datos");
                return;
            }
            chkNombreDepartamento.close();

            ResultSet chkEmpleado = statement.executeQuery("select 1 from empregado where NSS = '"+nssDirige+"'");
            if(!chkEmpleado.next()){
                System.out.println("El empleado introducido no existe");
                return;
            }
            chkEmpleado.close();

            LocalDate fechaActual = LocalDate.now();
            statement.executeUpdate("INSERT INTO DEPARTAMENTO(NUM_DEPARTAMENTO,NOME_DEPARTAMENTO,NSS_DIRIGE,DATA_DIRECCION) VALUES ("+numDepartamento+",'"+nomeDepartamento+"','"+nssDirige+"','"+fechaActual+"') ");
            System.out.println("El departamento " + nomeDepartamento +" se ha creado exitosamente.");

        }catch (SQLException e){
            System.out.println("Error al crear el departamento.");
            e.printStackTrace();
        }
    }

    public static void borrarEmpleadoProyecto(String nss, int numProyecto){
        try{
            int numFilasAfectadas = statement.executeUpdate("delete from empregado_proxecto where NSS_EMPREGADO = '"+nss+"' and NUM_PROXECTO = "+numProyecto+";");
            System.out.println("Registro de proyecto borrado. Filas afectadas: "+numFilasAfectadas);
        }catch (SQLException e){
            System.out.println("Error al borrar el registro de proyecto");
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

}
