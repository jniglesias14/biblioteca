import java.sql.Connection;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Connection con;
        boolean lectura1=true,lectura2=true,lectura3=true,lectura4=true,lectura5=true;
        Scanner teclado=new Scanner(System.in);
        if (GestorConexion.crearConexion("biblioteca", "user2", "A12345a")) {
            con = GestorConexion.getConexion();
            while (lectura1){
                try {
                    System.out.println("-----------------");
                    System.out.println("      MENU       ");
                    System.out.println("-----------------");
                    System.out.println("1) Secciones");
                    System.out.println("2) Socios");
                    System.out.println("3) Libros");
                    System.out.println("4) Prestamos");
                    System.out.println("5) Salir");
                    System.out.println("-----------------");
                    int opcion1 = Integer.parseInt(teclado.nextLine());
                    if (opcion1 == 1) {
                        lectura3=true;
                        while(lectura3){
                            try{
                                System.out.println("-----------------------------");
                                System.out.println("      MENU  DE SECCIONES     ");
                                System.out.println("-----------------------------");
                                System.out.println("1) Añadir seccion");
                                System.out.println("2) Eliminar seccion");
                                System.out.println("3) Ver todas los secciones");
                                System.out.println("4) Ver los libros de una seccion");
                                System.out.println("5) Salir");
                                System.out.println("-----------------------------");
                                int opcion3 = Integer.parseInt(teclado.nextLine());
                                if(opcion3==1){
                                    Secciones.añadir(con);
                                } else if (opcion3==2) {
                                    Secciones.eliminar(con);
                                } else if (opcion3==3) {
                                    Secciones.mostrar(con);
                                } else if (opcion3==4) {
                                    Secciones.mostrarSeccion(con);
                                }  else if(opcion3==5){
                                    lectura3=false;
                                } else {
                                    System.out.println("numero erroneo");
                                }
                            }catch (NumberFormatException nfe){
                                System.out.println("has metido texto");
                            }
                        }
                    } else if (opcion1 == 2) {
                        lectura2=true;
                        while(lectura2) {
                            try {
                                System.out.println("-------------------------");
                                System.out.println("      MENU  DE SOCIO     ");
                                System.out.println("-------------------------");
                                System.out.println("1) Añadir socio");
                                System.out.println("2) Eliminar socio");
                                System.out.println("3) Ver todos los socios");
                                System.out.println("4) Salir");
                                System.out.println("-------------------------");
                                int opcion2 = Integer.parseInt(teclado.nextLine());
                                if (opcion2 == 1) {
                                    Socios.añadir(con);
                                } else if (opcion2 == 2) {
                                    Socios.eliminar(con);
                                } else if (opcion2 == 3) {
                                    Socios.mostrar(con);
                                } else if (opcion2 == 4) {
                                    lectura2 = false;
                                } else {
                                    System.out.println("numero erroneo");
                                }
                            }catch (NumberFormatException nfe){
                                System.out.println("has metido texto");
                            }
                        }
                    } else if (opcion1 == 3) {
                        lectura4=true;
                        while(lectura4) {
                            try {
                                System.out.println("-------------------------");
                                System.out.println("      MENU  DE LIBROS     ");
                                System.out.println("-------------------------");
                                System.out.println("1) Añadir libro");
                                System.out.println("2) Eliminar libro");
                                System.out.println("3) Ver todos los libros");
                                System.out.println("4) Salir");
                                System.out.println("-------------------------");
                                int opcion4 = Integer.parseInt(teclado.nextLine());
                                if (opcion4 == 1) {
                                    Libros.añadir(con);
                                } else if (opcion4 == 2) {
                                    Libros.eliminar(con);
                                } else if (opcion4 == 3) {
                                    Libros.mostrar(con);
                                } else if (opcion4 == 4) {
                                    lectura4 = false;
                                } else {
                                    System.out.println("numero erroneo");
                                }
                            }catch (NumberFormatException nfe){
                                System.out.println("has metido texto");
                            }
                        }
                    } else if (opcion1 == 4) {
                        lectura5=true;
                        while(lectura5){
                            try{
                                System.out.println("---------------------------");
                                System.out.println("      MENU  DE PRESTAMOS     ");
                                System.out.println("---------------------------");
                                System.out.println("1) Añadir prestamo");
                                System.out.println("2) Eliminar prestamo");
                                System.out.println("3) Ver todos los prestamos");
                                System.out.println("4) Salir");
                                System.out.println("---------------------------");
                                int opcion5 = Integer.parseInt(teclado.nextLine());
                                if(opcion5==1){
                                    Prestamos.añadir(con);
                                } else if (opcion5==2) {
                                    Prestamos.eliminar(con);
                                } else if (opcion5==3) {
                                    Prestamos.mostrar(con);
                                } else if (opcion5==4) {
                                    lectura5=false;
                                } else{
                                    System.out.println("numero erroneo");
                                }
                            }catch (NumberFormatException nfe){
                                System.out.println("has introducido texto");
                            }
                        }
                    } else if (opcion1==5) {
                        lectura1=false;
                    } else {
                        System.out.println("numero erroneo");
                    }
                }catch (NumberFormatException nfe){
                    System.out.println("has metido texto");
                }
            }
        }else{
            System.out.println(GestorConexion.getError());
        }
    }
}
