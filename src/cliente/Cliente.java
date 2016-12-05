package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author netosolis
 */
public class Cliente implements Runnable {

    //Declaramos las variables necesarias para la conexion y comunicacion
    private Socket cliente;
    private DataInputStream in;
    private DataOutputStream out;
    //El puerto debe ser el mismo en el que escucha el servidor
    private int puerto = 2027;
    //Si estamos en nuestra misma maquina usamos localhost si no la ip de la maquina servidor
    private String host = "localhost";
    private String mensajes = "";
    private String nameCliente = "";
    public static String tableroCad = "";
    private String turnA = "";
    private static JLabel containerFicha;
    private JButton[][] fix;
    private FrameCliente oj;
    private static JLabel Aviso;
    private Ficha[][] fich;
    private final String[] f;
    private String[] user;
    private static JLabel eName;
    private static JLabel eJuega;
    private static JLabel name;
    private static JLabel juega;
    private static JTextField dirIP ;

    //Constructor recibe como parametro el panel donde se mostraran los mensajes
    public Cliente(JLabel containerFicha, FrameCliente oj, JButton[][] fix, JLabel Aviso, Ficha[][] fich, String[] f, JLabel eName, JLabel eJuega, JLabel name, JLabel juega, JTextField dirIP) {
        this.dirIP = dirIP;
        this.eName = eName;
        this.eJuega = eJuega;
        this.name = name;
        this.juega = juega;
        this.f = f;
        this.fich = fich;
        this.Aviso = Aviso;
        this.fix = fix;
        this.oj = oj;
        this.containerFicha = containerFicha;
        this.host = dirIP.getText();
        try {
            cliente = new Socket(host, puerto);
            in = new DataInputStream(cliente.getInputStream());
            out = new DataOutputStream(cliente.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static int val = 0, play = 0;

    @Override
    public void run() {
        try {
            //Ciclo infinito que escucha por mensajes del servidor y los muestra en el panel
            while (true) {
                if ("".equals(nameCliente)) {
                    mensajes += in.readUTF();
                    FrameCliente.setMsjBienvenida(mensajes);
                    nameCliente = in.readUTF();
                    System.out.println(nameCliente);
                    eName.setText(nameCliente);
                    user = nameCliente.split(" ");
                    FrameCliente.setTurno(Integer.parseInt(user[1]) + 1);
                } else {
                    tableroCad = in.readUTF();
                    if ("JUEGA".equals(tableroCad)) {
                        Aviso.setVisible(false);
                        containerFicha.setVisible(true);
                    } else {
                        Tablero.reconstruirTable(tableroCad);
                        FrameCliente.table = Tablero.tablero;
                        for (int i = 0; i < Tablero.FIL; i++) {
                            for (int j = 0; j < Tablero.COL; j++) {
                                fich[i][j].setNewImgFich(f[FrameCliente.table[i][j]], fix[i][j]);
                            }
                        }
                    }
                }

                turnA = in.readUTF();
                FrameCliente.setTurnoActual(Integer.parseInt(turnA));
                if (Integer.parseInt(turnA) == FrameCliente.getTurno()) {
                    eJuega.setText("Su Turno...");
                } else {
                    eJuega.setText("Turno Contrario...");
                }
                int cont = 0;
                if (val == 0) {
                    for (String retval : nameCliente.split(" ")) {
                        cont++;
                        if (cont == 2) {
                            int userNot = Integer.parseInt(retval);
                            if (userNot > 2 && val == 0) {
                                mensajes += "<h2>Servidor Ocupado Espere su turno<br> Mientras tanto disfrute el Juego</h2>";
                                mensajes += "<h3>Juega " + turnA + "</h3>";
                                FrameCliente.setMsjBienvenida(mensajes);
                                val = 1;
                            }
                            if (val == 0) {
                                FrameCliente.setTurno(userNot + 1);
                            }
                        }
                    }
                }

                if ("Cliente 2".equals(nameCliente) && play == 0) {
                    play = 1;
                    Thread.sleep(500);
                    containerFicha.setVisible(true);
                    Aviso.setVisible(false);
                    enviarMsg("JUEGA");
                }
            }
        } catch (IOException | NumberFormatException | InterruptedException e) {
        }
    }

    //Funcion sirve para enviar mensajes al servidor
    public void enviarMsg(String msg) throws InterruptedException {
        try {
            if ("2".equals(turnA)) {
                turnA = "3";
            } else {
                turnA = "2";
            }
            out.writeUTF(turnA);
            out.writeUTF(msg);
            out.writeUTF(nameCliente);
            Thread.sleep(2000);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
