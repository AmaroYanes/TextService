package pgv.service.servidor;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class TextServidor {
	public void init()  {

	}
	public static void main(String[] args) {
		DatagramSocket datagramSocket=null;
		File directorio = new  File("..\\..\\..\\..\\resources\\Txt");
		try {
			StringBuilder aux = new StringBuilder();
			String recivido,respuesta="";
			System.out.println("Creando socket datagrama");
			InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
			datagramSocket = new DatagramSocket(addr);
			while (true) {
				
				System.out.println("Recibiendo mensaje");
				byte[] mensaje = new byte[100];
				DatagramPacket datagrama1 = new DatagramPacket(mensaje, 100);
				datagramSocket.receive(datagrama1);
				recivido = mensaje.toString().trim();
				if(recivido.equals("lista")) {
					String[] cosa = directorio.list();
					for(int i= 0;i<cosa.length;i++) {
						aux.append(cosa[i]+",");
					}
					respuesta = aux.toString();
				}else {
					
				}
				byte[] respuestaBytes = respuesta.getBytes(); 
				InetAddress addr2 = InetAddress.getByName("localhost");
				DatagramPacket datagrama2 = new DatagramPacket(respuestaBytes,respuestaBytes.length, addr2, 5556);
				datagramSocket.send(datagrama2);
				System.out.println("Mensaje enviado");
				
			}

		} catch (IOException e) { e.printStackTrace(); }finally {
			datagramSocket.close();
		}
	}
}