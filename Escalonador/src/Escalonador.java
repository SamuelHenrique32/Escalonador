import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Escalonador {
	
	// tamanho maximo de uma historia para leitura
	private static final int maxHistorySize = 100;
	
	// quantidade maxima de transacoes para leitura
	private static final int maxTransactionQuantity = 100;
	
	// Opcao do menu, String para nao dar problema para ler outros dados depois de ler int
	private String option;
	
	// Historia lida do teclado
	private String historyRead;
	
	// Para a historia sem espaços
	private String[] historySplited;
	
	// Arraylist cada posicao possui uma operacao
	private ArrayList<String> history;
	
	// Transacao atual lida do teclado, sera passada para a String transactions
	private String currentTransactionRead;
	
	// para transacoes sem "*"
	private String[] transactionsSplited;
	
	// Array com todas transacoes separadas por "*"
	private String transactions;
	
	// Arraylist cada posicao possui uma transacao
	private ArrayList<String> transactionArray;
	 
	// Numero de transacoes lidas
	private int numberOfTransactions;
	
	// Ler do teclado
	private Scanner reader;
	
	//Ctrl + 3 -> construct
	public Escalonador() {
		this.option = new String(); 
		this.historyRead = new String();
		this.historySplited = new String[maxHistorySize];
		this.history = new ArrayList<String>();
		this.currentTransactionRead = new String();
		this.transactionsSplited = new String[maxTransactionQuantity];
		this.transactions = new String();	
		this.transactionArray = new ArrayList<String>();
		this.numberOfTransactions = 0;
		this.reader = new Scanner(System.in);
	}
	
	//Ctrl + 3 -> ggas
	public String getOption() {
		return option;
	}

	public void showOptions() {
		
		System.out.println("Escolha uma opcao:\n");
		System.out.println("1- Ler do arquivo");
		System.out.println("2- Digitar historia");
		System.out.println("3- Digitar transações");
		System.out.print("Opcao: ");		
	}

	public void readOption() {
		
		option = reader.nextLine();
		
		while(!option.equals("1") && !option.equals("2") && !option.equals("3")) {
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
				
			default:				
				System.out.println("Ocorreu um erro, encerrando...");
		}
	}

	private void readTransactions() {

		System.out.println("\nDigite Enter para proxima transacao e -1 se todas as transacoes foram informadas");		
		
		while(!this.currentTransactionRead.equals("-1")) {
			// Transacao lida
			System.out.print("Digite a transacao T" + this.numberOfTransactions + ": ");
			currentTransactionRead = reader.nextLine();		
			this.numberOfTransactions++;
			this.transactions += this.currentTransactionRead + "*";
		}
		
		this.transactions = this.transactions.substring(0,this.transactions.indexOf("-1"));
		
		System.out.println(this.transactions);

		System.out.println("\nVoce informou " + (this.numberOfTransactions - 1) + " transacoes");
	}

	private void readHistory() {
		
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
		
	}

	private void readFile() {
		// TODO Auto-generated method stub
		
	}
}
