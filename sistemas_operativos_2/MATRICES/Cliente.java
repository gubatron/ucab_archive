/*
 * Cliente.java
 *
 * Created on November 1, 2000, 6:53 PM
 */

/** 
 *
 * @author  Angel Leon.
 * @version 
 */
import java.util.*;
import java.net.*;
import java.io.*;

public class Cliente extends Object {

    //Socket que utiliza para conectarse.
    private Socket socket = null;
    
    /** El numero de columna que le toca al cliente.*/
    private int columnaActual = 0;
    
    /** La columna comun a todos los clientes */
    private int[] columnaA;
    
    /** Las columna que le toca calcular */
    private int[] columnaB;
    
    /** Devuelve el Resultado correspondiente a la multiplicacion de las 2 matrices. */
    private int calcularResultado()
    {
      int c = 0;
      for (int i=0; i<columnaA.length; i++)
              c += columnaA[i] * columnaB[i];
      
      return c;      
    }
    
    /** Creates new Cliente */
    public Cliente() {
    }
    
    /** Imprime en Pantalla el contenido de un arreglo lineal */
    private static String mostrarColumna(int [] c)
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
        
    /** Funcion principal, si se le pasa un IP, intenta conectarse a un Servidor
    de matrices en el puerto 6969 de ese IP, sino, intenta hacerlo en localhost.*/
    public static void main(String args[])
    {
      Cliente cliente = new Cliente();
      String ip = new String("127.0.0.1"); //Por defecto loopback.
      
      if (args.length > 0)
        ip = new String(args[0]);
      
      try
      {
        //Se conecta el socket.
        System.out.println("--Cliente: Conectandose a servidor de matrices en " + ip);
        cliente.socket = new Socket(ip,6969);
        
        //Se envia la peticion para recibir la matriz, y la columna correspondiente.
        System.out.println("--Cliente: Pidiendo columna para efectuar calculos");
        PrintStream ps = new PrintStream(cliente.socket.getOutputStream());
        ps.println("RECIBIR_CARGA");
        ps.flush();

        //Se pone el Socket a esperar...
        System.out.println("--Cliente: Socket en espera.");
        ObjectInputStream ois = new ObjectInputStream(cliente.socket.getInputStream());

        //Se obtiene el Indice de la columna correspondiente...
        Integer integer = (Integer) ois.readObject();
        cliente.columnaActual = integer.intValue();
          //Si se acabaron las columnas lo desconecto...
		if (cliente.columnaActual == -1)
        {
            System.out.println("--Cliente: Ya todas las columnas han sido tomadas.");
            cliente.socket.close();
            System.out.println("--Cliente: Estoy Desconectado.");
            System.exit(0);
        }
        System.out.println("--Cliente: Recibido el numero de columna correspondiente -> [" + cliente.columnaActual +"]");
        
        //Se obtiene la columna A.
        cliente.columnaA = (int []) ois.readObject();
        System.out.println("--Cliente: Recibido el Vector A -> " + mostrarColumna(cliente.columnaA));
        
        //Se obtiene la columna B
        cliente.columnaB = (int []) ois.readObject();
        System.out.println("--Cliente: Recibida la columna B[" + cliente.columnaActual + "] -> " + mostrarColumna(cliente.columnaB));
        ois.close();
        //////////////// UNA VEZ OBTENIDA LA COLUMNA B, EL PROTOCOLO ESTABLECIDO CIERRA LA CONEXION /////////////
        
        cliente.socket.close();
        cliente.socket = new Socket(ip,6969);
        ps.flush();
		ps.close();
		
        //Se restablece la comunicacion, se envia el comando, el Indice y el RESULTADO.
        ps = new PrintStream(cliente.socket.getOutputStream());
        
        //Se envia el REQUEST
        ps.println("ENVIAR_RESULTADO");
        
        //Se envia la columna correspondiente a este cliente.
        System.out.println("--Cliente: Columna Actual Enviada [" + cliente.columnaActual + "]");
        ps.println(cliente.columnaActual);
        
        //Se envia el resultado que le corresponde calcular a este cliente.
        System.out.println("--Cliente: Resultado Enviado [" + cliente.calcularResultado() + "]");
        ps.println(cliente.calcularResultado());
        
        ps.flush();
        ps.close();
        cliente.socket.close();
        
      }
      catch(Exception e) { System.out.println("-- Excepcion del Cliente::main " + e); };
    }
  
  
}
