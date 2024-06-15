import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Socios {


    public static int comprobar(Connection con,String nombre){
        String sql;
        try{
            sql="select id_socio from socios where nombre=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,nombre);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return rs.getInt("id_socio");
            }else{
                return 0;
            }
        }catch (SQLException sqle){

            return -1;
        }
    }

    public static void añadir(Connection con){
        Scanner teclado =new Scanner(System.in);
        System.out.println("nombre del socio: ");
        String nombre=teclado.nextLine();
        System.out.println("direccion: ");
        String direccion=teclado.nextLine();
        String sql;
        try{
            sql="insert into socios(nombre, direccion) values(?,?)";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,nombre);
            ps.setString(2,direccion);

            ps.executeUpdate();
            System.out.println("socio añadido");

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }

    }

    public static void n_libros(Connection con, String nombre, int operacion){
        String sql;
        try{
            sql="update socios set n_libros= n_libros+? where nombre=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,operacion);
            ps.setString(2,nombre);
            ps.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }





    public static void eliminar(Connection con){
        Scanner teclado=new Scanner(System.in);
        String sql;
        System.out.println("nombre socio: ");
        String nombre=teclado.nextLine();
        if(Socios.comprobar(con,nombre)==1) {
            try {
                sql = "delete from socios where nombre=?";
                PreparedStatement ps=con.prepareStatement(sql);
                ps.setString(1,nombre);
                ps.executeUpdate();
                System.out.println("socio eliminado");

            } catch (SQLException sqle) {
                System.out.println("error al ejecutar la instruccion sql");
            }
        } else if (Socios.comprobar(con,nombre)==0) {
            System.out.println("no hay ningun socio con ese nombre");
        }else {
            System.out.println("error al ejecutar la instruccion sql");
        }
    }

    public static void mostrar(Connection con){
        String sql;
        try{
            sql="select id_socio,nombre,direccion,n_libros from socios";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            System.out.println("---------------------------------------------------------------------------------------");
            while(rs.next()){

                System.out.println(rs.getInt("id_socio")+" "+rs.getString("nombre")+" "+rs.getString("direccion")+" "+rs.getInt("n_libros"));

            }
        }catch (SQLException sqle){
            System.out.println("error al ejecutar la instruccion sql");
        }

    }


}
