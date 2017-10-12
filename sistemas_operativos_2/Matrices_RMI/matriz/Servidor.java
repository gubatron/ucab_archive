/*
 * Servidor.java
 *
 * Created on January 10, 2001, 12:36 AM
 */

package matriz;

import java.rmi.*;

/** Remote interface.
 *
 * @author Valued Sony Customer
 * @version 1.0
 */
public interface Servidor extends java.rmi.Remote {  
  //String about() throws RemoteException;
  
  /** Devuelve el indice de la siguiente columna por calcular de la matriz.
    Si todas las columnas han sido multiplicadas devuelve -1
  */
  int getColumnaActual() throws RemoteException;
  
  /** Hay un vector A que multiplica a la matriz, con este metodo los clientes
    obtienen ese vector. En realidad es un vector comun a los clientes, por eso
    el nombre.
  */
  int[] getColumnaComun() throws RemoteException;
  
  /** Cada cliente multiplica A por una columna i de la matriz B, dependiendo del
  indice este metodo devuelve una columna de B */
  int[] getColumna(int indice) throws RemoteException;
  
  /** Una vez que los resultados son calculados, hay que colocar el resultado
  en la matriz C, requiere en que columna y cual es el resultado como parametros.*/
  void setResult(int indice,int resultado) throws RemoteException;
}