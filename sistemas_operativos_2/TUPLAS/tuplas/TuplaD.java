/*
 * TuplaD.java
 *
 * Created on January 12, 2001, 1:14 AM
 */

package tuplas;

import java.rmi.*;

/** Remote interface.
 *
 * @author Valued Sony Customer
 * @version 1.0
 */
public interface TuplaD extends java.rmi.Remote {  
  //String about() throws RemoteException;
  
  
  /** Crear (Nombre, Dimensión, Tipo, Lista de Servidores) = Status <br>
   * Donde <br>
   * @param<b>Nombre</b>: Identificador del conjunto de tuplas<br>
   * @param<b>Dimensión</b>: Número de elementos de una tupla (debe ser >= 2)<br>
   * @param<b>Tipo</b>: Indica si es segmentado, replicado o particionado<br>
   * @param<b>Lista de Servidores</b>: Nombre de las máquinas donde se desea que resida el conjunto de tuplas.<br><br>
   * Las operaciones sobre las tuplas pueden manejar un esquema de locking centralizado para proveer la
   * exclusión mutua.
   */
  public boolean crear(String nombre,int dimension,int tipo,String[] listaServidores) throws RemoteException;
  
  /** Eliminar (Nombre) = Status */
  public boolean eliminar(String nombre) throws RemoteException;
  
  /** Insertar (Nombre, ti) = Status */
  public boolean insertar(String nombre,String ti) throws RemoteException;
  
  /** Borrar (Nombre, Clave) = Status */
  public boolean borrar(String nombre,String clave) throws RemoteException;
    
  /** Buscar (Nombre, Clave) = Lista de Valores (tupla) */
  public boolean buscar(String nombre,String clave) throws RemoteException;
  
  /** Actualizar (Nombre, Clave, Posicion, Valor) = Status */
  public boolean actualizar(String nombre,String clave,int posicion,String valor) throws RemoteException;
  
  /** Configuración (Nombre) = Información de configuración del conjunto de tuplas */
  public String configuracion(String nombre) throws RemoteException;
  
  
  
}