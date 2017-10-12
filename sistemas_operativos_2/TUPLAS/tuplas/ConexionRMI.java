/*
 * HiloConexionRMI.java
 *
 * Created on January 12, 2001, 12:19 AM
 */
 
package tuplas;
import java.rmi.*;

/** 
 *
 * @author  Valued Sony Customer
 * @version 
 */
public class ConexionRMI {

  String _host;
  int _port;
  String _objUrl;
  
  
  /** Creates new HiloConexionRMI */
  public ConexionRMI(String host,int port,String objUrl) {
    _host = host;
    _port = port;
    _objUrl = objUrl;
  }
  
  /** El objeto Remoto Obtenido */
  public TuplaD _objetoRemoto;
  
  /** Indica si esta conectado o no */
  public boolean _conectado = false;

  /** Muestra el Lugar donde se conecto */
  public String stringDeConexion = new String();
  
  private boolean huboException = false;
  
  private String stringDeConexionPorDefecto = "//localhost:1099/tuplas/TuplaD";
  

    /** Coloca el Security Manager y Conecta al servidor y servicio especificado.
  Pone el objeto remoto en la propiedad _objetoRemoto de esta clase.
  Basta poner el host en null y se conectara a la conexion local por defecto.
  */
  public boolean _conectar() throws Exception {
    
    if ((_host == null || _host.equals("")) || (_port < 0) || (_objUrl == null || _objUrl.equals("")))
      stringDeConexion = stringDeConexionPorDefecto;
    else
      stringDeConexion = "//" + _host + ":" + _port + "/" + _objUrl;
    
    //Crea el SecurityManager
    //if (System.getSecurityManager() == null) System.setSecurityManager(new RMISecurityManager());
    _objetoRemoto = null;
    
    //Intenta la conexion y devuelve el objeto remoto.
    try {
      java.rmi.Remote obj = (java.rmi.Remote) Naming.lookup(stringDeConexion);
      _objetoRemoto = (TuplaD) obj;
    } catch (Exception ex) {
      System.out.println("HiloConexionRMI:" + ex);
      throw ex;
    }
   
    if (huboException)
    {
      _conectado = false;
      return false;
    }
    else 
    {
      System.out.println("HiloConexionRMI Conectado!!");
      _conectado = true;
      return true;
    }
  }
  
}