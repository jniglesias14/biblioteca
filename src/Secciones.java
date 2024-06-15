import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Secciones {

    public static int comprobar(Connection con, String nombre){
        String sql;
        try{
            sql="select * from secciones where seccion=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,nombre);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return 1;
            }else{
                return 0;
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }
    }

    public static void añadir(Connection con){
        Scanner teclado =new Scanner(System.in);
        System.out.println("nombre de la seccion: ");
        String nombre=teclado.nextLine();
        String sql;
        try{
            sql="insert into secciones(seccion) values(?)";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,nombre);
            ps.executeUpdate();
            System.out.println("seccion añadida");
        }catch (SQLException sqle){
            if(sqle.getErrorCode()==1062){
                System.out.println("esa seccion ya existe");
            }else {
                sqle.printStackTrace();
            }
        }

    }

    public static void eliminar(Connection con){
        Scanner teclado=new Scanner(System.in);
        String sql;
        System.out.println("nombre de la seccion: ");
        String nombre=teclado.nextLine();
        if(Secciones.comprobar(con,nombre)==1) {
            if(Libros.cambiarSeccion(con,nombre)) {
                try {
                    sql = "delete from secciones where seccion=?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, nombre);
                    ps.executeUpdate();
                    System.out.println("seccion eliminada");

                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                    System.out.println("error al ejecutar la instruccion sql");
                }
            }else{
                System.out.println("no se ha podido eliminar");
            }
        } else if (Secciones.comprobar(con,nombre)==0) {
            System.out.println("no hay ninguna seccion con ese nombre");
        }else {
            System.out.println("error al ejecutar la instruccion sql");
        }
    }

    public static void mostrar(Connection con){
        String sql;
        try{
            sql="select seccion from secciones";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            System.out.println("----------------------------------------");
            while(rs.next()){

                System.out.println(rs.getString("seccion"));

            }
        }catch (SQLException sqle){
            System.out.println("error al ejecutar la instruccion sql");
        }

    }

    public static void mostrarSeccion(Connection con){
        Scanner teclado=new Scanner(System.in);
        String sql;
        System.out.println("seccion: ");
        String nombre=teclado.nextLine();
        if(Secciones.comprobar(con,nombre)==1) {
            try {
                sql = "select nombre from libros where seccion=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1,nombre);
                ResultSet rs = ps.executeQuery();
                System.out.println("----------------------------------------");
                while (rs.next()) {
                    System.out.println(rs.getString("nombre"));
                }
            } catch (SQLException sqle) {
                System.out.println("error al ejecutar la instruccion sql");
            }
        }else{
            System.out.println("esa seccion no existe");
        }

    }


}
