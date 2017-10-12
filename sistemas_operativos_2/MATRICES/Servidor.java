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
import java.net.*;
import java.io.*;
/**
<b>Servidor.</b><br>
Esta clase hace lo siguiente dependiendo de las peticiones de sus clientes.<br>
<hr>
<EM>RECIBIR_CARGA</EM><br>
	Envia una columna i de la matriz B, al cliente i que efectua la petición.<br>
<EM>ENVIAR_RESULTADO</EM><br>
	Coloca el Resultado devuelto por el cliente i, en la posicion (1,i) de la matriz de resultados.<br>
<hr>
*/
public class Servidor extends Object
{
	private static final String RECIBIR_CARGA = "RECIBIR_CARGA";
	private static final String ENVIAR_RESULTADO = "ENVIAR_RESULTADO";
	
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
	
	private static int C[] = new int[maxCols + 1];
	
	/** El Socket del Servidor. */
	private static ServerSocket server_socket;
	
	/** El Socket Temporal */
	private static Socket temp_socket;


        /** Devuelve el vector de tamaño maxCols, el mismo es la columna que se envia al cliente. */
        private static int[] obtenerColumna(int col)
        {
          int[] columna = new int[maxCols + 1];
          for (int i=0; i< columna.length; i++)
            columna[i] = B[i][col];


          return columna;
        }
        
        /** Imprime en un String el contenido de un arreglo lineal */
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
        
	/** Dependiendo de la columnaActual se envia la columna, y se aumenta el indice.<br>
	Tambien se envia el Vector A<br>
        <b>Devuelve <u>-1</u> si no hay mas columnas que enviar</b>
	*/
	private static int enviarColumna()
	{
		System.out.println("--Servidor: Cliente Actual = " + columnaActual);
                
                if (columnaActual == -1)
                {
                  try
                  {
                      ObjectOutputStream oos = new ObjectOutputStream(temp_socket.getOutputStream());
                      //Primero se envia el numero de la columna...                      
                      Integer indice = new Integer(columnaActual);
                      oos.writeObject((Integer) indice);
                      System.out.println("--Servidor: NUMERO DE COLUMNA ENVIADO [" + columnaActual + "]");
                      oos.flush();
                      oos.close();
                   }
                  catch (Exception e) { System.out.println("-- Excepcion del Servidor::enviarColumna " + e); };
                      return -1;
                }
                else
                {
                  try
                  {
                      ObjectOutputStream oos = new ObjectOutputStream(temp_socket.getOutputStream());
                      
                      //Primero se envia el numero de la columna...                      
                      Integer indice = new Integer(columnaActual);
                      oos.writeObject((Integer) indice);
                      System.out.println("--Servidor: NUMERO DE COLUMNA ENVIADO [" + columnaActual + "]");

                      //Se envia la columna A.
                      oos.writeObject((int []) A);
                      System.out.println("--Servidor: VECTOR A ENVIADO -> " + mostrarColumna(A));
                      
                      //Luego se envia la columna B...
                      int[] columna = obtenerColumna(indice.intValue());
                      oos.writeObject((int []) columna);
                      
                      // Se muestra en el log, lo que se ha enviado al cliente n. (Utilizo indice, porque ya columnaActual incrementó)
                      System.out.println("--Servidor: COLUMNA B[" + indice.intValue() + "] ENVIADA -> " + mostrarColumna(columna));                      
                      
                      //Por ahora se cierra el socket, para permitir que otros puedan hacer los pedidos.
                      oos.flush();
                      oos.close();                      
                  }
                  catch (Exception e) { System.out.println("--Excepcion de Servidor::enviarColumna " + e );};
                  
                }
                
                //En caso de que sea la ultima columna.
                if (columnaActual == maxCols)
			columnaActual = -1;
		else
                        columnaActual++;
		
                return columnaActual;
	}		
	
	/** Va colocando el Resultado en el indice que le corresponde. */
	private static void colocarResultado(BufferedReader r)
	{
		int columna = 0;
		int resultado = 0;
		
		try
		{
			columna = Integer.parseInt(r.readLine());
			resultado = Integer.parseInt(r.readLine());
			
			System.out.println("--Servidor: Columna [" + columna + "] - Resultado [" + resultado + "]");						
		}
		catch (Exception e) { System.out.println("--Excepcion del Servidor:colocarResultado " + e);};
																									
		C[columna] = resultado;
		
		if (columna == maxCols)
			System.out.println("--Servidor: Resultado final de la multiplicacion -> " + mostrarColumna(C));
		else
			System.out.println("--Servidor: Resultado preliminar de la multiplicacion -> " + mostrarColumna(C));
	}	

	/** Inicia el Servidor. */                                                                            	
	private static void main(String args[])
	{
                System.out.println("--Servidor de multiplicacion de matrices inicializado.\n\tAngel KANGAROO GUBATRON Leon / Oct - 2000\n");
                System.out.println("------------------------------------------------------\n");
                
		while (true)
		{
			try
			{
				server_socket = new ServerSocket(6969);
				temp_socket = null;
				temp_socket = server_socket.accept();

				//1.- Recibo el REQUEST.
				BufferedReader br = new BufferedReader(new InputStreamReader(temp_socket.getInputStream()));
				String current_request = new String(br.readLine());
									

				//Si pide que le envien las columnas.
				if (current_request.equals(RECIBIR_CARGA))
				{
				  System.out.println("============================================");
				  System.out.println("--Servidor: Un Nuevo Cliente ha pedido CARGA");
				  enviarColumna();
				}
				//Si manda el resultado el cliente.
				else if (current_request.equals(ENVIAR_RESULTADO))
				{
				  System.out.println("============================================");
				  System.out.println("--Servidor: Un Cliente Envia Resultados.");
				  colocarResultado(br);
				  if (columnaActual == -1) 
				  {
					  System.out.println("--Servidor: Resultado Final Alcanzado, Servidor Desconectado.\nAngel Leon / OCtubre 2000");
					  System.exit(1);
				  }
				}								
				
				br.close();					
				temp_socket.close();
				server_socket.close();
				temp_socket = null;
				server_socket = null;
			}
			catch(Exception e) {System.out.println("-- Excepcion de Servidor::main -> " + e);};		
		}
	}

}
