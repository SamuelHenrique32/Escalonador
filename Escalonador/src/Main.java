import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("############## ESCALONADOR 2PL STRICT ##############\n");
		
		Escalonador e1 = new Escalonador();		
		e1.showOptions();		
		e1.readOption();
		e1.handleOption();
	}
}
