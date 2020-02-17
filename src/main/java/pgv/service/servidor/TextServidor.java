package pgv.service.servidor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TextServidor {

	public static void main(String[] args) {
		StringBuilder aux = new StringBuilder();
		String respuesta="";
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(5555);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Socket cs;
		BufferedReader br;
		BufferedWriter bw;
		File directorio = new  File("..\\..\\..\\..\\resources\\Txt");
//		ArrayList<String> clientes = new ArrayList<String>(); 
		try {
			
			while (true) {
				cs = ss.accept();
				br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
				bw = new BufferedWriter(new OutputStreamWriter(cs.getOutputStream()));

				if(br.readLine().equals("lista")) {
					String[] cosa = directorio.list();
					for(int i= 0;i<cosa.length;i++) {
						aux.append(cosa[i]+",");
					}
					respuesta = aux.toString();
				}else {
					
				}
				bw.write(respuesta);
				
			}

		} catch (IOException e) { e.printStackTrace(); }finally {
		
		}
	}
}