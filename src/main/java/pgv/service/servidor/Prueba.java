package pgv.service.servidor;

import java.io.File;

public class Prueba {

	public static void main(String[] args) {
		File directorio = new  File("src\\main\\resources\\Txt");
		System.out.println(directorio.getAbsolutePath());
		File[] cosa = directorio.listFiles();
		
			System.out.println(cosa.length+",");
		

	}

}
