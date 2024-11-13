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
            System.out.println("Error al mostrar info básica de la base de datos.");
        }
    }

    public static void mostrarTablasUsuarioBDEmpresa(){
        System.out.println("\nMostrando tablas de usuario: ");
        String patron = "%";
        String[] tipos = new String[1];
        tipos[0] = "TABLE";
        try{
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet tables = dbmd.getTables("bdempresa", null, "%", tipos);
            while (tables.next()){
                String tabla = tables.getString("TABLE_NAME");
                String tipo = tables.getString("TABLE_TYPE");
                String esquema = tables.getString("TABLE_SCHEM");
                String catalogo = tables.getString("TABLE_CAT");
                System.out.println("Nombre: "+tabla+ " | Tipo: "+tipo+ " | Esquema: "+esquema+ " | Catalogo: "+catalogo);
            }
            tables.close();

        }catch (SQLException e){
            System.out.println("Error al mostrar tablas de usuario de BDEmpresa.");
            e.printStackTrace();
        }
    }
    public static void mostrarColumnas(String esquema, String tabla){
        try{
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet columns = dbmd.getColumns(null, esquema, tabla, null);
            System.out.println("\nMostrando columnas de la tabla "+tabla);
            while (columns.next()){
                String nombreColumna = columns.getString("COLUMN_NAME");
                String tipoColumna = columns.getString("TYPE_NAME");
                String tamañoColumna = columns.getString("COLUMN_SIZE");
                String nullable = columns.getString("IS_NULLABLE");
                System.out.println("Nombre columna: "+nombreColumna+" | Tipo columna: "+tipoColumna+ " | Tamaño: "+tamañoColumna+" | Nullable: "+nullable);
            }
            columns.close();
        }catch (SQLException e){
            System.out.println("Error al mostrar columnas.");
            e.printStackTrace();
        }
    }

    public static void mostrarProceduresBDEmpresa(){
        try{
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet procedures = dbmd.getProcedures("bdempresa", null, null);
            System.out.println("\nMostrando procedures de BDEmpresa.");
            while (procedures.next()){
                String nombreProcedu = procedures.getString("PROCEDURE_NAME");
                String tipoProcedu = procedures.getString("PROCEDURE_TYPE");
                String catalogoProcedu = procedures.getString("PROCEDURE_CAT");
                //String esquemaProcedu = procedures.getString("PROCEDURE_SCHEM");
                System.out.println("Nombre: "+nombreProcedu+" | Tipo: "+tipoProcedu+" | BD: "+catalogoProcedu);

            }
            procedures.close();

        }catch (SQLException e){
            System.out.println("Error al mostrar procedures.");
            e.printStackTrace();
        }
    }

    public static void mostrarClavesPrimarias(String esquema, String tabla){
        try{
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet pks = dbmd.getPrimaryKeys(null, esquema, tabla);
            System.out.println("\nMostrando claves primarias de " + tabla);
            while (pks.next()){
                String pk = pks.getString("COLUMN_NAME");
                String table = pks.getString("TABLE_NAME");
                String catalog = pks.getString("TABLE_CAT");
                System.out.println("Nombre: "+pk+" | Table: "+table+" | Catalog: "+catalog);
            }
            pks.close();
        }catch (SQLException e){
            System.out.println("Error al mostrar claves primarias.");
            e.printStackTrace();
        }
    }

    public static void mostrarClavesForaneas(String esquema, String tabla){
        try{
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet fks = dbmd.getImportedKeys(null, esquema, tabla);
            System.out.println("\nMostrando claves foráneas de " + tabla);
            while (fks.next()){
                String fk = fks.getString("PKCOLUMN_NAME");
                String table = fks.getString("FKTABLE_NAME");
                String tablaPadre = fks.getString("PKTABLE_NAME");
                System.out.println("Nombre: "+fk+" | Tabla : "+table+" | Tabla padre que contiene la fk: "+tablaPadre);
            }
            fks.close();
        }catch (SQLException e){
            System.out.println("Error al mostrar claves primarias.");
            e.printStackTrace();
        }
    }

    public static void mostrarInfoProcFunCarac(){
        try{
            DatabaseMetaData dbmd = connection.getMetaData();
            System.out.println("Funciones de cadena disponibles: " + dbmd.getStringFunctions());
            System.out.println("Funciones de fecha y hora disponibles: " + dbmd.getTimeDateFunctions());
            System.out.println("Funciones matemáticas disponibles: " + dbmd.getNumericFunctions());
            System.out.println("Funciones de sistema disponibles: " + dbmd.getSystemFunctions());


            System.out.println("Palabras reservadas: " + dbmd.getSQLKeywords());


            System.out.println("Delimitador de identificadores: " + dbmd.getIdentifierQuoteString());


            System.out.println("Cadena de escape de caracteres comodín: " + dbmd.getSearchStringEscape());

            boolean procedimientosPermitidos = dbmd.allProceduresAreCallable();
            boolean tablasPermitidas = dbmd.allTablesAreSelectable();
            System.out.println("El usuario puede llamar a todos los procedimientos: " + (procedimientosPermitidos ? "Sí" : "No"));
            System.out.println("El usuario puede acceder a todas las tablas: " + (tablasPermitidas ? "Sí" : "No"));
        }catch (SQLException e){
            System.out.println("Error al mostrar caracteristicas de funciones, matematicas, procedimientos.");
            e.printStackTrace();
        }
    }


    public static void mostrarLimitesConector(){
        try{
            DatabaseMetaData dbmd = connection.getMetaData();
            System.out.println("Número máx. de conexiones simultáneas: "+ (dbmd.getMaxConnections() == 0? "Sin límite o límite desconocido": dbmd.getMaxConnections()));
            System.out.println("Número máx. de sentencias simultáneas: "+ (dbmd.getMaxStatements() == 0? "Sin límite o límite desconocido": dbmd.getMaxStatements()));
            System.out.println("Número máx. de tablas en una consulta SELECT: "+dbmd.getMaxTablesInSelect());
            System.out.println("Longitud máx. del nombre de una tabla: "+dbmd.getMaxTableNameLength());
            System.out.println("Longitud máx. del nombre de una columna: "+dbmd.getMaxColumnNameLength());
            System.out.println("Longitud máx. del nombre de una sentencia: "+dbmd.getMaxStatementLength());
            System.out.println("Longitud máx. del nombre de una fila: "+dbmd.getMaxRowSize());
            System.out.println("Longitud máx. del nombre de una procedimiento: "+dbmd.getMaxProcedureNameLength());
            System.out.println("Número máx. de columnas que se pueden usar en un ORDER: "+dbmd.getMaxColumnsInOrderBy());
            System.out.println("Número máx. de columnas que se pueden usar en un SELECT: "+dbmd.getMaxColumnsInSelect());
            System.out.println("Número máx. de columnas que se pueden usar en un GROUP BY: "+dbmd.getMaxColumnsInGroupBy());


        }catch (SQLException e){
            System.out.println("Error al mostrar los límites del conector.");
            e.printStackTrace();
        }
    }

    public static void mostrarInfoTransacciones(){
        try{
            DatabaseMetaData dbmd = connection.getMetaData();
            System.out.println("Soporta transacciones: "+(dbmd.supportsTransactions()?"Sí":"No"));
            System.out.println("Nivel de aislamiento de las transacciones predeterminado: "+dbmd.getDefaultTransactionIsolation());
            System.out.println("Soporta sentenzas de manipulación de datos e de definición de datos dentro das transaccións: "+(dbmd.supportsDataDefinitionAndDataManipulationTransactions()?"Sí":"No"));
        }catch (SQLException e){
            System.out.println("Error al mostrar información sobre transacciones.");
            e.printStackTrace();
        }
    }

    public static void mostrarSoporteCaract(){
        try{
            DatabaseMetaData dbmd = connection.getMetaData();
            System.out.println("La instrucción ALTER TABLE se puede utilizar ADD COLUMN y DROP COLUMN: "+(dbmd.supportsAlterTableWithAddColumn()?"Sí":"No"));
            System.out.println("Los alias de columnas se puede utilizar la palabra AS: "+(dbmd.supportsColumnAliasing()?"Sí":"No"));
            System.out.println("El resultado de concatenar un valor NULL con uno NOT NULL da como resultado NULL: "+(dbmd.nullPlusNonNullIsNull()?"Sí":"No"));
            System.out.println("Se soportan las conversiones entre tipos de datos JDBC: "+(dbmd.supportsConvert()?"Sí":"No"));
            System.out.println("Se soportan los nombres de tablas correlacionadas: "+(dbmd.supportsTableCorrelationNames()?"Sí":"No"));
            System.out.println("Se permite usar una columna que no esté en la instrucción SELECT en una cláusula ORDER BY: "+(dbmd.supportsOrderByUnrelated()?"Sí":"No"));
            System.out.println("Se soporta la cláusula GROUP BY: "+(dbmd.supportsGroupBy()?"Sí":"No"));
            System.out.println("Se permite el uso de una columna que no esté en la instrucción SELECT en una cláusula GROUP BY: "+(dbmd.supportsGroupByUnrelated()?"Sí":"No"));
            System.out.println("Se soportan las cláusulas LIKE: "+(dbmd.supportsLikeEscapeClause()?"Sí":"No"));
            System.out.println("Se soportan los outer joins: "+(dbmd.supportsOuterJoins()?"Sí":"No"));
            System.out.println("Se soportan subconsultas EXISTS: "+(dbmd.supportsSubqueriesInExists()?"Sí":"No"));
            System.out.println("Se soportan subconsultas en expresiones de comparación IN: "+(dbmd.supportsSubqueriesInIns()?"Sí":"No"));
            System.out.println("Se soportan subconsultas en expresiones de comparación en expresiones cuantificadas.: "+(dbmd.supportsSubqueriesInQuantifieds()?"Sí":"No"));


        }catch (SQLException e){
            System.out.println("Error al mostrar información sobre soporte de características.");
            e.printStackTrace();
        }
    }

    //e por cada columna: o nome, tipo, tamaño e si admite ou non nulos.

    public static void mostrarMetaDatosResSet(String consulta){
        try{
            ResultSet rs = statement.executeQuery(consulta);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumnas = rsmd.getColumnCount();
            System.out.println("\niNFORMACIÓN SOBRE COLUMNAS. TOTAL DE COLUMNAS EN LA CONSULTA: "+numColumnas);
            for (int i = 1; i <= numColumnas; i++){
                    String nombreColumna = rsmd.getColumnName(i);
                    String tipoColumna = rsmd.getColumnTypeName(i);
                    int tamañoColumna = rsmd.getColumnDisplaySize(i);
                    String admiteNulos = rsmd.isNullable(i) == 1? "Sí":"No";
                    System.out.println("Nombre de columna: "+nombreColumna+" | Tipo: "+tipoColumna+" | Tamaño: "+tamañoColumna+ " | Admite nulos: "+admiteNulos);
            }


        }catch (SQLException e){
            System.out.println("Error al mostrar metadatos del ResultSet.");
            e.printStackTrace();
        }
    }
}
