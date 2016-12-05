package cliente;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.*;

public class FrameCliente extends javax.swing.JFrame {

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new FrameCliente().setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FrameCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    Cliente cliente;
    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    public static int[][] table;
    private javax.swing.JEditorPane peMsg;
    private Ficha[][] fich;
    private JButton[][] fix;
    private static JLabel containerFicha;
    private static JLabel Aviso = new JLabel("Espere al Jugador 2");
    private static String cadTablero;
    private static String msjBienvenida;
    private final String[] f = new String[]{"f0.png", "f1.png", "f2.png", "f3.png", "f4.png", "f5.png"};
    private final int POS_INIT = 70;
    private int vacio;
    private static int turnoActual;
    private static int turno;
    private static int rival;
    private int dup;
    private int salt;
    private int nextPlay;
    private int posAI;
    private int posAJ;
    private static int cantUser;
    private static int asigTurno = 0;
    private static JLabel name;
    private static JLabel juega;
    private static JLabel eName;
    private static JLabel eJuega;
    private static JTextField dirIP = new JTextField("Ingrese Direccion IP...");
    private static JButton okIP = new JButton("OK");

    private static ImageIcon imgFondo;
    private static URL FONDO;
    // End of variables declaration    

    public FrameCliente() throws InterruptedException {
        //Instanciamos la variable cliente pasandole el panel y activamos el hilo
        initComponents();
        
        okIP.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dirIP.setEditable(false);
                cliente = new Cliente(containerFicha, FrameCliente.this, fix, Aviso, fich, f, eName, eJuega, name, juega, dirIP);
                Thread hilo = new Thread(cliente);
                hilo.start();
                setName("Usuario " + turno);
            }
        });
    }

    private void initComponents() throws InterruptedException {
        name = new JLabel("Jugador: ");
        juega = new JLabel("Turno: ");
        eName = new JLabel();
        eJuega = new JLabel();
        super.setBounds(50, 50, 800, 700);
        name.setBounds(10, 10, 150, 30);
        juega.setBounds(170, 10, 150, 30);
        eName.setBounds(70, 10, 150, 30);
        dirIP.setBounds(360, 10, 150, 30);
        okIP.setBounds(530, 10, 80, 30);
        eJuega.setBounds(220, 10, 500, 30);
        name.setForeground(Color.white);
        juega.setForeground(Color.white);
        eName.setForeground(Color.white);
        eJuega.setForeground(Color.white);
        table = Tablero.tablero;
        fich = new Ficha[Tablero.FIL][];
        fix = new JButton[Tablero.FIL][];
        for (int i = 0; i < Tablero.FIL; i++) {
            fich[i] = new Ficha[Tablero.COL];
            fix[i] = new JButton[Tablero.COL];
        }

        dup = 4;
        salt = 5;
        nextPlay = -1;
        posAI = 0;
        posAJ = 0;
        vacio = 1;
        String dirURL = "ImagenesJuego/fondo.jpg";
        FONDO = this.getClass().getResource(dirURL);
        imgFondo = new ImageIcon(FONDO);
        containerFicha = new JLabel(imgFondo);
        containerFicha.setBounds(5, 5, 800, 700);

        initTable(fich, containerFicha, fix);
        Aviso.setBounds(100, 125, 200, 30);
        for (int i = 0; i < Tablero.FIL; i++) {
            for (int j = 0; j < Tablero.COL; j++) {
                fix[i][j].addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        try {
                            ImagenActionPerformed(evt);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FrameCliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }
        containerFicha.setVisible(false);
        Aviso.setVisible(true);

        super.add(okIP);
        super.add(dirIP);
        super.add(juega);
        super.add(name);
        super.add(eJuega);
        super.add(eName);
        super.add(Aviso);
        super.add(containerFicha);
        Thread.sleep(100);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });

    }

    public void initTable(Ficha[][] fich, JLabel containerFicha, JButton[][] fix) {
        int posX = POS_INIT;
        int posY = POS_INIT;
        Ficha objFich = new Ficha();
        for (int i = 0; i < Tablero.FIL; i++) {
            posX = POS_INIT;
            for (int j = 0; j < Tablero.COL; j++) {
                switch (table[i][j]) {
                    case 0:
                        fich[i][j] = new Ficha();
                        fix[i][j] = fich[i][j].setFicha(posX, posY, f[0], containerFicha, fix[i][j]);
                        fix[i][j].setVisible(true);
                        break;
                    case 1:
                        fich[i][j] = new Ficha();
                        fix[i][j] = fich[i][j].setFicha(posX, posY, f[1], containerFicha, fix[i][j]);
                        fix[i][j].setVisible(true);
                        break;
                    case 2:
                        fich[i][j] = new Ficha();
                        fix[i][j] = fich[i][j].setFicha(posX, posY, f[2], containerFicha, fix[i][j]);
                        fix[i][j].setVisible(true);
                        break;
                    case 3:
                        fich[i][j] = new Ficha();
                        fix[i][j] = fich[i][j].setFicha(posX, posY, f[3], containerFicha, fix[i][j]);
                        fix[i][j].setVisible(true);
                        break;
                    case 4:
                        fich[i][j] = new Ficha();
                        fix[i][j] = fich[i][j].setFicha(posX, posY, f[4], containerFicha, fix[i][j]);
                        fix[i][j].setVisible(true);
                        break;
                    case 5:
                        fich[i][j] = new Ficha();
                        fix[i][j] = fich[i][j].setFicha(posX, posY, f[5], containerFicha, fix[i][j]);
                        fix[i][j].setVisible(true);
                        break;

                }
                posX += objFich.TAM_H;
                System.out.print("(" + i + "," + j + ") " + table[i][j] + " ");
            }
            posY += objFich.TAM_V;
            System.out.println("");
        }
    }

    private void ImagenActionPerformed(java.awt.event.ActionEvent e) throws InterruptedException {
        if (asigTurno == 0) {
            if (turno == 2) {
                rival = 3;
                asigTurno = 1;
            }
            if (turno == 3) {
                rival = 2;
                asigTurno = 1;
            }
        }
        Boolean valFront = false;//turnoActual = 2;
        if (turno == turnoActual) {
            for (int i = 0; i < Tablero.FIL; i++) {
                for (int j = 0; j < Tablero.COL; j++) {
                    if (table[i][j] != 0) {
                        if (e.getSource() == fix[i][j] && table[i][j] == turno && nextPlay == -1) {
                            if (i == 0 && !valFront) {
                                valFront = true;
                                //duplicar Abajo
                                posAI = i;
                                posAJ = j;
                                if (table[i][j - 1] != 0 && table[i][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 1].setNewImgFich(f[dup], fix[i][j - 1]);
                                    table[i][j - 1] = dup;
                                }
                                if (table[i][j + 1] != 0 && table[i][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 1].setNewImgFich(f[dup], fix[i][j + 1]);
                                    table[i][j + 1] = dup;
                                }
                                if (table[i + 1][j] != 0 && table[i + 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j].setNewImgFich(f[dup], fix[i + 1][j]);
                                    table[i + 1][j] = dup;
                                }
                                if (table[i + 1][j - 1] != 0 && table[i + 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j - 1].setNewImgFich(f[dup], fix[i + 1][j - 1]);
                                    table[i + 1][j - 1] = dup;
                                }
                                if (table[i + 1][j + 1] != 0 && table[i + 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j + 1].setNewImgFich(f[dup], fix[i + 1][j + 1]);
                                    table[i + 1][j + 1] = dup;
                                }
                                //saltar
                                if (table[i][j - 2] != 0 && table[i][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 2].setNewImgFich(f[salt], fix[i][j - 2]);
                                    table[i][j - 2] = salt;
                                }
                                if (table[i + 1][j - 2] != 0 && table[i + 1][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j - 2].setNewImgFich(f[salt], fix[i + 1][j - 2]);
                                    table[i + 1][j - 2] = salt;
                                }
                                if (table[i + 2][j - 2] != 0 && table[i + 2][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j - 2].setNewImgFich(f[salt], fix[i + 2][j - 2]);
                                    table[i + 2][j - 2] = salt;
                                }
                                if (table[i + 2][j - 1] != 0 && table[i + 2][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j - 1].setNewImgFich(f[salt], fix[i + 2][j - 1]);
                                    table[i + 2][j - 1] = salt;
                                }
                                if (table[i + 2][j] != 0 && table[i + 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j].setNewImgFich(f[salt], fix[i + 2][j]);
                                    table[i + 2][j] = salt;
                                }
                                if (table[i + 2][j + 1] != 0 && table[i + 2][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j + 1].setNewImgFich(f[salt], fix[i + 2][j + 1]);
                                    table[i + 2][j + 1] = salt;
                                }
                                if (table[i + 2][j + 2] != 0 && table[i + 2][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j + 2].setNewImgFich(f[salt], fix[i + 2][j + 2]);
                                    table[i + 2][j + 2] = salt;
                                }
                                if (table[i + 1][j + 2] != 0 && table[i + 1][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j + 2].setNewImgFich(f[salt], fix[i + 1][j + 2]);
                                    table[i + 1][j + 2] = salt;
                                }
                                if (table[i][j + 2] != 0 && table[i][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 2].setNewImgFich(f[salt], fix[i][j + 2]);
                                    table[i][j + 2] = salt;
                                }
                            }
                            if (i == (Tablero.FIL - 1) && !valFront) {
                                //duplicar
                                posAI = i;
                                posAJ = j;
                                valFront = true;
                                if (table[i][j - 1] != 0 && table[i][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 1].setNewImgFich(f[dup], fix[i][j - 1]);
                                    table[i][j - 1] = dup;
                                }
                                if (table[i][j + 1] != 0 && table[i][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 1].setNewImgFich(f[dup], fix[i][j + 1]);
                                    table[i][j + 1] = dup;
                                }
                                if (table[i - 1][j - 1] != 0 && table[i - 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j - 1].setNewImgFich(f[dup], fix[i - 1][j - 1]);
                                    table[i - 1][j - 1] = dup;
                                }
                                if (table[i - 1][j] != 0 && table[i - 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j].setNewImgFich(f[dup], fix[i - 1][j]);
                                    table[i - 1][j] = dup;
                                }
                                if (table[i - 1][j + 1] != 0 && table[i - 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j + 1].setNewImgFich(f[dup], fix[i - 1][j + 1]);
                                    table[i - 1][j + 1] = dup;
                                }
                                //saltar
                                if (table[i][j - 2] != 0 && table[i][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 2].setNewImgFich(f[salt], fix[i][j - 2]);
                                    table[i][j - 2] = salt;
                                }
                                if (table[i - 1][j - 2] != 0 && table[i - 1][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j - 2].setNewImgFich(f[salt], fix[i - 1][j - 2]);
                                    table[i - 1][j - 2] = salt;
                                }
                                if (table[i - 2][j - 2] != 0 && table[i - 2][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j - 2].setNewImgFich(f[salt], fix[i - 2][j - 2]);
                                    table[i - 2][j - 2] = salt;
                                }
                                if (table[i - 2][j - 1] != 0 && table[i - 2][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j - 1].setNewImgFich(f[salt], fix[i - 2][j - 1]);
                                    table[i - 2][j - 1] = salt;
                                }
                                if (table[i - 2][j] != 0 && table[i - 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j].setNewImgFich(f[salt], fix[i - 2][j]);
                                    table[i - 2][j] = salt;
                                }
                                if (table[i - 2][j + 1] != 0 && table[i - 2][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j + 1].setNewImgFich(f[salt], fix[i - 2][j + 1]);
                                    table[i - 2][j + 1] = salt;
                                }
                                if (table[i - 2][j + 2] != 0 && table[i - 2][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j + 2].setNewImgFich(f[salt], fix[i - 2][j + 2]);
                                    table[i - 2][j + 2] = salt;
                                }
                                if (table[i - 1][j + 2] != 0 && table[i - 1][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j + 2].setNewImgFich(f[salt], fix[i - 1][j + 2]);
                                    table[i - 1][j + 2] = salt;
                                }
                                if (table[i][j + 2] != 0 && table[i][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 2].setNewImgFich(f[salt], fix[i][j + 2]);
                                    table[i][j + 2] = salt;
                                }
                            }
                            if (j == 0 && !valFront) {
                                valFront = true;
                                //duplicar derecha
                                posAI = i;
                                posAJ = j;
                                if (table[i - 1][j] != 0 && table[i - 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j].setNewImgFich(f[dup], fix[i - 1][j]);
                                    table[i - 1][j] = dup;
                                }
                                if (table[i - 1][j + 1] != 0 && table[i - 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j + 1].setNewImgFich(f[dup], fix[i - 1][j + 1]);
                                    table[i - 1][j + 1] = dup;
                                }
                                if (table[i][j + 1] != 0 && table[i][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 1].setNewImgFich(f[dup], fix[i][j + 1]);
                                    table[i][j + 1] = dup;
                                }
                                if (table[i + 1][j + 1] != 0 && table[i + 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j + 1].setNewImgFich(f[dup], fix[i + 1][j + 1]);
                                    table[i + 1][j + 1] = dup;
                                }
                                if (table[i + 1][j] != 0 && table[i + 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j].setNewImgFich(f[dup], fix[i + 1][j]);
                                    table[i + 1][j] = dup;
                                }
                                //saltar
                                if (table[i - 2][j] != 0 && table[i - 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j].setNewImgFich(f[salt], fix[i - 2][j]);
                                    table[i - 2][j] = salt;
                                }
                                if (table[i - 2][j + 1] != 0 && table[i - 2][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j + 1].setNewImgFich(f[salt], fix[i - 2][j + 1]);
                                    table[i - 2][j + 1] = salt;
                                }
                                if (table[i - 2][j + 2] != 0 && table[i - 2][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j + 2].setNewImgFich(f[salt], fix[i - 2][j + 2]);
                                    table[i - 2][j + 2] = salt;
                                }
                                if (table[i - 1][j + 2] != 0 && table[i - 1][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j + 2].setNewImgFich(f[salt], fix[i - 1][j + 2]);
                                    table[i - 1][j + 2] = salt;
                                }
                                if (table[i + 2][j] != 0 && table[i + 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j].setNewImgFich(f[salt], fix[i + 2][j]);
                                    table[i + 2][j] = salt;
                                }
                                if (table[i + 2][j + 1] != 0 && table[i + 2][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j + 1].setNewImgFich(f[salt], fix[i + 2][j + 1]);
                                    table[i + 2][j + 1] = salt;
                                }
                                if (table[i + 2][j + 2] != 0 && table[i + 2][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j + 2].setNewImgFich(f[salt], fix[i + 2][j + 2]);
                                    table[i + 2][j + 2] = salt;
                                }
                                if (table[i + 1][j + 2] != 0 && table[i + 1][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j + 2].setNewImgFich(f[salt], fix[i + 1][j + 2]);
                                    table[i + 1][j + 2] = salt;
                                }
                                if (table[i][j + 2] != 0 && table[i][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 2].setNewImgFich(f[salt], fix[i][j + 2]);
                                    table[i][j + 2] = salt;
                                }
                            }
                            if (j == (Tablero.COL - 1) && !valFront) {
                                valFront = true;
                                //duplicar Izquierda
                                posAI = i;
                                posAJ = j;
                                if (table[i - 1][j] != 0 && table[i - 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j].setNewImgFich(f[dup], fix[i - 1][j]);
                                    table[i - 1][j] = dup;
                                }
                                if (table[i - 1][j - 1] != 0 && table[i - 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j - 1].setNewImgFich(f[dup], fix[i - 1][j - 1]);
                                    table[i - 1][j - 1] = dup;
                                }
                                if (table[i][j - 1] != 0 && table[i][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 1].setNewImgFich(f[dup], fix[i][j - 1]);
                                    table[i][j - 1] = dup;
                                }
                                if (table[i + 1][j - 1] != 0 && table[i + 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j - 1].setNewImgFich(f[dup], fix[i + 1][j - 1]);
                                    table[i + 1][j - 1] = dup;
                                }
                                if (table[i + 1][j] != 0 && table[i + 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j].setNewImgFich(f[dup], fix[i + 1][j]);
                                    table[i + 1][j] = dup;
                                }
                                //saltar
                                if (table[i][j - 2] != 0 && table[i][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 2].setNewImgFich(f[salt], fix[i][j - 2]);
                                    table[i][j - 2] = salt;
                                }
                                if (table[i + 1][j - 2] != 0 && table[i + 1][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j - 2].setNewImgFich(f[salt], fix[i + 1][j - 2]);
                                    table[i + 1][j - 2] = salt;
                                }
                                if (table[i + 2][j - 2] != 0 && table[i + 2][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j - 2].setNewImgFich(f[salt], fix[i + 2][j - 2]);
                                    table[i + 2][j - 2] = salt;
                                }
                                if (table[i + 2][j - 1] != 0 && table[i + 2][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j - 1].setNewImgFich(f[salt], fix[i + 2][j - 1]);
                                    table[i + 2][j - 1] = salt;
                                }
                                if (table[i + 2][j] != 0 && table[i + 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j].setNewImgFich(f[salt], fix[i + 2][j]);
                                    table[i + 2][j] = salt;
                                }
                                if (table[i - 1][j - 2] != 0 && table[i - 1][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j - 2].setNewImgFich(f[salt], fix[i - 1][j - 2]);
                                    table[i - 1][j - 2] = salt;
                                }
                                if (table[i - 2][j - 2] != 0 && table[i - 2][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j - 2].setNewImgFich(f[salt], fix[i - 2][j - 2]);
                                    table[i - 2][j - 2] = salt;
                                }
                                if (table[i - 2][j - 1] != 0 && table[i - 2][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j - 1].setNewImgFich(f[salt], fix[i - 2][j - 1]);
                                    table[i - 2][j - 1] = salt;
                                }
                                if (table[i - 2][j] != 0 && table[i - 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j].setNewImgFich(f[salt], fix[i - 2][j]);
                                    table[i - 2][j] = salt;
                                }
                            }
                            //···························································
                            if (i == 1 && !valFront) {
                                nextPlay = 1;
                                valFront = true;
                                //duplicar abajo
                                posAI = i;
                                posAJ = j;
                                if (table[i][j - 1] != 0 && table[i][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 1].setNewImgFich(f[dup], fix[i][j - 1]);
                                    table[i][j - 1] = dup;
                                }
                                if (table[i][j + 1] != 0 && table[i][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 1].setNewImgFich(f[dup], fix[i][j + 1]);
                                    table[i][j + 1] = dup;
                                }
                                if (table[i + 1][j] != 0 && table[i + 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j].setNewImgFich(f[dup], fix[i + 1][j]);
                                    table[i + 1][j] = dup;
                                }
                                if (table[i + 1][j - 1] != 0 && table[i + 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j - 1].setNewImgFich(f[dup], fix[i + 1][j - 1]);
                                    table[i + 1][j - 1] = dup;
                                }
                                if (table[i + 1][j + 1] != 0 && table[i + 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j + 1].setNewImgFich(f[dup], fix[i + 1][j + 1]);
                                    table[i + 1][j + 1] = dup;
                                }
                                if (table[i - 1][j - 1] != 0 && table[i - 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j - 1].setNewImgFich(f[dup], fix[i - 1][j - 1]);
                                    table[i - 1][j - 1] = dup;
                                }
                                if (table[i - 1][j] != 0 && table[i - 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j].setNewImgFich(f[dup], fix[i - 1][j]);
                                    table[i - 1][j] = dup;
                                }
                                if (table[i - 1][j + 1] != 0 && table[i - 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j + 1].setNewImgFich(f[dup], fix[i - 1][j + 1]);
                                    table[i - 1][j + 1] = dup;
                                }
                                //saltar
                                if (table[i][j - 2] != 0 && table[i][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 2].setNewImgFich(f[salt], fix[i][j - 2]);
                                    table[i][j - 2] = salt;
                                }
                                if (table[i + 1][j - 2] != 0 && table[i + 1][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j - 2].setNewImgFich(f[salt], fix[i + 1][j - 2]);
                                    table[i + 1][j - 2] = salt;
                                }
                                if (table[i + 2][j - 2] != 0 && table[i + 2][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j - 2].setNewImgFich(f[salt], fix[i + 2][j - 2]);
                                    table[i + 2][j - 2] = salt;
                                }
                                if (table[i + 2][j - 1] != 0 && table[i + 2][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j - 1].setNewImgFich(f[salt], fix[i + 2][j - 1]);
                                    table[i + 2][j - 1] = salt;
                                }
                                if (table[i + 2][j] != 0 && table[i + 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j].setNewImgFich(f[salt], fix[i + 2][j]);
                                    table[i + 2][j] = salt;
                                }
                                if (table[i + 2][j + 1] != 0 && table[i + 2][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j + 1].setNewImgFich(f[salt], fix[i + 2][j + 1]);
                                    table[i + 2][j + 1] = salt;
                                }
                                if (table[i + 2][j + 2] != 0 && table[i + 2][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j + 2].setNewImgFich(f[salt], fix[i + 2][j + 2]);
                                    table[i + 2][j + 2] = salt;
                                }
                                if (table[i + 1][j + 2] != 0 && table[i + 1][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j + 2].setNewImgFich(f[salt], fix[i + 1][j + 2]);
                                    table[i + 1][j + 2] = salt;
                                }
                                if (table[i][j + 2] != 0 && table[i][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 2].setNewImgFich(f[salt], fix[i][j + 2]);
                                    table[i][j + 2] = salt;
                                }
                                if (table[i - 1][j - 2] != 0 && table[i - 1][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j - 2].setNewImgFich(f[salt], fix[i - 1][j - 2]);
                                    table[i - 1][j - 2] = salt;
                                }
                                if (table[i - 1][j + 2] != 0 && table[i - 1][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j + 2].setNewImgFich(f[salt], fix[i - 1][j + 2]);
                                    table[i - 1][j + 2] = salt;
                                }
                            }
                            if (i == (Tablero.FIL - 2) && !valFront) {
                                //duplicar
                                posAI = i;
                                posAJ = j;
                                valFront = true;
                                if (table[i][j - 1] != 0 && table[i][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 1].setNewImgFich(f[dup], fix[i][j - 1]);
                                    table[i][j - 1] = dup;
                                }
                                if (table[i][j + 1] != 0 && table[i][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 1].setNewImgFich(f[dup], fix[i][j + 1]);
                                    table[i][j + 1] = dup;
                                }
                                if (table[i - 1][j - 1] != 0 && table[i - 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j - 1].setNewImgFich(f[dup], fix[i - 1][j - 1]);
                                    table[i - 1][j - 1] = dup;
                                }
                                if (table[i - 1][j] != 0 && table[i - 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j].setNewImgFich(f[dup], fix[i - 1][j]);
                                    table[i - 1][j] = dup;
                                }
                                if (table[i - 1][j + 1] != 0 && table[i - 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j + 1].setNewImgFich(f[dup], fix[i - 1][j + 1]);
                                    table[i - 1][j + 1] = dup;
                                }
                                if (table[i + 1][j - 1] != 0 && table[i + 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j - 1].setNewImgFich(f[dup], fix[i + 1][j - 1]);
                                    table[i + 1][j - 1] = dup;
                                }
                                if (table[i + 1][j] != 0 & table[i + 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j].setNewImgFich(f[dup], fix[i + 1][j]);
                                    table[i + 1][j] = dup;
                                }
                                if (table[i + 1][j + 1] != 0 && table[i + 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j + 1].setNewImgFich(f[dup], fix[i + 1][j + 1]);
                                    table[i + 1][j + 1] = dup;
                                }
                                //saltar
                                if (table[i][j - 2] != 0 && table[i][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 2].setNewImgFich(f[salt], fix[i][j - 2]);
                                    table[i][j - 2] = salt;
                                }
                                if (table[i - 1][j - 2] != 0 && table[i - 1][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j - 2].setNewImgFich(f[salt], fix[i - 1][j - 2]);
                                    table[i - 1][j - 2] = salt;
                                }
                                if (table[i - 2][j - 2] != 0 && table[i - 2][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j - 2].setNewImgFich(f[salt], fix[i - 2][j - 2]);
                                    table[i - 2][j - 2] = salt;
                                }
                                if (table[i - 2][j - 1] != 0 && table[i - 2][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j - 1].setNewImgFich(f[salt], fix[i - 2][j - 1]);
                                    table[i - 2][j - 1] = salt;
                                }
                                if (table[i - 2][j] != 0 && table[i - 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j].setNewImgFich(f[salt], fix[i - 2][j]);
                                    table[i - 2][j] = salt;
                                }
                                if (table[i - 2][j + 1] != 0 && table[i - 2][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j + 1].setNewImgFich(f[salt], fix[i - 2][j + 1]);
                                    table[i - 2][j + 1] = salt;
                                }
                                if (table[i - 2][j + 2] != 0 && table[i - 2][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j + 2].setNewImgFich(f[salt], fix[i - 2][j + 2]);
                                    table[i - 2][j + 2] = salt;
                                }
                                if (table[i - 1][j + 2] != 0 && table[i - 1][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j + 2].setNewImgFich(f[salt], fix[i - 1][j + 2]);
                                    table[i - 1][j + 2] = salt;
                                }
                                if (table[i][j + 2] != 0 && table[i][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 2].setNewImgFich(f[salt], fix[i][j + 2]);
                                    table[i][j + 2] = salt;
                                }
                                if (table[i + 1][j + 2] != 0 && table[i + 1][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j + 2].setNewImgFich(f[salt], fix[i + 1][j + 2]);
                                    table[i + 1][j + 2] = salt;
                                }
                                if (table[i + 1][j - 2] != 0 && table[i + 1][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j - 2].setNewImgFich(f[salt], fix[i + 1][j - 2]);
                                    table[i + 1][j - 2] = salt;
                                }
                            }
                            if (j == 1 && !valFront) {
                                valFront = true;
                                //duplicar
                                posAI = i;
                                posAJ = j;
                                if (table[i - 1][j] != 0 && table[i - 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j].setNewImgFich(f[dup], fix[i - 1][j]);
                                    table[i - 1][j] = dup;
                                }
                                if (table[i - 1][j + 1] != 0 && table[i - 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j + 1].setNewImgFich(f[dup], fix[i - 1][j + 1]);
                                    table[i - 1][j + 1] = dup;
                                }
                                if (table[i][j + 1] != 0 && table[i][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 1].setNewImgFich(f[dup], fix[i][j + 1]);
                                    table[i][j + 1] = dup;
                                }
                                if (table[i + 1][j + 1] != 0 && table[i + 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j + 1].setNewImgFich(f[dup], fix[i + 1][j + 1]);
                                    table[i + 1][j + 1] = dup;
                                }
                                if (table[i + 1][j] != 0 && table[i + 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j].setNewImgFich(f[dup], fix[i + 1][j]);
                                    table[i + 1][j] = dup;
                                }
                                if (table[i][j - 1] != 0 && table[i][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 1].setNewImgFich(f[dup], fix[i][j - 1]);
                                    table[i][j - 1] = dup;
                                }
                                if (table[i + 1][j - 1] != 0 && table[i + 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j - 1].setNewImgFich(f[dup], fix[i + 1][j - 1]);
                                    table[i + 1][j - 1] = dup;
                                }
                                if (table[i - 1][j - 1] != 0 && table[i - 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j - 1].setNewImgFich(f[dup], fix[i - 1][j - 1]);
                                    table[i - 1][j - 1] = dup;
                                }
                                //saltar
                                if (table[i - 2][j] != 0 && table[i - 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j].setNewImgFich(f[salt], fix[i - 2][j]);
                                    table[i - 2][j] = salt;
                                }
                                if (table[i - 2][j + 1] != 0 && table[i - 2][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j + 1].setNewImgFich(f[salt], fix[i - 2][j + 1]);
                                    table[i - 2][j + 1] = salt;
                                }
                                if (table[i - 2][j + 2] != 0 && table[i - 2][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j + 2].setNewImgFich(f[salt], fix[i - 2][j + 2]);
                                    table[i - 2][j + 2] = salt;
                                }
                                if (table[i - 1][j + 2] != 0 && table[i - 1][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j + 2].setNewImgFich(f[salt], fix[i - 1][j + 2]);
                                    table[i - 1][j + 2] = salt;
                                }
                                if (table[i + 2][j] != 0 && table[i + 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j].setNewImgFich(f[salt], fix[i + 2][j]);
                                    table[i + 2][j] = salt;
                                }
                                if (table[i + 2][j + 1] != 0 && table[i + 2][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j + 1].setNewImgFich(f[salt], fix[i + 2][j + 1]);
                                    table[i + 2][j + 1] = salt;
                                }
                                if (table[i + 2][j + 2] != 0 && table[i + 2][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j + 2].setNewImgFich(f[salt], fix[i + 2][j + 2]);
                                    table[i + 2][j + 2] = salt;
                                }
                                if (table[i + 1][j + 2] != 0 && table[i + 1][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j + 2].setNewImgFich(f[salt], fix[i + 1][j + 2]);
                                    table[i + 1][j + 2] = salt;
                                }
                                if (table[i][j + 2] != 0 && table[i][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 2].setNewImgFich(f[salt], fix[i][j + 2]);
                                    table[i][j + 2] = salt;
                                }
                                if (table[i - 2][j - 1] != 0 && table[i - 2][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j - 1].setNewImgFich(f[salt], fix[i - 2][j - 1]);
                                    table[i - 2][j - 1] = salt;
                                }
                                if (table[i + 2][j - 1] != 0 && table[i + 2][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j - 1].setNewImgFich(f[salt], fix[i + 2][j - 1]);
                                    table[i + 2][j - 1] = salt;
                                }
                            }
                            if (j == (Tablero.COL - 2) && !valFront) {
                                valFront = true;
                                //duplicar
                                posAI = i;
                                posAJ = j;
                                if (table[i - 1][j - 1] != 0 && table[i - 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j - 1].setNewImgFich(f[dup], fix[i - 1][j - 1]);
                                    table[i - 1][j - 1] = dup;
                                }
                                if (table[i - 1][j] != 0 && table[i - 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j].setNewImgFich(f[dup], fix[i - 1][j]);
                                    table[i - 1][j] = dup;
                                }
                                if (table[i][j - 1] != 0 && table[i][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 1].setNewImgFich(f[dup], fix[i][j - 1]);
                                    table[i][j - 1] = dup;
                                }
                                if (table[i + 1][j - 1] != 0 && table[i + 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j - 1].setNewImgFich(f[dup], fix[i + 1][j - 1]);
                                    table[i + 1][j - 1] = dup;
                                }
                                if (table[i + 1][j] != 0 && table[i + 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j].setNewImgFich(f[dup], fix[i + 1][j]);
                                    table[i + 1][j] = dup;
                                }
                                if (table[i - 1][j + 1] != 0 && table[i - 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j + 1].setNewImgFich(f[dup], fix[i - 1][j + 1]);
                                    table[i - 1][j + 1] = dup;
                                }
                                if (table[i + 1][j + 1] != 0 && table[i + 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j + 1].setNewImgFich(f[dup], fix[i + 1][j + 1]);
                                    table[i + 1][j + 1] = dup;
                                }
                                if (table[i][j + 1] != 0 && table[i][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 1].setNewImgFich(f[dup], fix[i][j + 1]);
                                    table[i][j + 1] = dup;
                                }
                                //saltar
                                if (table[i][j - 2] != 0 && table[i][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 2].setNewImgFich(f[salt], fix[i][j - 2]);
                                    table[i][j - 2] = salt;
                                }
                                if (table[i + 1][j - 2] != 0 && table[i + 1][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j - 2].setNewImgFich(f[salt], fix[i + 1][j - 2]);
                                    table[i + 1][j - 2] = salt;
                                }
                                if (table[i + 2][j - 2] != 0 && table[i + 2][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j - 2].setNewImgFich(f[salt], fix[i + 2][j - 2]);
                                    table[i + 2][j - 2] = salt;
                                }
                                if (table[i + 2][j - 1] != 0 && table[i + 2][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j - 1].setNewImgFich(f[salt], fix[i + 2][j - 1]);
                                    table[i + 2][j - 1] = salt;
                                }
                                if (table[i + 2][j] != 0 && table[i + 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j].setNewImgFich(f[salt], fix[i + 2][j]);
                                    table[i + 2][j] = salt;
                                }
                                if (table[i - 1][j - 2] != 0 && table[i - 1][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j - 2].setNewImgFich(f[salt], fix[i - 1][j - 2]);
                                    table[i - 1][j - 2] = salt;
                                }
                                if (table[i - 2][j - 2] != 0 && table[i - 2][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j - 2].setNewImgFich(f[salt], fix[i - 2][j - 2]);
                                    table[i - 2][j - 2] = salt;
                                }
                                if (table[i - 2][j - 1] != 0 && table[i - 2][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j - 1].setNewImgFich(f[salt], fix[i - 2][j - 1]);
                                    table[i - 2][j - 1] = salt;
                                }
                                if (table[i - 2][j] != 0 && table[i - 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j].setNewImgFich(f[salt], fix[i - 2][j]);
                                    table[i - 2][j] = salt;
                                }
                                if (table[i - 2][j + 1] != 0 && table[i - 2][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j + 1].setNewImgFich(f[salt], fix[i - 2][j + 1]);
                                    table[i - 2][j + 1] = salt;
                                }
                                if (table[i + 2][j + 1] != 0 && table[i + 2][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j + 1].setNewImgFich(f[salt], fix[i + 2][j + 1]);
                                    table[i + 2][j + 1] = salt;
                                }
                            }
                            //···························································
                            if (!valFront) {
                                //duplicar
                                posAI = i;
                                posAJ = j;
                                nextPlay = 1;
                                if (table[i][j - 1] != 0 && table[i][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 1].setNewImgFich(f[dup], fix[i][j - 1]);
                                    table[i][j - 1] = dup;
                                }
                                if (table[i][j + 1] != 0 && table[i][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 1].setNewImgFich(f[dup], fix[i][j + 1]);
                                    table[i][j + 1] = dup;
                                }
                                if (table[i + 1][j] != 0 && table[i + 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j].setNewImgFich(f[dup], fix[i + 1][j]);
                                    table[i + 1][j] = dup;
                                }
                                if (table[i + 1][j - 1] != 0 && table[i + 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j - 1].setNewImgFich(f[dup], fix[i + 1][j - 1]);
                                    table[i + 1][j - 1] = dup;
                                }
                                if (table[i + 1][j + 1] != 0 && table[i + 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j + 1].setNewImgFich(f[dup], fix[i + 1][j + 1]);
                                    table[i + 1][j + 1] = dup;
                                }
                                /**
                                 *
                                 */
                                if (table[i - 1][j - 1] != 0 && table[i - 1][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j - 1].setNewImgFich(f[dup], fix[i - 1][j - 1]);
                                    table[i - 1][j - 1] = dup;
                                }
                                if (table[i - 1][j] != 0 && table[i - 1][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j].setNewImgFich(f[dup], fix[i - 1][j]);
                                    table[i - 1][j] = dup;
                                }
                                if (table[i - 1][j + 1] != 0 && table[i - 1][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j + 1].setNewImgFich(f[dup], fix[i - 1][j + 1]);
                                    table[i - 1][j + 1] = dup;
                                }
                                //saltar
                                if (table[i][j - 2] != 0 && table[i][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j - 2].setNewImgFich(f[salt], fix[i][j - 2]);
                                    table[i][j - 2] = salt;
                                }
                                if (table[i + 1][j - 2] != 0 && table[i + 1][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j - 2].setNewImgFich(f[salt], fix[i + 1][j - 2]);
                                    table[i + 1][j - 2] = salt;
                                }
                                if (table[i + 2][j - 2] != 0 && table[i + 2][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j - 2].setNewImgFich(f[salt], fix[i + 2][j - 2]);
                                    table[i + 2][j - 2] = salt;
                                }
                                if (table[i + 2][j - 1] != 0 && table[i + 2][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j - 1].setNewImgFich(f[salt], fix[i + 2][j - 1]);
                                    table[i + 2][j - 1] = salt;
                                }
                                if (table[i + 2][j] != 0 && table[i + 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j].setNewImgFich(f[salt], fix[i + 2][j]);
                                    table[i + 2][j] = salt;
                                }
                                if (table[i + 2][j + 1] != 0 && table[i + 2][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j + 1].setNewImgFich(f[salt], fix[i + 2][j + 1]);
                                    table[i + 2][j + 1] = salt;
                                }
                                if (table[i + 2][j + 2] != 0 && table[i + 2][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 2][j + 2].setNewImgFich(f[salt], fix[i + 2][j + 2]);
                                    table[i + 2][j + 2] = salt;
                                }
                                if (table[i + 1][j + 2] != 0 && table[i + 1][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i + 1][j + 2].setNewImgFich(f[salt], fix[i + 1][j + 2]);
                                    table[i + 1][j + 2] = salt;
                                }
                                if (table[i][j + 2] != 0 && table[i][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i][j + 2].setNewImgFich(f[salt], fix[i][j + 2]);
                                    table[i][j + 2] = salt;
                                }
                                /**
                                 *
                                 */
                                if (table[i - 1][j - 2] != 0 && table[i - 1][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j - 2].setNewImgFich(f[salt], fix[i - 1][j - 2]);
                                    table[i - 1][j - 2] = salt;
                                }
                                if (table[i - 2][j - 2] != 0 && table[i - 2][j - 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j - 2].setNewImgFich(f[salt], fix[i - 2][j - 2]);
                                    table[i - 2][j - 2] = salt;
                                }
                                if (table[i - 2][j - 1] != 0 && table[i - 2][j - 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j - 1].setNewImgFich(f[salt], fix[i - 2][j - 1]);
                                    table[i - 2][j - 1] = salt;
                                }
                                if (table[i - 2][j] != 0 && table[i - 2][j] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j].setNewImgFich(f[salt], fix[i - 2][j]);
                                    table[i - 2][j] = salt;
                                }
                                if (table[i - 2][j + 1] != 0 && table[i - 2][j + 1] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j + 1].setNewImgFich(f[salt], fix[i - 2][j + 1]);
                                    table[i - 2][j + 1] = salt;
                                }
                                if (table[i - 2][j + 2] != 0 && table[i - 2][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 2][j + 2].setNewImgFich(f[salt], fix[i - 2][j + 2]);
                                    table[i - 2][j + 2] = salt;
                                }
                                if (table[i - 1][j + 2] != 0 && table[i - 1][j + 2] == 1) {
                                    nextPlay = 1;
                                    fich[i - 1][j + 2].setNewImgFich(f[salt], fix[i - 1][j + 2]);
                                    table[i - 1][j + 2] = salt;
                                }
                            }
                        }
                        if (e.getSource() == fix[i][j] && table[i][j] == dup && nextPlay == 1) {
                            nextPlay = 0;
                            fich[i][j].setNewImgFich(f[turno], fix[i][j]);
                            table[i][j] = turno;
                            CambioFicha(i, j);
                            recontruirTablero();
                            break;
                        }
                        if (e.getSource() == fix[i][j] && table[i][j] == salt && nextPlay == 1) {
                            nextPlay = 0;
                            fich[i][j].setNewImgFich(f[turno], fix[i][j]);
                            table[i][j] = turno;
                            fich[posAI][posAJ].setNewImgFich(f[vacio], fix[posAI][posAJ]);
                            table[posAI][posAJ] = vacio;
                            CambioFicha(i, j);
                            recontruirTablero();
                            break;
                        }

                    }
                }

            }

        } else {
            JOptionPane.showMessageDialog(null, "ESPERE SU TURNO", "!!!ERROR¡¡¡", JOptionPane.ERROR_MESSAGE);
        }
        if (nextPlay == 0) {
            for (int i = 0; i < Tablero.FIL; i++) {
                for (int j = 0; j < Tablero.COL; j++) {
                    fix[i][j].repaint();

                }
            }
            containerFicha.repaint();
            super.repaint();
            nextPlay = -1;
            cadTablero = Tablero.initStringTable();
            cliente.enviarMsg(cadTablero);
        }
    }

    public void recontruirTablero() {
        for (int i = 0; i < Tablero.FIL; i++) {
            for (int j = 0; j < Tablero.COL; j++) {
                if (salt == table[i][j] || table[i][j] == dup) {
                    table[i][j] = vacio;
                    fich[i][j].setNewImgFich(f[vacio], fix[i][j]);
                }
            }
        }
    }

    public void CambioFicha(int i, int j) {

        Boolean valFront = false;
        if (i == 0 && !valFront) {
            valFront = true;
            if (table[i][j - 1] == rival) {
                fich[i][j - 1].setNewImgFich(f[turno], fix[i][j - 1]);
                table[i][j - 1] = turno;
            }
            if (table[i][j + 1] == rival) {
                fich[i][j + 1].setNewImgFich(f[turno], fix[i][j + 1]);
                table[i][j + 1] = turno;
            }
            if (table[i + 1][j] == rival) {
                fich[i + 1][j].setNewImgFich(f[turno], fix[i + 1][j]);
                table[i + 1][j] = turno;
            }
            if (table[i + 1][j - 1] == rival) {
                fich[i + 1][j - 1].setNewImgFich(f[turno], fix[i + 1][j - 1]);
                table[i + 1][j - 1] = turno;
            }
            if (table[i + 1][j + 1] == rival) {
                fich[i + 1][j + 1].setNewImgFich(f[turno], fix[i + 1][j + 1]);
                table[i + 1][j + 1] = turno;
            }
        }
        if (i == (Tablero.FIL - 1) && !valFront) {
            valFront = true;
            if (table[i][j - 1] == rival) {
                fich[i][j - 1].setNewImgFich(f[turno], fix[i][j - 1]);
                table[i][j - 1] = turno;
            }
            if (table[i][j + 1] == rival) {
                fich[i][j + 1].setNewImgFich(f[turno], fix[i][j + 1]);
                table[i][j + 1] = turno;
            }
            if (table[i - 1][j - 1] == rival) {
                fich[i - 1][j - 1].setNewImgFich(f[turno], fix[i - 1][j - 1]);
                table[i - 1][j - 1] = turno;
            }
            if (table[i - 1][j] == rival) {
                fich[i - 1][j].setNewImgFich(f[turno], fix[i - 1][j]);
                table[i - 1][j] = turno;
            }
            if (table[i - 1][j + 1] == rival) {
                fich[i - 1][j + 1].setNewImgFich(f[turno], fix[i - 1][j + 1]);
                table[i - 1][j + 1] = turno;
            }
        }
        if (j == 0 && !valFront) {
            valFront = true;
            if (table[i - 1][j] == rival) {
                fich[i - 1][j].setNewImgFich(f[turno], fix[i - 1][j]);
                table[i - 1][j] = turno;
            }
            if (table[i - 1][j + 1] == rival) {
                fich[i - 1][j + 1].setNewImgFich(f[turno], fix[i - 1][j + 1]);
                table[i - 1][j + 1] = turno;
            }
            if (table[i][j + 1] == rival) {
                fich[i][j + 1].setNewImgFich(f[turno], fix[i][j + 1]);
                table[i][j + 1] = turno;
            }
            if (table[i + 1][j + 1] == rival) {
                fich[i + 1][j + 1].setNewImgFich(f[turno], fix[i + 1][j + 1]);
                table[i + 1][j + 1] = turno;
            }
            if (table[i + 1][j] == rival) {
                fich[i + 1][j].setNewImgFich(f[turno], fix[i + 1][j]);
                table[i + 1][j] = turno;
            }
        }
        if (j == (Tablero.COL - 1) && !valFront) {
            valFront = true;
            if (table[i - 1][j - 1] == rival) {
                fich[i - 1][j - 1].setNewImgFich(f[turno], fix[i - 1][j - 1]);
                table[i - 1][j - 1] = turno;
            }
            if (table[i - 1][j] == rival) {
                fich[i - 1][j].setNewImgFich(f[turno], fix[i - 1][j]);
                table[i - 1][j] = turno;
            }
            if (table[i][j - 1] == rival) {
                fich[i][j - 1].setNewImgFich(f[turno], fix[i][j - 1]);
                table[i][j - 1] = turno;
            }
            if (table[i + 1][j - 1] == rival) {
                fich[i + 1][j - 1].setNewImgFich(f[turno], fix[i + 1][j - 1]);
                table[i + 1][j - 1] = turno;
            }
            if (table[i + 1][j] == rival) {
                fich[i + 1][j].setNewImgFich(f[turno], fix[i + 1][j]);
                table[i + 1][j] = turno;
            }
        }
        //···························································
        if (i == 1 && !valFront) {
            valFront = true;
            if (table[i][j - 1] == rival) {
                fich[i][j - 1].setNewImgFich(f[turno], fix[i][j - 1]);
                table[i][j - 1] = turno;
            }
            if (table[i][j + 1] == rival) {
                fich[i][j + 1].setNewImgFich(f[turno], fix[i][j + 1]);
                table[i][j + 1] = turno;
            }
            if (table[i + 1][j] == rival) {
                fich[i + 1][j].setNewImgFich(f[turno], fix[i + 1][j]);
                table[i + 1][j] = turno;
            }
            if (table[i + 1][j - 1] == rival) {
                fich[i + 1][j - 1].setNewImgFich(f[turno], fix[i + 1][j - 1]);
                table[i + 1][j - 1] = turno;
            }
            if (table[i + 1][j + 1] == rival) {
                fich[i + 1][j + 1].setNewImgFich(f[turno], fix[i + 1][j + 1]);
                table[i + 1][j + 1] = turno;
            }
            if (table[i - 1][j - 1] == rival) {
                fich[i - 1][j - 1].setNewImgFich(f[turno], fix[i - 1][j - 1]);
                table[i - 1][j - 1] = turno;
            }
            if (table[i - 1][j] == rival) {
                fich[i - 1][j].setNewImgFich(f[turno], fix[i - 1][j]);
                table[i - 1][j] = turno;
            }
            if (table[i - 1][j + 1] == rival) {
                fich[i - 1][j + 1].setNewImgFich(f[turno], fix[i - 1][j + 1]);
                table[i - 1][j + 1] = turno;
            }
        }
        if (i == (Tablero.FIL - 2) && !valFront) {
            valFront = true;
            if (table[i][j - 1] == rival) {
                fich[i][j - 1].setNewImgFich(f[turno], fix[i][j - 1]);
                table[i][j - 1] = turno;
            }
            if (table[i][j + 1] == rival) {
                fich[i][j + 1].setNewImgFich(f[turno], fix[i][j + 1]);
                table[i][j + 1] = turno;
            }
            if (table[i - 1][j - 1] == rival) {
                fich[i - 1][j - 1].setNewImgFich(f[turno], fix[i - 1][j - 1]);
                table[i - 1][j - 1] = turno;
            }
            if (table[i - 1][j] == rival) {
                fich[i - 1][j].setNewImgFich(f[turno], fix[i - 1][j]);
                table[i - 1][j] = turno;
            }
            if (table[i - 1][j + 1] == rival) {
                fich[i - 1][j + 1].setNewImgFich(f[turno], fix[i - 1][j + 1]);
                table[i - 1][j + 1] = turno;
            }
            if (table[i + 1][j - 1] == rival) {
                fich[i + 1][j - 1].setNewImgFich(f[turno], fix[i + 1][j - 1]);
                table[i + 1][j - 1] = turno;
            }
            if (table[i + 1][j] == rival) {
                fich[i + 1][j].setNewImgFich(f[turno], fix[i + 1][j]);
                table[i + 1][j] = turno;
            }
            if (table[i + 1][j + 1] == rival) {
                fich[i + 1][j + 1].setNewImgFich(f[turno], fix[i + 1][j + 1]);
                table[i + 1][j + 1] = turno;
            }
        }
        if (j == 1 && !valFront) {
            valFront = true;
            if (table[i - 1][j] == rival) {
                fich[i - 1][j].setNewImgFich(f[turno], fix[i - 1][j]);
                table[i - 1][j] = turno;
            }
            if (table[i - 1][j + 1] == rival) {
                fich[i - 1][j + 1].setNewImgFich(f[turno], fix[i - 1][j + 1]);
                table[i - 1][j + 1] = turno;
            }
            if (table[i][j + 1] == rival) {
                fich[i][j + 1].setNewImgFich(f[turno], fix[i][j + 1]);
                table[i][j + 1] = turno;
            }
            if (table[i + 1][j + 1] == rival) {
                fich[i + 1][j + 1].setNewImgFich(f[turno], fix[i + 1][j + 1]);
                table[i + 1][j + 1] = turno;
            }
            if (table[i + 1][j] == rival) {
                fich[i + 1][j].setNewImgFich(f[turno], fix[i + 1][j]);
                table[i + 1][j] = turno;
            }
            if (table[i][j - 1] == rival) {
                fich[i][j - 1].setNewImgFich(f[turno], fix[i][j - 1]);
                table[i][j - 1] = turno;
            }
            if (table[i + 1][j - 1] == rival) {
                fich[i + 1][j - 1].setNewImgFich(f[turno], fix[i + 1][j - 1]);
                table[i + 1][j - 1] = turno;
            }
            if (table[i - 1][j - 1] == rival) {
                fich[i - 1][j - 1].setNewImgFich(f[turno], fix[i - 1][j - 1]);
                table[i - 1][j - 1] = turno;
            }
        }
        if (j == (Tablero.COL - 2) && !valFront) {
            valFront = true;
            if (table[i - 1][j - 1] == rival) {
                fich[i - 1][j - 1].setNewImgFich(f[turno], fix[i - 1][j - 1]);
                table[i - 1][j - 1] = turno;
            }
            if (table[i - 1][j] == rival) {
                fich[i - 1][j].setNewImgFich(f[turno], fix[i - 1][j]);
                table[i - 1][j] = turno;
            }
            if (table[i][j - 1] == rival) {
                fich[i][j - 1].setNewImgFich(f[turno], fix[i][j - 1]);
                table[i][j - 1] = turno;
            }
            if (table[i + 1][j - 1] == rival) {
                fich[i + 1][j - 1].setNewImgFich(f[turno], fix[i + 1][j - 1]);
                table[i + 1][j - 1] = turno;
            }
            if (table[i + 1][j] == rival) {
                fich[i + 1][j].setNewImgFich(f[turno], fix[i + 1][j]);
                table[i + 1][j] = turno;
            }
            if (table[i - 1][j + 1] == rival) {
                fich[i - 1][j + 1].setNewImgFich(f[turno], fix[i - 1][j + 1]);
                table[i - 1][j + 1] = turno;
            }
            if (table[i + 1][j + 1] == rival) {
                fich[i + 1][j + 1].setNewImgFich(f[turno], fix[i + 1][j + 1]);
                table[i + 1][j + 1] = turno;
            }
            if (table[i][j + 1] == rival) {
                fich[i][j + 1].setNewImgFich(f[turno], fix[i][j + 1]);
                table[i][j + 1] = turno;
            }
        }
        //···························································
        if (!valFront) {
            if (table[i][j - 1] == rival) {
                fich[i][j - 1].setNewImgFich(f[turno], fix[i][j - 1]);
                table[i][j - 1] = turno;
            }
            if (table[i][j + 1] == rival) {
                fich[i][j + 1].setNewImgFich(f[turno], fix[i][j + 1]);
                table[i][j + 1] = turno;
            }
            if (table[i + 1][j] == rival) {
                fich[i + 1][j].setNewImgFich(f[turno], fix[i + 1][j]);
                table[i + 1][j] = turno;
            }
            if (table[i + 1][j - 1] == rival) {
                fich[i + 1][j - 1].setNewImgFich(f[turno], fix[i + 1][j - 1]);
                table[i + 1][j - 1] = turno;
            }
            if (table[i + 1][j + 1] == rival) {
                fich[i + 1][j + 1].setNewImgFich(f[turno], fix[i + 1][j + 1]);
                table[i + 1][j + 1] = turno;
            }
            if (table[i - 1][j - 1] == rival) {
                fich[i - 1][j - 1].setNewImgFich(f[turno], fix[i - 1][j - 1]);
                table[i - 1][j - 1] = turno;
            }
            if (table[i - 1][j] == rival) {
                fich[i - 1][j].setNewImgFich(f[turno], fix[i - 1][j]);
                table[i - 1][j] = turno;
            }
            if (table[i - 1][j + 1] == rival) {
                fich[i - 1][j + 1].setNewImgFich(f[turno], fix[i - 1][j + 1]);
                table[i - 1][j + 1] = turno;
            }
        }
    }

    public static int getTurnoActual() {
        return turnoActual;
    }

    public static void setTurnoActual(int turnoActual) {
        FrameCliente.turnoActual = turnoActual;
    }

    public static int getTurno() {
        return turno;
    }

    public static void setTurno(int turno) {
        FrameCliente.turno = turno;
    }

    public static String getCadTablero() {
        return cadTablero;
    }

    public static String getMsjBienvenida() {
        return msjBienvenida;
    }

    public static void setCadTablero(String cadTablero) {
        FrameCliente.cadTablero = cadTablero;
    }

    public static void setMsjBienvenida(String msjBienvenida) {
        FrameCliente.msjBienvenida = msjBienvenida;
    }

    public static JLabel getContainerFicha() {
        return containerFicha;
    }

    public static void setContainerFicha(JLabel containerFicha) {
        FrameCliente.containerFicha = containerFicha;
    }

    public static int getCantUser() {
        return cantUser;
    }

    public static void setCantUser(int cantUser) {
        FrameCliente.cantUser = cantUser;
    }

}
