/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mensajeriahibernate;

import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Magali
 */
public class TestORM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MensajeriaORM db = new MensajeriaORM();
	System.out.println("Conectado.\n");
        
        List<Usuario> usuarios = db.getUsuarios();
		System.out.println("Usuario: ");
		for (Usuario s : usuarios) {
			System.out.println(s.getUsuario() + " : " + s.getNombre()+ " : " + s.getPassword());
		}
		System.out.println();
                
         
        //insertar usuario
       //  Usuario usuario = new Usuario("usuariog2", "Usuario generico 2", "pass4");
       //  db.insertarUsuario(usuario);       
                
         //validar entrada
	//System.out.println(db.validarUsuario("Mag", "pass"));
        
        //modificar usuario
        //Usuario usuarioupdate = new Usuario("Pepe", "Pepe Gilbert","pass2");
        //db.updateUsuario(usuarioupdate);
		
	//cambiar pass
	//db.cambiarContrasenya("usuariog", "passgenerica");
		
	//eliminar usuario creado
	//db.deleteUsuario(new Usuario("usuariog", "Usuario generico", "pswd"));
                
        //buscar mensaje por id
        //Mensajes buscar = db.getMensajeId(1);
        //System.out.println(db.getMensajeId(1).toString());	

                
         //obtener lista de todos los mensajes
        /*
        List<Mensajes> mensajes = db.getMensajes();
        System.out.println("Mensajes: ");
        for (Mensajes s : mensajes) {
        	System.out.println(s.toString());
        }*/
                
        //insertar nuevo mensaje
        //Mensajes mensaje = new Mensajes(7, "Prueba", new Usuario("Pepe"), new Usuario("Mag"), "121220121213");
        //db.insertarMensaje(mensaje);
                

                
        //obtener mensajes en relacion a un usuario
                //la segunda variable determina si es emisor(true) o receptor(false)
                /*
                List<Mensajes> mensajes = db.getMensajeUsuario(new Usuario("Pepe"), false);
                
		for (Mensajes s : mensajes) {
			System.out.println(s.toString());
		}
		System.out.println();
                */
                
                //ahora ponemos la variable a true para tener los mensajer del usuario como emisor
                /*
                List<Mensajes> mensajes2 = db.getMensajeUsuario(new Usuario("Pepe"), true);
		System.out.println("Usuarios: ");
		for (Mensajes s : mensajes2) {
			System.out.println(s.toString());
		}
		System.out.println();
                */
                
               //obtener la hora del ultimo inicio de sesion de un usuario
                //Usuario usuario = new Usuario("Mag", "Magali Boada", "pass");
                //System.out.println(db.getFechaPorUsuario(usuario));
		
		
		//ranking usuarios
                
		List<Usuario> ranking = db.getRanking();
                
		for (int i=0; i< ranking.size();++i) 
		{
			System.out.println( (i+1) + ". " + ranking.get(i).getNombre() + " |Mensajes: " + ranking.get(i).getMensajesEnviados());
		}
                
		//ranking usuario
		System.out.println("Posicion: " + db.getPosicionRanking("Pepe"));
                
                
        try{
            db.stop();
            System.exit(-1);
        }catch (Exception ex){}
         System.out.println("Conexion cerrada");
    }
    
}
