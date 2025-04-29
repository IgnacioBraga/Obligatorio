
package obligatorio.p2;

import java.util.Scanner;

public class OBLIGATORIOP2 {
    // Trabajo desarrollado por: TU NOMBRE REAL - TU NUMERO DE ESTUDIANTE

    static String[] nombresJugadores = new String[100];
    static int[] edadesJugadores = new int[100];
    static int[] partidasGanadas = new int[100];
    static int[] rachaActual = new int[100];
    static int cantidadJugadores = 0;
    static boolean partidaConfigurada = false;

    static boolean requiereContacto = false;
    static boolean longitudVariable = false;
    static int cantidadBandas = 10;
    static int cantidadTableros = 1;

    static int longitudFija = 2;

    static char[][] tablero = new char[7][13];

    static int triangulosBlanco = 0;
    static int triangulosNegro = 0;

    static String[] historialJugadas = new String[100];
    static int cantidadJugadas = 0;

    static int indiceJugadorBlanco = -1;
    static int indiceJugadorNegro = -1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=======================================");
        System.out.println("Trabajo desarrollado por: TU NOMBRE REAL - TU NUMERO DE ESTUDIANTE");
        System.out.println("=======================================");

        boolean salir = false;
        while (!salir) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("a) Registrar un jugador");
            System.out.println("b) Configurar la partida");
            System.out.println("c) Comienzo de partida");
            System.out.println("d) Mostrar ranking y racha");
            System.out.println("e) Terminar el programa");
            System.out.print("Ingrese opcion: ");
            String opcion = scanner.nextLine();

            switch (opcion.toLowerCase()) {
                case "a":
                    registrarJugador(scanner);
                    break;
                case "b":
                    if (cantidadJugadores >= 2) {
                        configurarPartida(scanner);
                        partidaConfigurada = true;
                    } else {
                        System.out.println("Debe registrar al menos 2 jugadores antes de configurar la partida.");
                    }
                    break;
                case "c":
                    if (partidaConfigurada) {
                        comenzarPartida(scanner);
                    } else {
                        System.out.println("Debe configurar la partida antes de comenzarla.");
                    }
                    break;
                case "d":
                    mostrarRanking();
                    break;
                case "e":
                    System.out.println("Programa finalizado. Gracias por jugar!");
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion incorrecta. Ingrese nuevamente.");
            }
        }
    }

    static void registrarJugador(Scanner scanner) {
        System.out.print("Ingrese nombre del jugador: ");
        String nombre = scanner.nextLine();

        boolean nombreExiste = false;
        for (int i = 0; i < cantidadJugadores; i++) {
            if (nombresJugadores[i].equalsIgnoreCase(nombre)) {
                nombreExiste = true;
                break;
            }
        }

        if (nombreExiste) {
            System.out.println("El nombre ya existe. No se puede registrar.");
        } else {
            int edad = -1;
            while (edad < 1) {
                System.out.print("Ingrese edad del jugador: ");
                try {
                    edad = Integer.parseInt(scanner.nextLine());
                    if (edad < 1) System.out.println("Edad inválida.");
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número válido.");
                }
            }
            nombresJugadores[cantidadJugadores] = nombre;
            edadesJugadores[cantidadJugadores] = edad;
            partidasGanadas[cantidadJugadores] = 0;
            rachaActual[cantidadJugadores] = 0;
            cantidadJugadores++;
            System.out.println("Jugador registrado exitosamente.");
        }
    }

    static void configurarPartida(Scanner scanner) {
    System.out.print("¿Desea usar configuración avanzada? (S/N): ");
    String respuesta = scanner.nextLine();

    if (!respuesta.equalsIgnoreCase("S")) {
        // Configuración por defecto
        requiereContacto = false;
        longitudVariable = false;
        longitudFija = 4;
        cantidadBandas = 10;
        cantidadTableros = 1;
        System.out.println("Configuración por defecto aplicada.");
        return;
    }

    // Configuración avanzada
    System.out.print("¿Las bandas deben tocar otra anterior? (S/N): ");
    requiereContacto = scanner.nextLine().equalsIgnoreCase("S");

    System.out.print("¿La longitud de las bandas es variable? (S/N): ");
    longitudVariable = scanner.nextLine().equalsIgnoreCase("S");

    if (!longitudVariable) {
        System.out.print("Ingrese la longitud fija de las bandas (1-4): ");
        longitudFija = Integer.parseInt(scanner.nextLine());
    }

    System.out.print("Ingrese cantidad de bandas para terminar la partida: ");
    cantidadBandas = Integer.parseInt(scanner.nextLine());

    System.out.print("Ingrese cantidad de tableros a mostrar (1-4): ");
    cantidadTableros = Integer.parseInt(scanner.nextLine());

    System.out.println("Configuración avanzada aplicada.");
}


    static void comenzarPartida(Scanner scanner) {
        System.out.println("\nLista de jugadores disponibles:");
        for (int i = 0; i < cantidadJugadores; i++) {
            System.out.println((i + 1) + ") " + nombresJugadores[i]);
        }

        int jugador1 = -1, jugador2 = -1;

        while (jugador1 < 1 || jugador1 > cantidadJugadores) {
            System.out.print("Seleccione número del primer jugador (Blanco): ");
            try {
                jugador1 = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }

        while (jugador2 < 1 || jugador2 > cantidadJugadores || jugador2 == jugador1) {
            System.out.print("Seleccione número del segundo jugador (Negro): ");
            try {
                jugador2 = Integer.parseInt(scanner.nextLine());
                if (jugador2 == jugador1) {
                    System.out.println("No puede elegir el mismo jugador.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }

        indiceJugadorBlanco = jugador1 - 1;
        indiceJugadorNegro = jugador2 - 1;

        inicializarTablero();
        triangulosBlanco = 0;
        triangulosNegro = 0;
        cantidadJugadas = 0;

        System.out.println("\nComienza la partida entre:");
        System.out.println("Blanco: " + nombresJugadores[indiceJugadorBlanco]);
        System.out.println("Negro: " + nombresJugadores[indiceJugadorNegro]);

        jugar(scanner);
    }

    static void inicializarTablero() {
    for (int fila = 0; fila < 7; fila++) {
        for (int col = 0; col < 13; col++) {
            tablero[fila][col] = ' '; // espacios vacíos por defecto
        }
    }
    for (int fila = 0; fila < 7; fila++) {
        int minCol = Math.abs(3 - fila);
        int maxCol = 12 - Math.abs(3 - fila);
        for (int col = minCol; col <= maxCol; col += 2) {
            tablero[fila][col] = '*'; // solo los nodos (intersecciones)
        }
    }
}


    static void jugar(Scanner scanner) {
        int bandasColocadas = 0;
        boolean turnoBlanco = true;

        while (bandasColocadas < cantidadBandas) {
            mostrarTablero();
            System.out.println(turnoBlanco ? "Turno de Blanco" : "Turno de Negro");
            System.out.print("Ingrese jugada (ej: D1D4), H para historial, o X para abandonar: ");
            String jugada = scanner.nextLine().toUpperCase();

            if (jugada.equals("H")) {
                mostrarHistorial();
                continue;
            }

            if (jugada.equals("X")) {
                System.out.println((turnoBlanco ? "Negro" : "Blanco") + " gana por abandono!");
                actualizarRachas(turnoBlanco ? false : true);
                return;
            }

            if (procesarJugada(jugada, turnoBlanco)) {
                historialJugadas[cantidadJugadas++] = jugada;
                bandasColocadas++;
                turnoBlanco = !turnoBlanco;
            } else {
                System.out.println("Jugada inválida. Intente de nuevo.");
            }
        }

        finalizarPartida();
    }

    static boolean procesarJugada(String jugada, boolean turnoBlanco) {
    if (jugada.length() != 4) return false;

    int col = jugada.charAt(0) - 'A';
    int fila = Character.getNumericValue(jugada.charAt(1)) - 1;
    char direccion = jugada.charAt(2);
    int cantidad = Character.getNumericValue(jugada.charAt(3));

    if (fila < 0 || fila > 6 || col < 0 || col > 12) return false;
    if (tablero[fila][col] != '*') return false;
    if (!longitudVariable && cantidad != longitudFija) return false;

    int df = 0, dc = 0;
    switch (direccion) {
        case 'D': dc = 2; break;              // Este
        case 'A': dc = -2; break;             // Oeste
        case 'E': df = -1; dc = 1; break;     // Noreste
        case 'C': df = 1; dc = 1; break;      // Sureste
        case 'Q': df = -1; dc = -1; break;    // Noroeste
        case 'Z': df = 1; dc = -1; break;     // Suroeste
        default: return false;
    }

    // Validar que todas las posiciones del trayecto son válidas
    for (int i = 0; i < cantidad; i++) {
        int f = fila + i * df;
        int c = col + i * dc;
        if (f < 0 || f > 6 || c < 0 || c > 12) return false;

        char casilla = tablero[f][c];
        if (!(casilla == '*' || casilla == ' ' || casilla == '-' || casilla == '/' || casilla == '\\')) {
            return false;
        }
    }

    if (requiereContacto && cantidadJugadas > 0 && !hayContacto(fila, col, df, dc, cantidad)) {
        System.out.println("La banda no toca otra.");
        return false;
    }

    colocarBanda(fila, col, direccion, cantidad);
    buscarTriangulos(turnoBlanco);
    return true;
}




public static void colocarBanda(int filaInicio, int colInicio, char direccion, int cantidad) {
    int fila = filaInicio;
    int col = colInicio;

    for (int i = 0; i < cantidad - 1; i++) {
        int siguienteFila = fila;
        int siguienteCol = col;

        switch (direccion) {
            case 'D': siguienteCol += 2; break;
            case 'A': siguienteCol -= 2; break;
            case 'C': siguienteFila += 1; siguienteCol += 1; break;
            case 'Z': siguienteFila += 1; siguienteCol -= 1; break;
            case 'E': siguienteFila -= 1; siguienteCol += 1; break;
            case 'Q': siguienteFila -= 1; siguienteCol -= 1; break;
        }

        int interFila = (fila + siguienteFila) / 2;
        int interCol = (col + siguienteCol) / 2;

        // Solo colocar si el espacio intermedio está vacío
        if (interFila >= 0 && interFila < 7 && interCol >= 0 && interCol < 13 && tablero[interFila][interCol] == ' ') {
            switch (direccion) {
                case 'D':
                case 'A':
                    tablero[interFila][interCol] = '-';
                    break;
                case 'C':
                case 'Q':
                    tablero[interFila][interCol] = '\\';
                    break;
                case 'Z':
                case 'E':
                    tablero[interFila][interCol] = '/';
                    break;
            }
        }

        fila = siguienteFila;
        col = siguienteCol;
    }
}




    static boolean hayContacto(int fila, int col, int df, int dc, int cantidad) {
        int[][] dir = {{-1,0},{1,0},{0,-2},{0,2},{-1,-1},{1,1},{-1,1},{1,-1}};
        for (int i = 0; i < cantidad; i++) {
            int f = fila + i * df;
            int c = col + i * dc;
            for (int[] d : dir) {
                int nf = f + d[0];
                int nc = c + d[1];
                if (nf >= 0 && nf <= 6 && nc >= 0 && nc <= 12) {
                    if (tablero[nf][nc] == 'B' || tablero[nf][nc] == 'N') {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static void mostrarHistorial() {
        System.out.println("\nHistorial de jugadas:");
        for (int i = 0; i < cantidadJugadas; i++) {
            System.out.println((i+1) + ") " + historialJugadas[i]);
        }
    }

    static void buscarTriangulos(boolean turnoBlanco) {
        // (Podés agregar acá el algoritmo para detectar triángulos capturados si querés)
    }

    static void finalizarPartida() {
        System.out.println("\nFin de la partida!");
        System.out.println("Triángulos Blanco: " + triangulosBlanco);
        System.out.println("Triángulos Negro: " + triangulosNegro);

        if (triangulosBlanco > triangulosNegro) {
            System.out.println("¡Gana Blanco!");
            actualizarRachas(true);
        } else if (triangulosNegro > triangulosBlanco) {
            System.out.println("¡Gana Negro!");
            actualizarRachas(false);
        } else {
            System.out.println("¡Empate!");
            rachaActual[indiceJugadorBlanco] = 0;
            rachaActual[indiceJugadorNegro] = 0;
        }
    }

    static void actualizarRachas(boolean blancoGana) {
        if (blancoGana) {
            partidasGanadas[indiceJugadorBlanco]++;
            rachaActual[indiceJugadorBlanco]++;
            rachaActual[indiceJugadorNegro] = 0;
        } else {
            partidasGanadas[indiceJugadorNegro]++;
            rachaActual[indiceJugadorNegro]++;
            rachaActual[indiceJugadorBlanco] = 0;
        }
    }

    static void mostrarRanking() {
        System.out.println("\nRanking de jugadores:");
        for (int i = 0; i < cantidadJugadores; i++) {
            System.out.println((i+1) + ") " + nombresJugadores[i] + " - Ganadas: " + partidasGanadas[i] + " - Racha: " + rachaActual[i]);
        }
    }
    
    static boolean esParteDelHexagono(int fila, int col) {
    int[][] posiciones = {
        {3, 5, 7, 9},
        {2, 4, 6, 8, 10},
        {1, 3, 5, 7, 9, 11},
        {0, 2, 4, 6, 8, 10, 12},
        {1, 3, 5, 7, 9, 11},
        {2, 4, 6, 8, 10},
        {3, 5, 7, 9}
    };

    for (int i = 0; i < posiciones[fila].length; i++) {
        if (posiciones[fila][i] == col) return true;
    }
    return false;
}



   static void mostrarTablero() {
    System.out.print("   ");
    for (int col = 0; col < 13; col++) {
        System.out.print((char) ('A' + col) + " ");
    }
    System.out.println();

    for (int fila = 0; fila < 7; fila++) {
        System.out.print((fila + 1) + "  ");
        for (int col = 0; col < 13; col++) {
            if (esParteDelHexagono(fila, col) || tablero[fila][col] != ' ') {
                System.out.print(tablero[fila][col] + " ");
            } else {
                System.out.print("  ");
            }
        }
        System.out.println();
    }
}



}