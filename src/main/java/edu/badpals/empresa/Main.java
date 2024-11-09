package edu.badpals.empresa;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DatabaseManager.conectarDB();

        //ejercicio2_1();

    }

    public static void ejercicio2_1(){
        DatabaseManager.subirSalarioDepartamento(27,3);
        DatabaseManager.subirSalarioDepartamento(1000,10);
        DatabaseManager.crearDepartamento(1,"VENTAS","1231231");
        DatabaseManager.crearDepartamento(7,"VENTAS","1231231");
        DatabaseManager.crearDepartamento(8,"ADMINISTRACION","9999999");
        DatabaseManager.borrarEmpleadoProyecto("9990009",7);
    }

    public static void ejercicio2_2(){

    }
}