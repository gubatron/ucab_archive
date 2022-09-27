package tuplas.ansi;
/** Clase Ansi - Caracas 30/Noviembre/2000

    Esta clase utiliza las secuencias de escape ANSI
    para poder crear aplicaciones de consola.

    Si se utiliza en DOS/Windows, asegurese de tener
    en el archivo CONFIG.SYS cargado el device ANSI.SYS
    Por ejemplo:

    device=c:\windows\command\ansi.sys

    Esta linea en el config.sys carga el standard ansi
    al arrancar el sistema

    Creada por Angel 'Kangaroo Gubatron' Le¢n
*/
public class Ansi
{
        /** Constante que representa el caracter de escape.<br>
        Es el caracter 27, se puede colocar como "\33" (Lo vi en Microsoft),
        y en Linux en C/C++ sirve como \e para representarlo.
        */
        public static final char ESC = (char) 27;

        // Constantes para representar los modos de pantalla
        public static final int _40X25_BW = 0;
        public static final int _40X25_COL= 1;
        public static final int _80X25_BW = 2;
        public static final int _80X25_COL= 3;
        public static final int _320X200_COL= 4;
        public static final int _320X200_BW = 5;
        public static final int _640X200_BW = 6;

        public static final int BLACK = 30;
        public static final int RED = 31;
        public static final int GREEN = 32;
        public static final int YELLOW = 33;
        public static final int BLUE = 34;
        public static final int MAGENTA = 35;
        public static final int CYAN = 36;
        public static final int WHITE = 37;

        public static final int TURN_OFF_ATTRS = 0;
        public static final int HIGH_INTENSITY = 1;
        public static final int UNDERLINE = 4;
        public static final int BLINK = 5;
        public static final int REVERSE = 7;
        public static final int INVISIBLE = 8;

        /** M‚todo gotoXY(int x,int y)<br>
        Coloca el cursor en la posici¢n (x,y) dada.
        Se toma como origen de la pantalla la esquina superior izquierda.
        */
        public static void gotoXY(int x, int y)
        {
                System.out.print(ESC + "[" + y + ";" + x + "f");
        }

        /** M‚todo cls()<br>
        Sirve para limpiar la pantalla.
        */
        public static void cls()
        {
                System.out.print(ESC + "[2J");
        }

        /** M‚todo eraseLine()
        Elimina desde la posici¢n actual del cursor hasta el fin de la linea.
        */
        public static void eraseLine()
        {
                System.out.print(ESC + "[K");
        }

        /** M‚todo setDisplayMode(int mode)
        Sirve para cambiar la resolucion y colores de la pantalla.
        Utilize las constantes definidas en esta clase.
        */
        public static void setDisplayMode(int mode)
        {
                System.out.print(ESC + "[=" + mode + "h");
        }

        /** M‚todo wrapLine(boolean) <br>
        Cuando las lineas son mas largas que el ancho de pantalla, las mismas<br>
        pueden ser truncadas, o envueltas a la linea inferior.<br>
        <b>true</b> - Envuelve la linea.
        <b>false</b> - Trunca la linea.
        */
        public static void wrapLine(boolean state)
        {
                if (state)
                System.out.print(ESC + "[=7h");
                else
                System.out.print(ESC + "[=7l");
        }

        /** M‚todo setForeground(int color)<br>
        Coloca el color de las letras. Utilice las constantes de color definidas en
        la clase Ansi. Tambien pone atributos como Alta intensidad, SUbrayar, Blink,Invisible...
        */
        public static void setForeground(int color)
        {
                System.out.print(ESC + "[" + color + "m");
        }

        /** M‚todo setBackground(int color)<br>
        Coloca el color del fondo, en realidad,a la constante de color
        que entra como parametro se le suma 10 internamente, ya que no
        son las mismas para fondo y letras. En resumen utilize las mismas
        constantes de colores que se utilizan para setForeground.
        Funciona de manera analoga a setForeGround
        */
        public static void setBackground(int color)
        {
                setForeground(color + 10);
        }

        public static void main(String args[])
        {
        }
}
