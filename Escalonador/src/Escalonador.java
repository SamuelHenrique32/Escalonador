import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Escalonador {
	
	// tamanho maximo de uma historia para leitura
	private static final int maxHistorySize = 100;
	
	//tamanho maximo de uma transacao
	private static final int maxTransactionLength = 100;
	
	// Opcao do menu, String para nao dar problema para ler outros dados depois de ler int
	private String option;
	
	// Historia lida do teclado
	private String historyRead;
	
	// Para a historia sem espaços
	private String[] historySplited;
	
	// Arraylist cada posicao possui uma operacao
	private ArrayList<String> history;
	
	// Array de transacoes informada pelo usuario
	private ArrayList<Transacao> informedTransactions; 
	
	// Transacao atual lida do teclado, sera passada para a String transactions
	private String currentTransactionRead;
	
	// Numero de transacoes lidas
	private int numberOfTransactions;
	
	// Objeto Historia informada pelo usuario
	private Historia historiaInformada;
	
	// Ler do teclado
	private Scanner reader;
	
	//Ctrl + 3 -> construct
	public Escalonador() {
		this.option = new String(); 
		this.historyRead = new String();
		this.historySplited = new String[maxHistorySize];
		this.history = new ArrayList<String>();
		this.informedTransactions = new ArrayList<Transacao>();
		this.currentTransactionRead = new String();
		this.numberOfTransactions = 0;
		this.historiaInformada = new Historia();		
		this.reader = new Scanner(System.in);
	}
	
	//Ctrl + 3 -> ggas
	public String getOption() {
		return option;
	}	

	public static int getMaxTransactionLength() {
		return maxTransactionLength;
	}

	public void showOptions() {
		
		System.out.println("Escolha uma opcao:\n");
		System.out.println("1- Ler do arquivo");
		System.out.println("2- Digitar historia");
		System.out.println("3- Digitar transacoes");
		System.out.println("4- Mostrar transacoes");
		System.out.print("Opcao: ");		
	}

	public void readOption() {
		
		option = reader.nextLine();
		
		while(!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4")) {
			System.out.println("\nOpcao Invalida!");
			this.showOptions();
			option = reader.nextLine();
		}
		System.out.print("\nOpcao Escolhida: ");
		System.out.println(this.getOption());
	}

	public void handleOption() {
		
		switch(this.getOption()) {
		
			case "1":
				this.readFile();
			break;

			case "2":
				this.readHistory();
			break;
			
			case "3":
				this.readTransactions();				
			break;
			
			case "4":
				this.showTransactions();
			break;
				
			default:				
				System.out.println("Ocorreu um erro, encerrando...");
		}
	}

	private void showTransactions() {
		
		System.out.println("\nTransacoes Cadastradas:\n");
		
		for (Transacao t : informedTransactions) {
			t.printTransacao();
		}
		
	}

	private void readTransactions() {

		System.out.println("\nDigite Enter para proxima transacao e -1 se todas as transacoes foram informadas");		
		
		while(!this.currentTransactionRead.equals("-1")) {
			
			System.out.print("Digite a transacao T" + this.numberOfTransactions + ": ");
			// Transacao lida
			currentTransactionRead = reader.nextLine();
			
			if(!currentTransactionRead.equalsIgnoreCase("-1")) {
			
				Transacao transactionToCreate = new Transacao();
				transactionToCreate.criaTransacao(currentTransactionRead, numberOfTransactions);
				this.informedTransactions.add(transactionToCreate);				
				this.numberOfTransactions++;
			}			
		}
		
		System.out.println("\nVoce informou " + (this.numberOfTransactions) + " transacoes\n");
		
	}

	private void readHistory() {
		
//		System.out.print("\nDigite a historia:");
//		// String lida
//		this.historyRead = reader.nextLine();
//		// String com split
//		this.historySplited = this.historyRead.split(" ");
//
//		// Posicoes preenchidas: historySplited.length-1
//		for(int i=0 ; i< this.historySplited.length ; i++) {
//			// Array com cada operacao
//			this.history.add(this.historySplited[i]);
//		}
//		
//		this.historiaInformada.criarHistoriaInicial(history);
//		
//		System.out.println("\nHistoria criada\n\n");
//		
//		this.historiaInformada.printHistoria();
	}

	private void readFile() {
		// TODO Auto-generated method stub
		
	}
}
