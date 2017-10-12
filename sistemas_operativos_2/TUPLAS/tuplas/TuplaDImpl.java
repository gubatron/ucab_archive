/*
 * TuplaDImpl.java
 *
 * Created on January 11, 2001, 3:36 PM
 */
 
package tuplas;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.MalformedURLException;

/** Unicast remote object implementing TuplaD interface.
 *
 * @author Valued Sony Customer
 * @version 1.0
 */
public class TuplaDImpl extends UnicastRemoteObject implements TuplaD {

  public static final int REPLICADO = 0;
  public static final int SEGMENTADO = 1;
  public static final int PARTICIONADO = 2;
  
  /** Nombre de registro */
  static protected String objURLName = "tuplas/TuplaD";
    
  /** Construye un objeto TuplaDImpl y los exporta por el puerto por defecto
   */
  public TuplaDImpl() throws RemoteException {
    super();
  }

  /** Construye un objeto TuplaDImpl y los exporta por el puerto especificado
   * @param port El puerto para exportar
   */
  public TuplaDImpl(int port) throws RemoteException {
    super(port);
  }

  /** Registra el objeto TuplaDImpl con el Registro RMI
  * @param rmiName Nombre que identifica el servicio en el registro RMI, Ej. "tuplas/TuplaD"
  * @throw RemoteException Si no puede ser exportado o atado (bind - bound) al registro RMI
  * @throw MalformedURLException Si el rmiName no sirve para construir un URL valido
  * @throw IllegalArgumentException Si rmiName es null
  */
  public void register(String rmiName) throws RemoteException, MalformedURLException{
    
    if (rmiName == null) throw new IllegalArgumentException("El nombre de registro no puede ser encontrado (null)");
    
    try {
      Naming.rebind(rmiName, this);
    } catch (RemoteException ex){
      Registry r = LocateRegistry.createRegistry(1099);
      r.rebind(rmiName, this);
    }
  }
  
  /** Metodo main
   */
  public static void main(String[] args) throws Exception{

    //System.setSecurityManager(new RMISecurityManager());
    
    TuplaDImpl obj = new TuplaDImpl ();
    obj.register(objURLName);

    System.err.println(objURLName + " esta Registrado!");
  }
  
  /** Crear (Nombre, Dimensión, Tipo, Lista de Servidores) = Status <br>
   * Donde <br>
   * @param<b>Nombre</b>: Identificador del conjunto de tuplas<br>
   * @param<b>Dimensión</b>: Número de elementos de una tupla (debe ser >= 2)<br>
   * @param<b>Tipo</b>: Indica si es segmentado, replicado o particionado<br>
   * @param<b>Lista de Servidores</b>: Nombre de las máquinas donde se desea que resida el conjunto de tuplas.<br><br>
   * Las operaciones sobre las tuplas pueden manejar un esquema de locking centralizado para proveer la
   * exclusión mutua.
   */
  public boolean crear(String nombre,int dimension,int tipo,String[] listaServidores) throws RemoteException {
    String TIPO = new String();
    switch (tipo)
    {
      case REPLICADO:
        TIPO = "REPLICADO";
        break;
      case PARTICIONADO:
        TIPO = "PARTICIONADO";
        break;
      case SEGMENTADO:
        TIPO = "SEGMENTADO";
        break;
    }
    
    System.out.println("--");
    System.out.println("Crear (" + nombre + "," + Integer.toString(dimension) + "," + TIPO + ",");
    System.out.println("Lista de Servidores: ");
    
    for (int i=0; i<listaServidores.length; i++)
      System.out.println(listaServidores[i]);
    System.out.println("--");
    
    return false;
  }
  
  /** Eliminar (Nombre) = Status */
  public boolean eliminar(String nombre) throws RemoteException {
    System.out.println("Eliminar (" + nombre + ")");
    return false;
  }
  
  /** Insertar (Nombre, ti) = Status */
  public boolean insertar(String nombre,String ti) throws RemoteException {
    System.out.println("Insertar (" + nombre + "," + ti + ")");
    return false;
  }
  
  /** Borrar (Nombre, Clave) = Status */
  public boolean borrar(String nombre,String clave) throws RemoteException {
    System.out.println("Borrar (" + nombre + "," + clave + ")");
    return false;
  }
  
  
  /** Buscar (Nombre, Clave) = Lista de Valores (tupla) */
  public boolean buscar(String nombre,String clave) throws RemoteException {
    System.out.println("Buscar (" + nombre + "," + clave + ")");
    return false;
  }
  
  /** Actualizar (Nombre, Clave, Posicion, Valor) = Status */
  public boolean actualizar(String nombre,String clave,int posicion,String valor) throws RemoteException {
    System.out.println("Actualizar (" + nombre + "," + clave + "," + Integer.toString(posicion) + "," + valor + ")");
    return false;
  }
  
  /** Configuración (Nombre) = Información de configuración del conjunto de tuplas */
  public String configuracion(String nombre) throws RemoteException {
    System.out.println("Desde el Servidor -> Configuracion (" + nombre +")");
    return "Aqui aparece la configuracion de " + nombre;
  }
}