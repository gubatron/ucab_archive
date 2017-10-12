/*
 * ServidorImpl.java
 *
 * Created on January 10, 2001, 12:36 AM
 */

package matriz;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.MalformedURLException;

/** Unicast remote object implementing Servidor interface.
 *
 * @author Valued Sony Customer
 * @version 1.0
 */
public class ServidorImpl extends UnicastRemoteObject implements Servidor {


  private static int columnaActual = 0;

  /* Matriz A */
  private static final int A[] = { 1,2,3,4 };

  /* Matriz B, si a es de longitud 1xN, esta debe ser NxN */
  private static final int B[][] = {{1,2,3,4},
                                    {1,2,3,4},
                                    {1,2,3,4},
                                    {1,2,3,4}};

  /** Maximo Numero de Columnas */
  private static int maxCols = A.length - 1;

  /** Matriz en donde se colocal las respuestas. */
  private static int C[] = new int[maxCols + 1];


  /** Registration name */
  static protected String objURLName = "matriz/Servidor";

  /** Constructs ServidorImpl object and exports it on default port.
   */
  public ServidorImpl() throws RemoteException {
    super();
  }

  /** Constructs ServidorImpl object and exports it on specified port.
   * @param port The port for exporting
   */
  public ServidorImpl(int port) throws RemoteException {
    super(port);
  }

  /** Register ServidorImpl object with the RMI registry.
   * @param rmiName name identifying the service in the RMI registry, e.g. "matriz/Servidor"
   * @throw RemoteException if cannot be exported or bound to RMI registry
   * @throw MalformedURLException if rmiName cannot be used to construct a valid URL
   * @throw IllegalArgumentException if null passed as rmiName
   */
  public void register(String rmiName) throws RemoteException, MalformedURLException{

    if (rmiName == null) throw new IllegalArgumentException("Registration name can not be null");

    try {
      Naming.rebind(rmiName, this);
    } catch (RemoteException ex){
      Registry r = LocateRegistry.createRegistry(1099);
      r.rebind(rmiName, this);
    }
  }

  /** Main method.
   */
  public static void main(String[] args) throws Exception{

    //System.setSecurityManager(new RMISecurityManager());

    ServidorImpl obj = new ServidorImpl ();
    obj.register(objURLName);

    System.err.println("--Servidor: Servidor de Matrices Registrado.");
  }
  public int getColumnaActual() throws RemoteException {
    return this.columnaActual;
  }
  /** Hay un vector A que multiplica a la matriz, con este metodo los clientes
   * obtienen ese vector. En realidad es un vector comun a los clientes, por eso
   * el nombre.
   */
  public int[] getColumnaComun() throws RemoteException {
    return this.A;
  }
  /** Cada cliente multiplica A por una columna i de la matriz B, dependiendo del
   * indice este metodo devuelve una columna de B */
  public int[] getColumna(int indice) throws RemoteException {
    
    int[] columna = new int[maxCols + 1];
    
    for (int i=0; i< columna.length; i++)
      columna[i] = B[i][indice];
    
    //Aumento el indice de la columnaActual si aun no he terminado.
    if (columnaActual > -1)
    columnaActual = columnaActual + 1;
    //Devuelvo la columna.
    return columna;
  }
  /** Una vez que los resultados son calculados, hay que colocar el resultado
   * en la matriz C, requiere en que columna y cual es el resultado como parametros. */
  public void setResult(int indice,int resultado) throws RemoteException {
    this.C[indice] = resultado;
    System.out.println("--Servidor: Colocando Resultados.");
    System.out.println("--Servidor: Matriz Resultados -> " + mostrarColumna(this.C));
    System.out.println();
      
      //Si la columna actual sobrepasa el maximo de columnas el servidor termino.
      if (columnaActual > maxCols)
      {
        columnaActual = -1;
        System.out.println("--Servidor: Terminé");
      }
  }
  
  /** Imprime en un String el contenido de un arreglo lineal */
  private String mostrarColumna(int [] c)
  {
          String res = new String();
          
          for (int i=0; i<c.length; i++)
          {
            if (i==0) res = "[" + c[i] + ",";
            else
            if (i==(c.length -1)) res += c[i] + "]";
            else  res += c[i] + ",";
          }
          
          return res;
  }
}