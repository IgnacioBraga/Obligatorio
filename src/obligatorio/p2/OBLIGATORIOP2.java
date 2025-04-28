
package obligatorio.p2;

import java.util.Scanner;

public class OBLIGATORIOP2 {
    // Trabajo desarrollado por: TU NOMBRE - TU NUMERO DE ESTUDIANTE

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

    static char[][] tablero = new char[7][13];

    static int triangulosBlanco = 0;
    static int triangulosNegro = 0;

    static String[] historialJugadas = new String[100];
    static int cantidadJugadas = 0;

    static int indiceJugadorBlanco = -1;
    static int indiceJugadorNegro = -1;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Trabajo desarrollado por: TU NOMBRE - TU NUMERO");

    boolean salir = false;
    while (!salir) {
        System.out.println("\nMENU PRINCIPAL");
        System.out.println("a) Registrar un jugador");
        System.out.println("b) Configurar la partida");
        System.out.println("c) Comienzo de partida");
        System.out.println("d) Mostrar ranking y racha");
        System.out.println("e) Terminar el programa");
        System.out.print("Ingrese opción: ");
        String opcion = scanner.nextLine();

        if (opcion.equalsIgnoreCase("a")) {
            registrarJugador(scanner);
        } else if (opcion.equalsIgnoreCase("b")) {
            if (cantidadJugadores >= 2) {
                configurarPartida(scanner);
                partidaConfigurada = true;
            } else {
                System.out.println("Debe registrar al menos 2 jugadores antes de configurar la partida.");
            }
        } else if (opcion.equalsIgnoreCase("c")) {
            if (partidaConfigurada) {
                comenzarPartida(scanner);
            } else {
                System.out.println("Debe configurar la partida antes de comenzarla.");
            }
        } else if (opcion.equalsIgnoreCase("d")) {
            mostrarRanking();
        } else if (opcion.equalsIgnoreCase("e")) {
            System.out.println("Programa finalizado. Gracias por jugar!");
            salir = true;
        } else {
            System.out.println("Opción incorrecta. Ingrese nuevamente.");
        }
    }
}




  public static void registrarJugador(Scanner scanner) {
    System.out.print("Ingrese nombre del jugador: ");
    String nombre = scanner.nextLine();

    boolean nombreExiste = false;
    int i = 0;
    while (i < cantidadJugadores) {
        if (nombresJugadores[i].equalsIgnoreCase(nombre)) {
            nombreExiste = true;
        }
        i++;
    }

    if (nombreExiste) {
        System.out.println("El nombre ya existe. No se puede registrar.");
    } else {
        boolean edadValida = false;
        int edad = 0;

        while (!edadValida) {
            System.out.print("Ingrese edad del jugador: ");
            String entrada = scanner.nextLine(); // Siempre leer como texto

            try {
                edad = Integer.parseInt(entrada); // Intentamos convertir a número
                if (edad >= 1) {
                    edadValida = true; // Edad válida
                } else {
                    System.out.println("Edad inválida. Debe ingresar un número mayor o igual a 1.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Edad inválida. Debe ingresar un número entero.");
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



    public static void configurarPartida(Scanner scanner) {
        System.out.println("Configurando partida...");
        System.out.print("¿Las bandas deben tocar otra anterior? (S/N): ");
        String respuesta1 = scanner.nextLine();
        requiereContacto = respuesta1.equalsIgnoreCase("S");

        System.out.print("¿La longitud de las bandas es variable? (S/N): ");
        String respuesta2 = scanner.nextLine();
        longitudVariable = respuesta2.equalsIgnoreCase("S");

        System.out.print("Ingrese cantidad de bandas para terminar la partida: ");
        int bandas = scanner.nextInt();
        scanner.nextLine();
        if (bandas > 0) {
            cantidadBandas = bandas;
        }

        System.out.print("Ingrese cantidad de tableros a mostrar (1-4): ");
        int tableros = scanner.nextInt();
        scanner.nextLine();
        if (tableros >= 1 && tableros <= 4) {
            cantidadTableros = tableros;
        }
    }

    public static void comenzarPartida(Scanner scanner) {
        if (cantidadJugadores < 2) {
            System.out.println("No hay suficientes jugadores registrados.");
            return;
        }

        ordenarJugadores();

        System.out.println("\nLista de jugadores:");
        int i = 0;
        while (i < cantidadJugadores) {
            System.out.println((i + 1) + ") " + nombresJugadores[i] + " (" + edadesJugadores[i] + " años)");
            i++;
        }

        System.out.print("Seleccione número del primer jugador: ");
        int jugador1 = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Seleccione número del segundo jugador: ");
        int jugador2 = scanner.nextInt();
        scanner.nextLine();

        if (jugador1 == jugador2 || jugador1 < 1 || jugador1 > cantidadJugadores || jugador2 < 1 || jugador2 > cantidadJugadores) {
            System.out.println("Selección inválida.");
        } else {
            indiceJugadorBlanco = jugador1 - 1;
            indiceJugadorNegro = jugador2 - 1;
            inicializarTablero();
            triangulosBlanco = 0;
            triangulosNegro = 0;
            cantidadJugadas = 0;
            System.out.println("\nComienza partida entre: " + nombresJugadores[indiceJugadorBlanco] + " (Blanco) y " + nombresJugadores[indiceJugadorNegro] + " (Negro)");
            jugar(scanner);
        }
    }

    public static void ordenarJugadores() {
        int i = 0;
        while (i < cantidadJugadores - 1) {
            int j = 0;
            while (j < cantidadJugadores - 1 - i) {
                if (nombresJugadores[j].compareToIgnoreCase(nombresJugadores[j + 1]) > 0) {
                    String tempNombre = nombresJugadores[j];
                    nombresJugadores[j] = nombresJugadores[j + 1];
                    nombresJugadores[j + 1] = tempNombre;
                    int tempEdad = edadesJugadores[j];
                    edadesJugadores[j] = edadesJugadores[j + 1];
                    edadesJugadores[j + 1] = tempEdad;
                }
                j++;
            }
            i++;
        }
    }

    public static void inicializarTablero() {
        int fila = 0;
        while (fila < 7) {
            int col = 0;
            while (col < 13) {
                tablero[fila][col] = '*';
                col++;
            }
            fila++;
        }
    }

    public static boolean esParteDelHexagono(int fila, int col) {
        int[][] posiciones = {
            {3,5,7,9},
            {2,4,6,8,10},
            {1,3,5,7,9,11},
            {0,2,4,6,8,10,12},
            {1,3,5,7,9,11},
            {2,4,6,8,10},
            {3,5,7,9}
        };
        int i = 0;
        while (i < posiciones[fila].length) {
            if (posiciones[fila][i] == col) {
                return true;
            }
            i++;
        }
        return false;
    }

    public static void mostrarTablero() {
        System.out.print("   ");
        int col = 0;
        while (col < 13) {
            System.out.print((char) ('A' + col) + " ");
            col++;
        }
        System.out.println();

        int fila = 0;
        while (fila < 7) {
            System.out.print((fila + 1) + "  ");
            col = 0;
            while (col < 13) {
                if (esParteDelHexagono(fila, col)) {
                    System.out.print(tablero[fila][col] + " ");
                } else {
                    System.out.print("  ");
                }
                col++;
            }
            System.out.println();
            fila++;
        }
    }

    public static void jugar(Scanner scanner) {
    int bandasColocadas = 0;
    boolean turnoBlanco = true;

    while (bandasColocadas < cantidadBandas) {
        mostrarTablero();

        if (turnoBlanco) {
            System.out.println("Turno del jugador Blanco");
        } else {
            System.out.println("Turno del jugador Negro");
        }

        System.out.print("Ingrese jugada (ej: D1D4) o H para historial: ");
        String jugada = scanner.nextLine();

        if (jugada.equalsIgnoreCase("X")) {
            System.out.println("Partida terminada anticipadamente.");
            return;
        }

        if (jugada.equalsIgnoreCase("H")) {
            mostrarHistorial();
        } else {
            boolean movimientoCorrecto = procesarJugada(jugada, turnoBlanco);
            if (movimientoCorrecto) {
                bandasColocadas++;
                turnoBlanco = !turnoBlanco;
            }
        }
    }

    finalizarPartida();
}

public static boolean procesarJugada(String jugada, boolean turnoBlanco) {
    boolean esValido = true;

    if (jugada.length() < 4) {
        System.out.println("Formato de jugada inválido. Debe ser LetraFilaDirecciónCantidad.");
        esValido = false;
    } else {
        char letraColumna = jugada.charAt(0);
        int columnaInicio = letraColumna - 'A';
        int filaInicio = Character.getNumericValue(jugada.charAt(1)) - 1;
        char direccion = jugada.charAt(2);
        int longitud = Character.getNumericValue(jugada.charAt(3));

        if (filaInicio < 0 || filaInicio >= 7 || columnaInicio < 0 || columnaInicio >= 13 || !esParteDelHexagono(filaInicio, columnaInicio)) {
            System.out.println("La posición inicial está fuera del hexágono o del tablero.");
            esValido = false;
        } else {
            if (longitud < 1 || longitud > 4) {
                System.out.println("La longitud debe ser entre 1 y 4.");
                esValido = false;
            } else {
                int fila = filaInicio;
                int columna = columnaInicio;
                int pasos = 0;

                while (pasos < longitud && esValido) {
                    if (fila < 0 || fila >= 7 || columna < 0 || columna >= 13) {
                        esValido = false;
                    } else {
                        pasos++;
                        if (direccion == 'D') columna++;
                        else if (direccion == 'A') columna--;
                        else if (direccion == 'C') { fila++; columna++; }
                        else if (direccion == 'Z') { fila++; columna--; }
                        else if (direccion == 'E') { fila--; columna++; }
                        else if (direccion == 'Q') { fila--; columna--; }
                        else esValido = false;
                    }
                }

                if (esValido) {
                    colocarBanda(filaInicio, columnaInicio, direccion, longitud);
                    historialJugadas[cantidadJugadas] = jugada;
                    cantidadJugadas++;
                    buscarTriangulos(turnoBlanco);
                } else {
                    System.out.println("Movimiento fuera de los límites del tablero.");
                }
            }
        }
    }
    return esValido;
}



 public static void colocarBanda(int filaInicio, int columnaInicio, char direccion, int cantidad) {
    int fila = filaInicio;
    int columna = columnaInicio;
    int pasos = 0;

    while (pasos < cantidad) {
        // Verificar límites antes de colocar
        if (fila >= 0 && fila < 7 && columna >= 0 && columna < 13) {
            if (direccion == 'D' || direccion == 'A') {
                tablero[fila][columna] = '-';
            } else if (direccion == 'C' || direccion == 'Q') {
                tablero[fila][columna] = '\\';
            } else if (direccion == 'Z' || direccion == 'E') {
                tablero[fila][columna] = '/';
            }
        } else {
            System.out.println("Intentaste colocar fuera del tablero. Movimiento interrumpido.");
            break;
        }

        // Mover al siguiente casillero
        if (direccion == 'D') columna++;
        else if (direccion == 'A') columna--;
        else if (direccion == 'C') { fila++; columna++; }
        else if (direccion == 'Z') { fila++; columna--; }
        else if (direccion == 'E') { fila--; columna++; }
        else if (direccion == 'Q') { fila--; columna--; }

        pasos++;
    }
}




    public static void buscarTriangulos(boolean turnoBlanco) {
        int fila = 0;
        while (fila < 6) {
            int col = 0;
            while (col < 12) {
                if (tablero[fila][col] == '*' && tablero[fila][col+2] == '*' && tablero[fila+1][col+1] == '*') {
                    if (tablero[fila][col+1] == '-' && tablero[fila+1][col] == '/' && tablero[fila+1][col+2] == '\\') {
                        if (turnoBlanco) {
                            tablero[fila+1][col+1] = '□';
                            triangulosBlanco++;
                        } else {
                            tablero[fila+1][col+1] = '■';
                            triangulosNegro++;
                        }
                    }
                }
                col++;
            }
            fila++;
        }
    }

    public static void mostrarHistorial() {
        System.out.println("\nHistorial de jugadas:");
        int i = 0;
        while (i < cantidadJugadas) {
            System.out.println((i+1) + ") " + historialJugadas[i]);
            i++;
        }
    }

    public static void finalizarPartida() {
        System.out.println("Partida terminada. Se colocaron todas las bandas.");
        System.out.println("Triángulos jugador Blanco: " + triangulosBlanco);
        System.out.println("Triángulos jugador Negro: " + triangulosNegro);

        if (triangulosBlanco > triangulosNegro) {
            System.out.println("Ganó el jugador Blanco!");
            partidasGanadas[indiceJugadorBlanco]++;
            rachaActual[indiceJugadorBlanco]++;
            rachaActual[indiceJugadorNegro] = 0;
        } else if (triangulosNegro > triangulosBlanco) {
            System.out.println("Ganó el jugador Negro!");
            partidasGanadas[indiceJugadorNegro]++;
            rachaActual[indiceJugadorNegro]++;
            rachaActual[indiceJugadorBlanco] = 0;
        } else {
            System.out.println("Empate!");
            rachaActual[indiceJugadorBlanco] = 0;
            rachaActual[indiceJugadorNegro] = 0;
        }
    }

    public static void mostrarRanking() {
        System.out.println("\nRanking de jugadores:");
        int[] indices = new int[cantidadJugadores];
        int i = 0;
        while (i < cantidadJugadores) {
            indices[i] = i;
            i++;
        }

        i = 0;
        while (i < cantidadJugadores - 1) {
            int j = 0;
            while (j < cantidadJugadores - 1 - i) {
                if (partidasGanadas[indices[j]] < partidasGanadas[indices[j+1]]) {
                    int temp = indices[j];
                    indices[j] = indices[j+1];
                    indices[j+1] = temp;
                }
                j++;
            }
            i++;
        }

        i = 0;
        while (i < cantidadJugadores) {
            int idx = indices[i];
            System.out.println((i+1) + ") " + nombresJugadores[idx] + " - Partidas ganadas: " + partidasGanadas[idx]);
            i++;
        }

        int maxRacha = 0;
        String jugadorMaxRacha = "";
        i = 0;
        while (i < cantidadJugadores) {
            if (rachaActual[i] > maxRacha) {
                maxRacha = rachaActual[i];
                jugadorMaxRacha = nombresJugadores[i];
            }
            i++;
        }

        if (maxRacha > 0) {
            System.out.println("\nJugador con mayor racha ganadora: " + jugadorMaxRacha + " con " + maxRacha + " partidas consecutivas.");
        } else {
            System.out.println("\nNo hay rachas ganadoras todavía.");
        }
    }
}


