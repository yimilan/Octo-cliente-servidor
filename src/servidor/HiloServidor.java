
package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author netosolis
 */
public class HiloServidor implements Runnable{
    //Declaramos las variables que utiliza el hilo para estar recibiendo y mandando mensajes
    private final Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    //Lista de los usuarios conectados al servidor
    private LinkedList<Socket> usuarios = new LinkedList<Socket>();
    
    //Constructor que recibe el socket que atendera el hilo y la lista de usuarios conectados
    public HiloServidor(Socket soc,LinkedList users){
        socket = soc;
        usuarios = users;
    }
    
    
    static int numClien = 0;
    static int vali = 0;
    static String[] clienteNum = new String[Servidor.getNoConexiones()];
    private String tableroCad = "";
    private String ultimaJugada = "2";
    @Override
    public void run() {
        try {
            numClien ++;
            clienteNum[numClien-1] = "Cliente "+numClien;
            //Inicializamos los canales de comunicacion y mandamos un mensaje de bienvenida
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("<h2>Bienvenido....</h2>");
            out.writeUTF(clienteNum[numClien-1]);
            out.writeUTF(ultimaJugada);  
            //System.out.println(clienteNum[numClien-1]); 
            //Ciclo infinito para escuchar por mensajes del cliente
            while(true){
                int val = 0;
                String jugadaSig = in.readUTF();
               String recibido = in.readUTF();
               String usuario = in.readUTF();
               //Cuando se recibe un mensaje se envia a todos los usuarios conectados 
                if(usuarios.size()>1){
                    if("Cliente 1".equals(usuario) || "Cliente 2".equals(usuario)){
                     //   if(ultimaJugada.equals(usuario)){
                             for (int i = 0; i < usuarios.size(); i++) {
                                        out = new DataOutputStream(usuarios.get(i).getOutputStream());
                                        out.writeUTF(recibido); 
                                        out.writeUTF(jugadaSig); 
                                        if(clienteNum[i].equals(usuario)){
                                            vali = 1;
                                        }
                                 }
                            
                      //  }
                    }
                    else{
                        int cont = 0;
                        int userNot;
                        for (String retval: usuario.split(" ")) {
                            cont++;
                            if(cont == 2){
                                userNot = Integer.parseInt(retval);
                                 out = new DataOutputStream(usuarios.get(userNot-1).getOutputStream());
                                 out.writeUTF("<h2>Espera tu turno</h2>");
                                 out.writeUTF("NJ"); 
                            }
                         }
                    }
                }         
            }
                      
        } catch (IOException e) {
            //Si ocurre un excepcion lo mas seguro es que sea por que el cliente se desconecto asi que lo quitamos de la lista de conectados
            for (int i = 0; i < usuarios.size(); i++) {
                if(usuarios.get(i) == socket){
                    usuarios.remove(i);
                    if(clienteNum[i].equals("Cliente 2")){
                        vali = 0; 
                    }
                    clienteNum[i] = "";
                    numClien--;
                    break;
                } 
            }
        }
    }
}
