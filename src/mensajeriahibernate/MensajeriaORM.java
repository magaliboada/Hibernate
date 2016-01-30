/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mensajeriahibernate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


/**
 *
 * @author Magali
 */
public class MensajeriaORM {
    
private static org.hibernate.SessionFactory sessionFactory;

	public static Session getSession() {
		if (sessionFactory == null) {
			ServiceRegistry serviceRegistry;
			Configuration configuration = new Configuration();
			configuration.configure();
			serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();      
			sessionFactory = configuration.buildSessionFactory(
					serviceRegistry);
		}
		return sessionFactory.openSession();
	}
        
        public void stop() throws Exception {
            sessionFactory.close();
        }

	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuarios() {
		Session session = getSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Usuario.class);
		return c.list();
	}

	public void insertarUsuario(Usuario usuario) {
            try{
                Session session = getSession();
		Transaction transaction = session.beginTransaction();
		session.save(usuario);
		transaction.commit();
            }
            catch(Exception ex){
                
            }
		
	}
        
        public void insertarMensaje(Mensajes mensaje) {
            try{
                Session session = getSession();
		Transaction transaction = session.beginTransaction();
		session.save(mensaje);
		transaction.commit();
            }catch(Exception ex){
                System.out.println(ex);
            }
		
	}

        
        public String validarUsuario(String usuarioentra, String contrasenyaentra) 
	{
		Usuario usuariovalidar = new Usuario();
		Session session = getSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Usuario.class)
				.add(Restrictions.eq("usuario", usuarioentra));
		@SuppressWarnings("unchecked")
		List<Usuario> usuario = c.list();
		
		if(usuario.size()==0)
			return "Usuario incorrecto";
		
		usuariovalidar = usuario.get(0);		
				
		if (usuariovalidar.getPassword().equals(contrasenyaentra))
				return "Hola "+usuariovalidar.getNombre();
		else return "Contrasenya incorrecta.";
		
	}
        
        public static void updateUsuario(Usuario usuario) {
            try{
                Session session = getSession();
		Transaction transaction = session.beginTransaction();
		session.update(usuario);
		transaction.commit();
            }catch(Exception ex){
                
            }
		
	}
	
	public static void cambiarContrasenya(String usuariocambia, String contrasenya) {
            try{
                Session session = getSession();
		Transaction transaction = session.beginTransaction();
		Criteria c = session.createCriteria(Usuario.class)
				.add(Restrictions.eq("usuario", usuariocambia));
		@SuppressWarnings("unchecked")
		List<Usuario> usuario = c.list();		
		Usuario cambia = usuario.get(0);
		cambia.setPassword(contrasenya);		
		
		session.update(cambia);
		transaction.commit();
            }catch(Exception ex){
                
            }
		
	}	
	
	public static void deleteUsuario(Usuario usuario) {
            try{
                Session session = getSession();
		Transaction transaction = session.beginTransaction();
		session.delete(usuario);
		transaction.commit();
            }catch(Exception ex){
                
            }
		
	}
        
        
        public static Mensajes getMensajeId(int id) {
            try{
                Session session = getSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Mensajes.class)
				.add(Restrictions.eq("id", id));
		@SuppressWarnings("unchecked")
		List<Mensajes> mensajes = c.list();
		return mensajes.get(0);
            }catch(Exception ex){
                
            }
            return null;
	}
        
        public List<Mensajes> getMensajes() {
            try{
                Session session = getSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Mensajes.class)
				.addOrder(Order.asc("id"));
		@SuppressWarnings("unchecked")
		List<Mensajes> mensajes = c.list();
		return mensajes;
            }catch(Exception ex){
                
            }
            
            return null;
	}
	
        public static List<Mensajes> getMensajeUsuario(Usuario usuario, boolean esEmisor) {
            try{
                Session session = getSession();
		session.beginTransaction();
                Criteria c;
                
                if(esEmisor)
                    c = session.createCriteria(Mensajes.class).add(Restrictions.eq("usuarioByEmisor", usuario));
		else
                    c = session.createCriteria(Mensajes.class).add(Restrictions.eq("usuarioByReceptor", usuario));
                
		@SuppressWarnings("unchecked")
		List<Mensajes> mensajes = c.list();
                
		return mensajes;
            }catch(Exception ex){
                
            }
            return null;
	}
        
        public String getFechaPorUsuario(Usuario usuario) {
			
            String fechafinal;
            Session session = getSession();
            session.beginTransaction();
            Criteria c = session.createCriteria(Historial.class)
                .addOrder(Order.desc("fecha"));
                        
            @SuppressWarnings("unchecked")
            List<Historial> historial = c.list();
            Historial historial1 = historial.get(0);
            fechafinal = historial1.convertirHora(historial1.getFecha());                        
            return fechafinal;
		
	}
        
        public List<Usuario> getRanking()
	{
            List<Usuario> usuarios = getUsuarios();
				
		for (Usuario u : usuarios) 
		{
                    List<Mensajes> mensajes = getMensajeUsuario(u, false);
                    for (Mensajes s : mensajes) {
                        u.setMensajesEnviados(u.getMensajesEnviados()+1);
                    }
		}
                
            Collections.sort(usuarios, new CustomComparator());
            Collections.reverse(usuarios);
            return usuarios;
	}
        
        public int getPosicionRanking(String usuario)
	{
		List<Usuario> usuarios = this.getRanking();
		String usuariocomp;
		
		for (int i=0; i<usuarios.size();++i)
		{			
			usuariocomp = usuarios.get(i).getUsuario();
			if(usuariocomp.equals(usuario))
			{
				return i+1;
			}
		}
		
		return 0;		
	}

}
