/*
 * Cliente.java
 *
 *  Created on January 10, 2001, 12:46 AM
 */

package matriz;

import java.rmi.*;

/**
 *
 * @author Angel Leon
 * @version 1.0
 */
public class Cliente extends Object {

  /** El numero de columna que le toca al cliente.*/
  private int columnaActual = 0;

  /** La columna comun a todos los clientes */
  private int[] columnaA;

  /** Las columna que le toca calcular */
  private int[] columnaB;

  /** Creates new Cliente */
  public Cliente() {
  }

  /** Devuelve el Resultado correspondiente a la multiplicacion de las 2 matrices. */
  private int calcularResultado() {
    int c = 0;
    for (int i=0; i<columnaA.length; i++)
    c += columnaA[i] * columnaB[i];

    return c;
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

  /**
   * @param args the command line arguments
   */

  public static void main (String args[]) {
    
    String host = new String("localhost");
    
    if (args.length > 0 && args[0].length() > 0)
      host = args[0];
    
    //System.setSecurityManager(new RMISecurityManager());
    try {
      java.rmi.Remote obj = (java.rmi.Remote) Naming.lookup("//" + host + ":1099/matriz/Servidor");

      //Creo una instancia de este cliente.
      Cliente cliente = new Cliente();

      //Creo una instancia del servidor en el cliente para ejecutar metodos remotos.
      Servidor servidor = (Servidor) obj;

      //1.- Primero Obtengo el Indice de la columna actual.
      cliente.columnaActual = servidor.getColumnaActual();

      //Si el indice de la columna actual es -1 entonces devolvemos -1
      if (cliente.columnaActual == -1)
      {
        System.out.println("--Cliente: Ya todas las columnas han sido tomadas.");
        System.exit(1);
      }
      else
      {
        System.out.println("--Cliente: Muy bien me toca la columna " + cliente.columnaActual);

        
        //2.- Obtengo la columna comun, es decir columna A.
        cliente.columnaA = servidor.getColumnaComun();
        System.out.println("--Cliente: Recibo la columna A desde el servidor.");
        System.out.println(cliente.mostrarColumna(cliente.columnaA));
        
        //3.- Obtengo la columna que me corresponde de la matriz que esta en el servidor
        cliente.columnaB = servidor.getColumna(cliente.columnaActual);
        System.out.println("--Cliente: Recibo la columna B desde el servidor.(ACTUAL)");
        System.out.println(cliente.mostrarColumna(cliente.columnaB));

        
        //4.- Multiplico las columnas para obtener el resultado que le correponde al cliente.
        //    y Coloco el resultado en el servidor en el lugar correspondiente.
        servidor.setResult(cliente.columnaActual,cliente.calcularResultado());
        System.out.println("--Cliente: Enviando resultado [" + cliente.calcularResultado() +"] a la columna de indice [" + cliente.columnaActual + "]");
        
      }


    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}