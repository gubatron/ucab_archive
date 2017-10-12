/*
 * ClienteFTP.java
 *
 * Created on November 4, 2000, 1:09 PM
 */
import java.util.*;
import java.net.*;
import java.io.*;

/**
 *
 * @author  Angel Leon.
            Luis Fleitas.
 * @version 1a.
 */

/** Sencillo Cliente FTP, funciona en un Shell con los comandos:<br>
<li>LS</li>
<li>PUT</li>
<li>GET</li>
<li>CD</li>
*/
public class ClienteFTP extends Object {

  /** Servidor al cual estoy conectado.<br>Por defecto el servidor ftp es: <b><em>ftp.cantv.net</em></b>*/
  public static String _ip_remoto = new String("ftp.cantv.net");
  
  /** Nombre de Usuario */
  public static String _login = new String("anonymous");
  
  /** Password del Usuario*/
  public static String _password;
  
  /** La ultima linea leida desde el shell */
  public static String _lineaActual;
  
  //////////////////////////////////////////////////////////////////////////
  
  /** Socket de conexion al servidor FTP */
  public static Socket _socket;
  
  /** Socket de Conexion de Datos*/
  public static Socket _data_socket;
  
  /** Socket de Servidor para que se establesca la conexion con la maquina local */
  public static ServerSocket _server_socket;
  
  /** Canal de entrada de datos */
  public static BufferedReader _br;
  
  /** Canal de salida, por aqui se envian los requests al servidor */
  public static PrintStream _ps;
  
  //////////////////////////////////////////////////////////////////////////
  
  /** Para poder hacer LS y Conectarme muchas veces, se deben cambiar los
  puertos*/
  public static int a;

  
  /** Este es el numero de cambiar... */
  public static int b;
  
  /** Este es el puerto local del ServerSocjet que se incrementar  por cada
  llamada a PORT */
  public static int port = 4444;
  
  //////////////////////////////////////////////////////////////////////////
  
  /** Da la bienvenida al programa y pide los datos del usuarios una vez que se conecta al servidor FTP */  
  public static void conectar()
  {
       System.out.println("Cliente FTP en Java - Sistemas Operativos II - UCAB Noviembre 2000\n" +
      "Autores: Luis Fleitas && Angel Leon.\n\n Conectandose con Servidor -> " + _ip_remoto + "\n\n");
      
      //1.- Intenta Efectuar la conexion.      
      try
      {
        _socket = new Socket(_ip_remoto,21);
        
        //Obtengo canal de salida.
        OutputStream os = _socket.getOutputStream();
        _ps = new PrintStream(os);
        
        //Obtengo canal de entrada.
        _br = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
        mensajeDelServidor();
        
        System.out.print("--INSERTE SU LOGIN (" + _ip_remoto +"): ");
        _login = read();
        
        //Envio el LOGIN.
        _ps.println("USER " + _login);
        mensajeDelServidor();
        
        System.out.print("--INSERTE SU PASSWORD: ");
        _password = read();
        
        //Envio el PASSWORD.
        _ps.println("PASS " + _password);
        mensajeDelServidor();
        
      }
      catch (Exception e) 
      { System.out.println("--Hubo un ERROR DE CONEXION.\nVerifique que este conectado, y el nombre del Servidor FTP"); };
      
      //2.- Identificarse con el Servidor.
      System.out.println("");
  }

  /** Sirve para abrir un puerto en la maquina local para que se conecte el Servidor */
  public static void port()
  {
    InetAddress iadd = _socket.getLocalAddress();
    String ipString = iadd.getHostAddress();
    StringTokenizer tokenizer = new StringTokenizer(ipString,".");
    
    //Actualizo los valores de a y b. Son el valor del puerto local de mi
    //ServerSocket como 2 bytes hexadecimales.

    try
    {
        _server_socket = new ServerSocket(port);
        port++;
    }
    catch(Exception e)
    {
        System.out.println("-- EXCEPTION EN PORT :: " + e);
    };

    a = ((_server_socket.getLocalPort() >>> 8) 
     & 0xFF);
    b = (_server_socket.getLocalPort() & 0xFF);

    /////////////////////////////////////////////////////////////////////
    //Coloco el IP y el Puerto local de la forma
    // ip1,ip2,ip3,ip4,p1,p2
    // Donde i son los 4 numeros del ip y p1 y p2 representan el puerto
    // en 2 bytes hexadecimales pasados a decimales.
    //Ej. Si el puerto es 4444 es 0x115Ch -> 11h=17d & 5Ch=92d
    //    y si el ip es 200.44.25.72, entonces el comando quedaria
    //    PORT 200,44,25,72,17,92
    /////////////////////////////////////////////////////////////////////

    ipString = new String();
    ipString += tokenizer.nextToken() + ",";
    ipString += tokenizer.nextToken() + ",";
    ipString += tokenizer.nextToken() + ",";
    ipString += tokenizer.nextToken() + ",";
    ipString += a + "," + b;

    b++;
    
    //System.out.println("PORT " + ipString);
    _ps.print("PORT " + ipString + "\r\n");
    mensajeDelServidor();
  }
  
  /** Lista el contenido de la carpeta actual. */
  public static void ls()
  {
    try
    {
          //Se hace PORT!!!!
          port();
          _ps.println("LIST");
    
          try
          {
            
            mensajeDelServidor();
            
            _data_socket = _server_socket.accept();
        
            BufferedReader d = new BufferedReader (new InputStreamReader(_data_socket.getInputStream()));
            String buffer = new String();

            //Se listan los archivos.
            while ((buffer = d.readLine()) != null)
              System.out.println(buffer);
            
            mensajeDelServidor();
            
            //Se cierran los sockets.
            d.close();
            _data_socket.close();
            _server_socket.close();
            
          }
          catch (Exception e)
          {
              System.out.println("-- EXCEPTION EN LS :: " + e);
              e.printStackTrace();
          }
     }
     catch (Exception e) 
     { System.out.println("--EXCEPCION LS :: " + e); };
  }
  
  /** Baja un archivo del Servidor a la maquina local. */
  public static void get(String linea)
  {
    int length = linea.length();
    StringTokenizer tokenizer = new StringTokenizer(linea," ");
    tokenizer.nextToken();
    
    String argumento = tokenizer.nextToken();
    
    //Se hace PORT!!!!
    port();
    _ps.println("RETR " + argumento);
    mensajeDelServidor();
    
    //Abro la conexion de Datos.. y me lo traigo por un DataInputStream y lo escribo en un archivo.
    try
    {
      _data_socket = _server_socket.accept();
      
      File file = new File(argumento);
      FileOutputStream fis = new FileOutputStream(file);
      
      DataInputStream dis = new DataInputStream(_data_socket.getInputStream());
      
      //Escribir en el archivo.
      byte[] arreglo_de_bytes = new byte[1];
      while ( dis.read(arreglo_de_bytes) != -1)
      {
        fis.write(arreglo_de_bytes);
      }
      
      dis.close();
      
      fis.flush();
      fis.close();
  
      _data_socket.close();
      _server_socket.close();
      
      _server_socket = null;
      _data_socket = null;      
      
    }
    catch (Exception e)
    {
      System.out.println("--EXCEPCION GET :: " + e);
    };
    
  }
  
  /** Coloca un archivo local en el servidor. */
  public static void put(String linea)
  {
    int length = linea.length();
    StringTokenizer tokenizer = new StringTokenizer(linea," ");
    tokenizer.nextToken();
    
    String argumento = tokenizer.nextToken();
    
    //Se hace PORT!!!!
    port();
    _ps.println("STOR " + argumento);
    mensajeDelServidor();
    
    try
    {
      _data_socket = _server_socket.accept();
      
      //A partir del nombre creo el archivo...
      File file = new File(argumento);
      FileInputStream fis = new FileInputStream(file);
      
      DataOutputStream dos = new DataOutputStream(_data_socket.getOutputStream());
      
      //Escribir el Archivo en el servidor FTP.
      //Mientras tenga que leer del archivo... sigo enviando.
      byte[] arreglo_de_bytes = new byte[fis.available()];
      
      while (fis.read(arreglo_de_bytes) != -1)
      {
        dos.write(arreglo_de_bytes);
      }
      
      dos.flush();
      dos.close();
      
      fis.close();
      _data_socket.close();
      _server_socket.close();

    }
    catch (Exception e) { System.out.println("-- Excepcion PUT :: " + e);};
  }
  
  /** Sirve para cambiar en que directorio me encuentro. */
  public static void cd(String linea)
  {
    int length = linea.length();
    //Por que CD tiene Solo 2 letras.
    StringTokenizer st = new StringTokenizer(linea);
    
    st.nextToken();
    
    String argumento = null;
    try
    {
      argumento = st.nextToken();
    }
    catch (Exception e) { argumento = "."; };
    
    _ps.println("CWD " + argumento);
  }
  
  /** Lee una linea desde el teclado.
  @returns El String Leido, sin el caracter CRLF */
  public static String read()
  {
    String s = new String();
    String luis = null;
    try
    {
            byte[] buffer = new byte[100];
            System.in.read(buffer);

            s = new String(buffer);
            
            
            StringTokenizer st = new StringTokenizer(s);
            int tokens = st.countTokens();
            
            switch (tokens)
            {
              //La linea sola.
              case 1:
                s = "   ";
                break;
              
              //Una palabra.
              case 2:
                s = st.nextToken();
                break;
              
              //Un comando con argumento.
              case 3:
                s = st.nextToken();
                s += " " + st.nextToken();
                break;
              
            }
            
            if (!s.equals("   "))
            System.out.println("[" + s +"]");

    }
    catch(Exception i) {};
    return s;
  }
  
  /** Muestra en pantalla la respuesta del Servidor */
  public static void mensajeDelServidor()
  {
          String _mensaje = new String ();
          String no_guion = null;
          
          try
          {
            //System.out.println(">>>>>>>>>>>>>>>>>>>>> ESPERE - MENSAJE DESDE EL SERVIDOR:");
              do
              {
                _mensaje = _br.readLine();
                System.out.println(_mensaje);
                
                no_guion = _mensaje.substring(3,4);
                
                if (_mensaje == null) continue;
                
                //Si se encuentra con un espacio se sale...
                if (no_guion.equals(" ")) break;                
              
              } while (true);

            //System.out.println("<<<<<<<<<<<<<<<<<<<<< FIN MENSAJE DESDE EL SERVIDOR");
          }
          catch (Exception e) 
          {
            //System.out.println("<<<<<<<<<<<<<<<<<<<<< FIN MENSAJE DESDE EL SERVIDOR");
            mensajeDelServidor();
          };          
  }
  
  
  /** Metodo Principal necesita el IP o el nombre del Servidor.*/
  public static void main(java.lang.String[] args) {
    

    if (args.length == 0)
    {
      System.out.println(
      "Cliente FTP en Java - Sistemas Operativos II - UCAB Noviembre 2000\n" +
      "Autores: Luis Fleitas && Angel Leon.\n\n" + 
      "USO: java ClienteFTP <nombreServidor>\n\n");
    }
    else
    {
      //El ip sera el pasado por el Shell...
      _ip_remoto = args[0];      
      conectar();      
    try
    {
            do
            {
              //Este es el Prompt
              System.out.print("ftp> ");
              //Se lee hasta que se presione ENTER...
              _lineaActual = read(); 

              //Y dependiendo de lo que se haya leido... se efectua una acción correspondiente.

              //.- Salir.
              if (_lineaActual.equals("QUIT") || _lineaActual.equals("quit") || _lineaActual.equals("exit") || _lineaActual.equals("EXIT")) 
              {
                Runtime.getRuntime().exit(1);
              }
              //.- Abrir Otro Servidor FTP
              else if (_lineaActual.equals("OPEN") || _lineaActual.equals("open"))
              {
                System.out.println("--INTRODUZCA EL IP O EL NOMBRE DEL SERVIDOR ");
                _ip_remoto = read();
                conectar();
              }
              else if (_lineaActual.equals("HELP") || _lineaActual.equals("help") || _lineaActual.equals("?"))
              {
                System.out.println
                (
                "-- AYUDA: COMANDOS DISPONIBLES PARA ESTE CLIENTE FTP:\n" +
                "   QUIT    Sale del Programa" +
                "   HELP,?    Muestra esta ayuda\n" +
                "   LS      Muestra la lista remota de Archivos\n" + 
                "   CD      Sirve para Cambiar Directorio\n" +
                "   PWD     Muestra en que carpeta se encuentra\n" +
                "   GET     Sirve para copiar un archivo desde el servidor a la maquina local\n" +
                "   PUT     Sirve para copiar un archivo desde la maquina local al servidor\n\n"
                );
              }
              else if (_lineaActual.equals("LS") || _lineaActual.equals("ls") || _lineaActual.equals("dir"))
              {
                  System.out.println("---> LIST");
                  ls();
              }
              else if (_lineaActual.substring(0,2).equals((String) "CD") || _lineaActual.substring(0,2).equals((String) "cd"))
              {
                  System.out.println("---> CWD");
                  cd(_lineaActual);
                  mensajeDelServidor();
              }
              else if (_lineaActual.equals("PWD") || _lineaActual.equals("pwd"))
              {
                  _ps.println("PWD");
                  System.out.println("---> PWD");
                  mensajeDelServidor();
              }
              else if (_lineaActual.substring(0,3).equals((String) "PUT") || _lineaActual.substring(0,3).equals((String) "put"))
              {
                  System.out.println("---> PUT");
                  put(_lineaActual);
                  mensajeDelServidor();
              }
              else if (_lineaActual.substring(0,3).equals((String) "GET") || _lineaActual.substring(0,3).equals((String) "get"))
              {
                  System.out.println("---> GET");
                  get(_lineaActual);
                  mensajeDelServidor();
              }
              else if (_lineaActual.equals("   "))
              {
              }
              else
              {
                System.out.println("-- ERROR <" + _lineaActual + "> NO HA SIDO IMPLEMENTADO PARA ESTE CLIENTE");          
              }
            }
            while (true);
      }
      catch(Exception ee) { 
      System.out.println("-- Excepcion en main:");
      ee.printStackTrace();};
    } //if
  }
  
  
}
