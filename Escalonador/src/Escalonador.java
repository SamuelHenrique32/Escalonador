import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Escalonador {
	
	private static final int maxHistorySize = 100;
	
	private int option;
	private String historyRead;
	private String[] historySplited;
	private ArrayList<String> history; 
	
	//Ctrl + 3 -> construct
	public Escalonador() {
		this.option = 0; 
		this.historyRead = new String();
		this.historySplited = new String[maxHistorySize];
		this.history = new ArrayList<String>();
	}

	//Ctrl + 3 -> ggas
	public int getOption() {
		return option;
	}

	public void showOptions() {
		
		System.out.println("Escolha uma opcao:\n");
		System.out.println("1- Ler do arquivo");
		System.out.println("2- Digitar entrada\n\n");
		
	}

	public void readOption() {
		
		Scanner reader = new Scanner(System.in);
		option = reader.nextInt();
		
		while(option!=1 && option != 2) {
			System.out.println("\nOpcao Invalida!");
			this.showOptions();
			option = reader.nextInt();
		}
		System.out.print("\nOpcao Escolhida: ");
		System.out.println(this.getOption());
		
		reader.close();
	}

	public void handleOption() {
		
		switch(this.getOption()) {
		
			case 1:
				this.readFile();
			break;

			case 2:
				this.readHistory();
			break;
				
			default:
				
				System.out.println("Ocorreu um erro, encerrando...");
		}
	}

	private void readHistory() {
		Scanner reader = new Scanner(System.in);
		System.out.println("Digite a historia:");
		// String lida
		this.historyRead = reader.nextLine();
		// String com split
		this.historySplited = this.historyRead.split(" ");
		
		// Posicoes preenchidas: historySplited.length-1
		for(int i=0 ; i< this.historySplited.length ; i++) {
			// Array com cada operacao
			this.history.add(this.historySplited[i]);
		}

//		for (String s : history) {
//			System.out.println(s);
//		}
		
		reader.close();
		
	}

	private void readFile() {
		// TODO Auto-generated method stub
		
	}
}
