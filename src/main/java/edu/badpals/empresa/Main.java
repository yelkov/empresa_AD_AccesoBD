package edu.badpals.empresa;

import java.sql.ResultSet;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DatabaseManager dbm = new DatabaseManager();
        dbm.conectarDB();
        try(ResultSet rs = dbm.statement.executeQuery("select * from departamento")){
            while(rs.next()){
                System.out.println(rs.getInt("num_departamento"));
                System.out.println(rs.getString("nome_departamento"));
                System.out.println(rs.getString("nss_dirige"));
                System.out.println(rs.getDate("data_direccion"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}