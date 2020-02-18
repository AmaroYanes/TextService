package pgv.service.servidor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
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
		BufferedReader br;
		BufferedWriter bw;
		File directorio = new  File("src\\main\\resources\\Txt");
//		ArrayList<String> clientes = new ArrayList<String>(); 
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
				if(recibido.equals("lista")) {
					System.out.println(" - Lista - \n\n");
					String[] cosa = directorio.list();
					for(int i= 0;i<cosa.length;i++) {
						aux.append(cosa[i]+",");
					}
					
					respuesta = aux.toString();
				}else {
					String[] arrayRecibido = recibido.split(":");
					String orden = arrayRecibido[0];
					String archivo = arrayRecibido[1];
					if(orden.equals("eliminar")) {
						System.out.println(new File(TextServidor.class.getResource("/Txt/"+archivo).toString()));
						
					}else if(orden.equals("importar")) {
						
					}else if(orden.equals("exportar")) {
						
					}
				}
				System.out.println(" - Escribiendo respuesta - \n\n");
				dos.writeUTF(respuesta);
				
			}

		} catch (Exception e) { e.printStackTrace(); }finally {
		
		}
	}
}