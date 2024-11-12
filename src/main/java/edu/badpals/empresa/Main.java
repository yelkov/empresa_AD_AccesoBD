package edu.badpals.empresa;

import javax.xml.crypto.Data;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DatabaseManager.conectarDB();
        //ejercicio2_1();
        //ejercicio2_2();
        //ejercicio2_3();
        //ejercicio2_4();
        //ejercicio2_5();
        //ejercicio2_6();
        DatabaseManager.desconectarDB();

        DatabaseMetadatos.conectarDB();
        ejercicio3_2();
        DatabaseMetadatos.desconectarDB();
    }


    public static void ejercicio2_1(){
        System.out.println("\n**************Ejercicio 2.1*************");
        DatabaseManager.subirSalarioDepartamento(27,3);
        DatabaseManager.subirSalarioDepartamento(1000,10);
        DatabaseManager.crearDepartamento(1,"VENTAS","1231231");
        DatabaseManager.crearDepartamento(7,"VENTAS","1231231");
        DatabaseManager.crearDepartamento(8,"VENTAS","1231231");
        DatabaseManager.crearDepartamento(9,"ADMINISTRACION","9999999");
        DatabaseManager.borrarEmpleadoProyecto("9990009",7);
        DatabaseManager.borrarEmpleadoProyecto("9990009",200);
        DatabaseManager.borrarEmpleadoProyecto("9999999",7);
    }

    public static void ejercicio2_2(){
        System.out.println("\n**************Ejercicio 2.2*************");
        DatabaseManager.getEmpleadosLocalidad("Vigo");
        DatabaseManager.getEmpleadosLocalidad("Casablanca");
    }

    public static void ejercicio2_3(){
        System.out.println("\n**************Ejercicio 2.3*************");
        DatabaseManager.cambiarDepartamento("PERSOAL","XESTION DE PERSOAL");
        Proxecto proxecto = new Proxecto("PROYECTO DE PRUEBA","CANGAS",1);
        DatabaseManager.insertProyecto(proxecto);
        DatabaseManager.borrarProyecto(1);
    }

    public static void ejercicio2_4(){
        System.out.println("\n**************Ejercicio 2.4*************");
        List<Proxecto> proxectosInformatica = DatabaseManager.getProyectosDepartamento("INFORMÁTICA");
        proxectosInformatica.stream().forEach(proxecto -> System.out.println(proxecto));
        List<Proxecto> proxectosDesconocido = DatabaseManager.getProyectosDepartamento("DESCONOCIDO");
        proxectosDesconocido.stream().forEach(proxecto -> System.out.println(proxecto));
    }

    public static void ejercicio2_5(){
        System.out.println("\n**************Ejercicio 2.5*************");
        /*  a)    */
        DatabaseManager.cambioDomicilioPR("9990009","Juan Flórez",91,"2-C","15001","Coruña, A");
        DatabaseManager.cambioDomicilioPR("9999999","Juan Flórez",91,"2-C","15001","Coruña, A");

        /*  b)    */
        Proxecto proxecto = DatabaseManager.getProxectoPR(2);
        if(proxecto != null){
            System.out.println(proxecto);
        }
        DatabaseManager.departControlaProxecPR(0);
        DatabaseManager.departControlaProxecPR(1);
        DatabaseManager.departControlaProxecPR(22);

        /*  d)    */
        DatabaseManager.getNumEmpregadosDep("INFORMATICA");
    }

    public static void ejercicio2_6(){
        DatabaseManager.getRsTypesConc();
        Proxecto proxecto = new Proxecto(11,"PROXECTO DE PROBA","LUGO",1);
        DatabaseManager.insertProxectoDinamico(proxecto);
        Proxecto proxecto2 = new Proxecto(11,"PROXECTO DE PROBA","LUGO",1);
        DatabaseManager.insertProxectoDinamico(proxecto2);
        Proxecto proxecto3 = new Proxecto(15,"PROXECTO DE PROBA","LUGO",20);
        DatabaseManager.insertProxectoDinamico(proxecto3);
        DatabaseManager.incrementarSalarioDin(3,1);
        DatabaseManager.getEmpregadosCantProxectos(1);

    }
    private static void ejercicio3_2() {
        DatabaseMetadatos.mostrarInformacionBasica();
        DatabaseMetadatos.mostrarTablasUsuarioBDEmpresa();
        DatabaseMetadatos.mostrarColumnas(null,"empregado");
        DatabaseMetadatos.mostrarProceduresBDEmpresa();
        DatabaseMetadatos.mostrarClavesPrimarias(null,"proxecto");
        DatabaseMetadatos.mostrarClavesForaneas(null,"empregado_proxecto");
    }
}