package edu.badpals.empresa;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    /************************ EJERCICIO 2.1
                                           *****************************/

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
        }
    }

    public static void borrarEmpleadoProyecto(String nss, int numProyecto){
        try{
            ResultSet chkEmpleado = statement.executeQuery("select 1 from empregado where NSS = '"+nss+"'");
            if(!chkEmpleado.next()){
                System.out.println("El empleado introducido no existe");
                return;
            }
            chkEmpleado.close();

            ResultSet chkProyecto = statement.executeQuery("select 1 from proxecto where NUM_PROXECTO = "+numProyecto+"");
            if(!chkProyecto.next()){
                System.out.println("El número de proyecto introducido no existe");
                return;
            }
            chkProyecto.close();

            int numFilasAfectadas = statement.executeUpdate("delete from empregado_proxecto where NSS_EMPREGADO = '"+nss+"' and NUM_PROXECTO = "+numProyecto+";");
            System.out.println("Registro de proyecto borrado. Filas afectadas: "+numFilasAfectadas);
        }catch (SQLException e){
            System.out.println("Error al borrar el registro de proyecto");
        }
    }

    /************************ EJERCICIO 2.2
                                            *****************************/

    public static void getEmpleadosLocalidad(String localidad){
        try{
            ResultSet rs = statement.executeQuery("select\n" +
                    "NOME,APELIDO_1,APELIDO_2,LOCALIDADE,SALARIO,DATA_NACEMENTO,\n" +
                    "            (\n" +
                    "            select NOME\n" +
                    "                from empregado\n" +
                    "                where nss = e1.NSS_SUPERVISA\n" +
                    "           ) as nome_xefe,\n" +
                    "            (\n" +
                    "                select NOME_DEPARTAMENTO\n" +
                    "                from departamento\n" +
                    "                where NUM_DEPARTAMENTO = e1.NUM_DEPARTAMENTO_PERTENECE\n" +
                    "                ) as departamento\n" +
                    "    from empregado as e1\n" +
                    "    where LOCALIDADE = '"+localidad+"'");
            if(!rs.next()){
                System.out.println("No hay empleados registrados de "+localidad);
            }else{
                System.out.println("Los empleados de "+localidad+" registrados son: ");
                while (rs.next()){
                    String nome = rs.getString("NOME");
                    String apelido1 = rs.getString("APELIDO_1");
                    String apelido2 = rs.getString("APELIDO_2");
                    String localidade = rs.getString("LOCALIDADE");
                    double salario = rs.getDouble("SALARIO");
                    Date dataNacemento = rs.getDate("DATA_NACEMENTO");
                    String nomeXefe = rs.getString("nome_xefe");
                    String departamento = rs.getString("departamento");

                    System.out.println("-".repeat(15));
                    System.out.println("Nombre: " + nome);
                    System.out.println("Apellido 1: " + apelido1);
                    System.out.println("Apellido 2: " + apelido2);
                    System.out.println("Localidad: " + localidade);
                    System.out.println("Salario: " + salario);
                    System.out.println("Fecha de Nacimiento: " + dataNacemento);
                    System.out.println("Nombre del Jefe: " + (nomeXefe != null ? nomeXefe : "Sin jefe"));
                    System.out.println("Departamento: " + (departamento != null ? departamento : "Sin departamento"));
                    System.out.println("-".repeat(15));
                }
            }
            rs.close();
        }catch (SQLException e){
            System.out.println("Error al consultar empleados por localidad.");
            e.printStackTrace();
        }
    }

    /************************ EJERCICIO 2.3
                                            *****************************/
    public static void cambiarDepartamento(String nombreDepartamento, String nombreProyecto){
        try{
            String query = """
                            UPDATE proxecto
                                SET NUM_DEPARTAMENTO = (
                                                    SELECT NUM_DEPARTAMENTO
                                                    FROM departamento
                                                    WHERE NOME_DEPARTAMENTO = ?
                                                    )
                                WHERE NOME_PROXECTO = ?""";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, nombreDepartamento);
            ps.setString(2, nombreProyecto);
            ps.executeUpdate();
            ps.close();
            System.out.println("El proyecto "+nombreProyecto+" ahora está dirigido por el departamento: "+nombreDepartamento);

        } catch (SQLException e) {
            System.out.println("Error al cambiar el departamento de proyecto.");
        }
    }

    public static void  insertProyecto(Proxecto proxecto){
        try{
            String query = """
                                INSERT INTO proxecto 
                                (NOME_PROXECTO, LUGAR, NUM_DEPARTAMENTO)
                                VALUES (?,?,?)""";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, proxecto.getNome_proxecto());
            ps.setString(2, proxecto.getLugar());
            ps.setInt(3, proxecto.getNum_departamento());
            ps.executeUpdate();
            ps.close();
            System.out.println("El proyecto fue creado exitosamente.");
        }catch (SQLException e){
            System.out.println("Error al insertar el proyecto ");
        }
    }

    public static void borrarProyecto(int numProyecto){
        try{
            String borrarRegistros = """
                                        DELETE FROM empregado_proxecto
                                        WHERE NUM_PROXECTO = ?""";
            PreparedStatement ps = connection.prepareStatement(borrarRegistros);
            ps.setInt(1, numProyecto);
            ps.executeUpdate();
            ps.close();

            String borrarProyecto = """
                                        DELETE FROM proxecto
                                        WHERE NUM_PROXECTO = ? """;
            PreparedStatement ps2 = connection.prepareStatement(borrarProyecto);
            ps2.setInt(1, numProyecto);
            ps2.executeUpdate();
            ps2.close();
            System.out.println("El proyecto y sus registros se han borrado con éxito.");
        }catch (SQLException e){
            System.out.println("Error al borrar el proyecto ");
        }
    }

    /************************ EJERCICIO 2.4
                                            *****************************/
        public static List<Proxecto> getProyectosDepartamento(String nombreDepartamento){
            List<Proxecto> proxectos = new ArrayList<>();
            try{
                String query = """
                        SELECT *
                            FROM proxecto
                            WHERE NUM_DEPARTAMENTO = (
                                    SELECT NUM_DEPARTAMENTO
                                    FROM departamento
                                    WHERE NOME_DEPARTAMENTO = ?
                                )  """;
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, nombreDepartamento);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    String nombreProyecto = rs.getString("nome_proxecto");
                    int numProyecto = rs.getInt("num_proxecto");
                    String lugar = rs.getString("lugar");
                    int numDepartamento = rs.getInt("num_departamento");
                    Proxecto proxecto = new Proxecto(numProyecto,nombreProyecto,lugar,numDepartamento);
                    proxectos.add(proxecto);
                }
                rs.close();
                ps.close();
            }catch (SQLException e){
                System.out.println("Error al obtener la lista de proyectos ");
                e.printStackTrace();
            }
            return proxectos;
    }


}
