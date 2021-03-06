package pgv.service.servidor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TextServidor {

	public static void main(String[] args) {
		
		String respuesta="";
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(5555);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Socket cs;
		File directorio = new  File("Txt");
		try {
			System.out.println(" - Servidor creado -\n\n");
			while (true) {
				StringBuilder aux = new StringBuilder();
				System.out.println(" -  Esperando  - \n\n");
				cs = ss.accept();
				DataInputStream dis = new DataInputStream(cs.getInputStream());
				DataOutputStream dos = new DataOutputStream(cs.getOutputStream());
				
				
				
				System.out.println(" - Cliente Conectado - \n\n");
				String recibido = dis.readUTF();
				if (!recibido.equals("abortado")) {
					if (recibido.equals("lista")) {
						System.out.println(" - Lista - \n\n");
						String[] cosa = directorio.list();
						for (int i = 0; i < cosa.length; i++) {
							aux.append(cosa[i] + ",");
						}

						respuesta = aux.toString();
						dos.writeUTF(respuesta);
					} else {
						String[] arrayRecibido = recibido.split(":");
						String orden = arrayRecibido[0];
						String archivo = arrayRecibido[1];
						if (orden.equals("eliminar")) {
							new File("Txt/" + archivo).delete();
						} else if (orden.equals("importar")) {
							BufferedWriter bw = new BufferedWriter(new FileWriter("Txt/" + archivo));
							try {
								while (true) {
									bw.write(dis.readUTF() + "\n");
								}
							} catch (Exception e) {
								bw.close();
							}
						} else if (orden.equals("exportar")) {
							BufferedReader br = new BufferedReader(new FileReader("Txt/" + archivo));
							String aux2;
							while ((aux2 = br.readLine()) != null) {
								dos.writeUTF(aux2);
							}
							br.close();
							dos.close();
						}
						System.out.println(" - Orden realizada : "+orden+" - \n\n");
					} 
				}	
				System.out.println(" - Cliente desconectado - \n\n");
			}

		} catch (Exception  e) { e.printStackTrace(); }finally {
		
		}
	}
}