import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Libros {

    public static int comprobar(Connection con, String nombre){
        String sql;
        try{
            sql="select id_libro from libros where nombre=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,nombre);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return rs.getInt("id_libro");
            }else{
                return 0;
            }
        }catch (SQLException sqle){

            return -1;
        }
    }

    public static String estado(Connection con,String nombre){
        String sql;
        try{
            sql="select estado from libros where nombre=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,nombre);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return rs.getString("estado");
            }else{
                return "0";
            }
        }catch (SQLException sqle){
            System.out.println("error al ejecutar la instruccion sql");
            return "-1";
        }
    }
    public static void cambiarEstado(Connection con,String nombre,String texto){
        String sql;
        try{
            sql="update libros set estado=? where nombre=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,texto);
            ps.setString(2,nombre);
            ps.executeUpdate();

        }catch (SQLException sqle){
            sqle.printStackTrace();

        }
    }

    public static void añadir(Connection con){
        String sql;
        Scanner teclado =new Scanner(System.in);
        System.out.println("nombre del libro: ");
        String nombre=teclado.nextLine();
        System.out.println("seccion: ");
        String seccion=teclado.next();
        if(Secciones.comprobar(con,seccion)==1) {
            try {
                sql = "insert into libros(nombre,seccion) values(?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nombre);
                ps.setString(2, seccion);
                ps.executeUpdate();
                System.out.println("libro añadido");

            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        } else if (Secciones.comprobar(con,seccion)==0) {
            System.out.println("esa seccion no existe");
        } else{
            System.out.println("error al ejecutar la instruccion");
        }

    }

    public static void eliminar(Connection con){
        Scanner teclado=new Scanner(System.in);
        String sql;
        System.out.println("nombre del libro: ");
        String nombre=teclado.nextLine();
        if(Libros.comprobar(con,nombre)==1) {
            try {
                sql = "delete from libros where nombre=?";
                PreparedStatement ps=con.prepareStatement(sql);
                ps.setString(1,nombre);
                ps.executeUpdate();
                System.out.println("libro eliminado");

            } catch (SQLException sqle) {
                System.out.println("error al ejecutar la instruccion sql");
            }
        } else if (Libros.comprobar(con,nombre)==0) {
            System.out.println("no hay ningun libro con ese nombre");
        }else {
            System.out.println("error al ejecutar la instruccion sql");
        }
    }

    public static void mostrar(Connection con){
        String sql;
        try{
            sql="select id_libro,nombre,seccion,estado from libros";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            System.out.println("--------------------------------------------------------------------------------");
            while(rs.next()){

                System.out.println(rs.getInt("id_libro")+" "+rs.getString("nombre")+" "+rs.getString("seccion")+" "+rs.getString("estado"));

            }
        }catch (SQLException sqle){
            System.out.println("error al ejecutar la instruccion sql");
        }

    }

    public static boolean cambiarSeccion(Connection con,String seccion){
        String sql;
        Scanner teclado=new Scanner(System.in);

        try{
            sql="select nombre from libros where seccion=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,seccion);
            ResultSet rs=ps.executeQuery();
            con.setAutoCommit(false);
            while (rs.next() ) {
                    System.out.println("nueva seccion para el libro "+rs.getString("nombre")+" :");
                    String nombre = teclado.nextLine();
                    if(!nombre.equals(seccion)) {
                        if (Secciones.comprobar(con, nombre) == 1) {
                            Libros.actualizar(con, nombre, rs.getString("nombre"));
                        } else if (Secciones.comprobar(con, nombre) == 0) {
                            System.out.println("esa seccion no existe");
                            con.rollback();
                            con.setAutoCommit(true);
                            return false;
                        } else {
                            System.out.println("error al ejecutar la instruccion");
                            con.rollback();
                            con.setAutoCommit(true);
                            return false;
                        }
                    }else {
                        System.out.println("no puedes poner esa seccion ya que va a ser eliminada");
                        con.rollback();
                        con.setAutoCommit(true);
                        return false;
                    }
            }
            con.commit();
            con.setAutoCommit(true);
            return true;
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public static void actualizar(Connection con,String nombre,String libro){
        String sql;
        try{
            sql="update libros set seccion=? where nombre=?";
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setString(1,nombre);
            ps.setString(2,libro);
            ps.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
}
