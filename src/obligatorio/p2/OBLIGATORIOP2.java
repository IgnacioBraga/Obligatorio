
package obligatorio.p2;

import java.util.Scanner;

public class OBLIGATORIOP2 {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mostrarMenu();
         // Crear jugadores
        Jugador jugador1 = new Jugador("Ignacio", 25);
        Jugador jugador2 = new Jugador("Luis", 27);

        // Crear partida
        Partida partida = new Partida(jugador1, jugador2);
        partida.tablero.mostrarTablero();

        // Realizar movimientos de prueba
        partida.jugarTurno(2, 3, "D", 4);
        partida.jugarTurno(4, 5, "A", 3);

        // Mostrar tablero actualizado
        partida.tablero.mostrarTablero();

    }
}

class Jugador {
    String nombre;
    int edad;
    int triangulosGanados;

    Jugador(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.triangulosGanados = 0;
    }

    void ganarTriangulo() {
        triangulosGanados++;
    }
}

class Banda {
    int filaInicio;
    int columnaInicio;
    String direccion;
    int longitud;

    Banda(int filaInicio, int columnaInicio, String direccion, int longitud) {
        this.filaInicio = filaInicio;
        this.columnaInicio = columnaInicio;
        this.direccion = direccion;
        this.longitud = longitud;
    }
}
class Tablero {
    final int filas = 7;
    final int columnas = 13;
    char[][] tablero;
    Banda[] bandas;
    int cantidadBandas;

    Tablero() {
        tablero = new char[filas][columnas];
        bandas = new Banda[50];
        cantidadBandas = 0;
        inicializarTablero();
    }

    void inicializarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = '*';
            }
        }
    }

    boolean colocarBanda(int filaInicio, int colInicio, String direccion, int longitud) {
        boolean ubicacionValida = validarUbicacion(filaInicio, colInicio, direccion, longitud);
        if (ubicacionValida && cantidadBandas < bandas.length) {
            bandas[cantidadBandas] = new Banda(filaInicio, colInicio, direccion, longitud);
            actualizarTablero(bandas[cantidadBandas]);
            cantidadBandas++;
            return true;
        }
        return false;
    }

    boolean validarUbicacion(int filaInicio, int colInicio, String direccion, int longitud) {
        int filaFin = filaInicio;
        int columnaFin = colInicio;

        if (direccion.equals("D")) columnaFin += longitud;
        else if (direccion.equals("A")) columnaFin -= longitud;
        else if (direccion.equals("C")) { filaFin += longitud; columnaFin += longitud; }
        else if (direccion.equals("Z")) { filaFin += longitud; columnaFin -= longitud; }
        else if (direccion.equals("E")) { filaFin -= longitud; columnaFin += longitud; }
        else if (direccion.equals("Q")) { filaFin -= longitud; columnaFin -= longitud; }
        else return false;

        return filaFin >= 1 && filaFin < filas && columnaFin >= 1 && columnaFin < columnas;
    }

    void actualizarTablero(Banda banda) {
        int fila = banda.filaInicio;
        int columna = banda.columnaInicio;
        int longitud = banda.longitud;
        String direccion = banda.direccion;

        for (int i = 0; i < longitud; i++) {
            if (direccion.equals("D")) tablero[fila][columna + i] = '-';
            else if (direccion.equals("A")) tablero[fila][columna - i] = '-';
            else if (direccion.equals("C")) tablero[fila + i][columna + i] = '\\';
            else if (direccion.equals("Z")) tablero[fila + i][columna - i] = '/';
            else if (direccion.equals("E")) tablero[fila - i][columna + i] = '/';
            else if (direccion.equals("Q")) tablero[fila - i][columna - i] = '\\';
        }
    }

  void mostrarTablero() {
    System.out.print("   ");
    int col = 0;
    while (col < columnas) {
        System.out.print((char) ('A' + col) + " ");
        col = col + 1;
    }
    System.out.println();

    int fila = 0;
    while (fila < filas) {
        System.out.print((fila + 1) + "  ");
        col = 0;
        while (col < columnas) {
            if (esParteDelHexagono(fila, col)) {
                System.out.print(tablero[fila][col] + " ");
            } else {
                System.out.print("  ");
            }
            col = col + 1;
        }
        System.out.println();
        fila = fila + 1;
    }
}

boolean esParteDelHexagono(int fila, int col) {
    int[][] posiciones = {
        {3, 5, 7, 9},                // Fila 0 → D F H J
        {2, 4, 6, 8, 10},           // Fila 1 → C E G I K
        {1, 3, 5, 7, 9, 11},        // Fila 2 → B D F H J L
        {0, 2, 4, 6, 8, 10, 12},    // Fila 3 → A C E G I K M
        {1, 3, 5, 7, 9, 11},        // Fila 4 → B D F H J L
        {2, 4, 6, 8, 10},           // Fila 5 → C E G I K
        {3, 5, 7, 9}                // Fila 6 → D F H J
    };

    boolean dentro = false;
    int i = 0;
    while (i < posiciones[fila].length) {
        if (posiciones[fila][i] == col) {
            dentro = true;
        }
        i = i + 1;
    }
    return dentro;
}




}


class Partida {
    Jugador jugador1;
    Jugador jugador2;
    Tablero tablero;
    boolean turnoJugador1;

    Partida(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.tablero = new Tablero();
        this.turnoJugador1 = true;
    }

    void jugarTurno(int filaInicio, int columnaInicio, String direccion, int longitud) {
        Jugador jugadorActual = turnoJugador1 ? jugador1 : jugador2;
        boolean colocada = tablero.colocarBanda(filaInicio, columnaInicio, direccion, longitud);

        if (colocada) {
            System.out.println(jugadorActual.nombre + " colocó una banda.");
            turnoJugador1 = !turnoJugador1;
        } else {
            System.out.println("Movimiento inválido.");
        }
    }
}

class Menu {
    Scanner scanner = new Scanner(System.in);
    Jugador jugador1, jugador2;
    Partida partida;

    void mostrarMenu() {
        System.out.print("Nombre del jugador 1: ");
        jugador1 = new Jugador(scanner.nextLine(), 25);
        System.out.print("Nombre del jugador 2: ");
        jugador2 = new Jugador(scanner.nextLine(), 27);

        partida = new Partida(jugador1, jugador2);
        partida.tablero.mostrarTablero();

        while (true) {
            System.out.print("Ingrese fila, columna, dirección, longitud (X para salir): ");
            String entrada = scanner.nextLine();
            if (entrada.equals("X")) break;

            String[] partes = entrada.split(" ");
            partida.jugarTurno(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]), partes[2], Integer.parseInt(partes[3]));
            partida.tablero.mostrarTablero();
        }
    }
}
