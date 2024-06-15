import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;
import java.sql.Date;

public class Prestamos {

    public static int comprobar(Connection con, int id_socio, int id_libro, LocalDate fecha) {
        String sql;
        Date date = Date.valueOf(fecha);
        try {
            sql = "select * from prestamos where id_socio=? and id_libro=? and fecha_inicio=? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_socio);
            ps.setInt(2, id_libro);
            ps.setDate(3, date);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }
    }

    public static void añadir(Connection con) {
        Scanner teclado = new Scanner(System.in);
        String sql;
        boolean lectura=true;
        System.out.println("nombre del socio: ");
        String nombre = teclado.nextLine();
        if (Socios.comprobar(con, nombre) > 0) {
            System.out.println("nombre del libro");
            String libro = teclado.nextLine();
            if (Libros.comprobar(con, libro) > 0) {
                if(Libros.estado(con,libro).equals("libre")) {
                    LocalDate fecha=null;
                    while(lectura) {
                        System.out.println("desea introducir un a fecha de devolucion(si/no)");
                        String opcion = teclado.nextLine();
                        if(opcion.equals("si")) {
                            boolean lectura2=true;
                            while(lectura2) {
                                System.out.println("fecha devolucion: ");
                                int año = Integer.parseInt(teclado.nextLine());
                                int mes = Integer.parseInt(teclado.nextLine());
                                int dia = Integer.parseInt(teclado.nextLine());
                                fecha = LocalDate.of(año, mes, dia);
                                if(fecha.isBefore(LocalDate.now())){
                                    System.out.println("fecha erronea");
                                }else {
                                    lectura2=false;
                                    lectura = false;
                                }

                            }
                        }
                        else if(!opcion.equals("no")){
                            System.out.println("error");
                        }
                        else{
                            lectura=false;
                        }
                    }
                    try {
                        sql = "insert into prestamos(id_socio,id_libro,fecha_inicio,fecha_fin) values(?,?,?,?)";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setInt(1, Socios.comprobar(con, nombre));
                        ps.setInt(2, Libros.comprobar(con, libro));
                        ps.setDate(3, Date.valueOf(LocalDate.now()));
                        if(fecha!=null) {
                            ps.setDate(4, Date.valueOf(fecha));
                        }else{
                            ps.setNull(4,java.sql.Types.DATE);
                        }
                        ps.executeUpdate();
                        System.out.println("prestamo añadido");
                        Libros.cambiarEstado(con,libro,"ocupado");
                        Socios.n_libros(con,nombre,+1);
                    } catch (SQLException sqle) {

                        sqle.printStackTrace();

                    }
                }else if(Libros.estado(con,libro).equals("ocupado")){
                    System.out.println("ese libro ya esta cogido");
                }else{
                    System.out.println("ese libro no existe");
                }
            } else{
                System.out.println("ese libro no existe");
            }
        } else {
            System.out.println("ese socio no existe");
        }

    }

    public static void eliminar(Connection con) {
        Scanner teclado = new Scanner(System.in);
        String sql;
        System.out.println("nombre del socio: ");
        String nombre = teclado.nextLine();
        System.out.println("nombre del libro: ");
        String libro=teclado.nextLine();
        System.out.println("fecha en que te llevaste el libro: ");
        int año=Integer.parseInt(teclado.nextLine());
        int mes=Integer.parseInt(teclado.nextLine());
        int dia=Integer.parseInt(teclado.nextLine());
        LocalDate fecha=LocalDate.of(año,mes,dia);
        if (Socios.comprobar(con, nombre) >0) {
            if (Libros.comprobar(con, libro)>0) {
                if(Prestamos.comprobar(con,Socios.comprobar(con,nombre),Libros.comprobar(con,libro),fecha)==1) {
                    try {
                        sql = "delete from prestamos where id_socio=? and id_libro=? and fecha_inicio=?";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setInt(1, Socios.comprobar(con,nombre));
                        ps.setInt(2, Libros.comprobar(con,libro));
                        ps.setDate(3,Date.valueOf(fecha));
                        ps.executeUpdate();
                        System.out.println("prestamo eliminado");
                        Libros.cambiarEstado(con,libro,"libre");
                        Socios.n_libros(con,nombre,-1);
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        System.out.println("error al ejecutar la instruccion sql");
                    }
                }else{
                    System.out.println("ese prestamo no existe");
                }
            } else {
                System.out.println("no hay ningun libro con ese nombre");
            }
        } else if (Secciones.comprobar(con, nombre) == 0) {
            System.out.println("no hay ninguna socio con ese nombre");
        } else {
            System.out.println("error al ejecutar la instruccion sql");
        }
    }

    public static void mostrar(Connection con) {
        String sql;
        try {
            sql = "select id_socio,id_libro,fecha_inicio,fecha_fin from prestamos";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("----------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("id_socio")+" "+rs.getInt("id_libro")+" "+rs.getDate("fecha_inicio")+" "+rs.getDate("fecha_fin"));
            }
        } catch (SQLException sqle) {
            System.out.println("error al ejecutar la instruccion sql");
        }

    }
}
