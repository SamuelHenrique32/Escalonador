import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Escalonador {

	// tamanho maximo de uma historia para leitura
	protected static final int maxHistorySize = 100;

	// tamanho maximo de uma transacao
	private static final int maxTransactionLength = 100;

	// Opcao do menu, String para nao dar problema para ler outros dados depois de
	// ler int
	private String option;

	// Historia lida do teclado
	private String historyRead;

	// Array de transacoes informada pelo usuario
	private ArrayList<Transacao> informedTransactions;

	// Array de transacoes lidas do arquivo
	private ArrayList<Transacao> storedTransactions;

	// Transacao atual lida do teclado, sera passada para a String transactions
	private String currentTransactionRead;

	// Numero de transacoes lidas
	private int numberOfTransactions;

	// Objeto Historia informada pelo usuario
	private Historia informedHistory;

	// Ler do teclado
	private Scanner reader;

	// Ctrl + 3 -> construct
	public Escalonador() {
		this.option = new String();
		this.historyRead = new String();
		this.informedTransactions = new ArrayList<Transacao>();
		this.storedTransactions = new ArrayList<Transacao>();
		this.currentTransactionRead = new String();
		this.numberOfTransactions = 0;
		this.informedHistory = new Historia();
		this.reader = new Scanner(System.in);
	}

	// Ctrl + 3 -> ggas
	public String getOption() {
		return option;
	}

	public static int getMaxTransactionLength() {
		return maxTransactionLength;
	}

	public void showOptions() {

		System.out.println("\nEscolha uma opcao:\n");
		System.out.println("1- Ler transacoes do arquivo");			
		System.out.println("2- Cadastrar transacoes");
		System.out.println("3- Digitar historia inicial");
		System.out.println("4- Mostrar transacoes cadastradas");
		System.out.println("5- Mostrar transacoes armazenadas");
		System.out.print("Opcao: ");
	}

	public void readOption() {

		option = reader.nextLine();

		while (!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4") && !option.equals("5")) {
			System.out.println("\nOpcao Invalida!");
			this.showOptions();
			option = reader.nextLine();
		}
		System.out.print("\nOpcao Escolhida: ");
		System.out.println(this.getOption());
	}

	public void handleOption() {

		switch (this.getOption()) {

		case "1":
			this.readFile();
		break;

		case "2":
			this.readTransactions();			
		break;

		case "3":
			this.readHistory();
		break;

		case "4":
			this.showTransactionsRead();
		break;
		
		case "5":
			this.showTransactionsStored();
		break;

		default:
			System.out.println("Ocorreu um erro, encerrando...");
		}
	}

	private void showTransactionsStored() {

		System.out.println("\nTransacoes Armazenadas:\n");

		for (Transacao t : storedTransactions) {
			t.printTransacao();
		}
		
	}

	private void showTransactionsRead() {

		System.out.println("\nTransacoes Cadastradas:\n");

		for (Transacao t : informedTransactions) {
			t.printTransacao();
		}

	}

	private void readTransactions() {

		System.out.println("\nDigite Enter para proxima transacao e -1 se todas as transacoes foram informadas");

		while (!this.currentTransactionRead.equals("-1")) {

			System.out.print("Digite a transacao T" + this.numberOfTransactions + ": ");
			// Transacao lida
			currentTransactionRead = reader.nextLine();

			if (!currentTransactionRead.equalsIgnoreCase("-1")) {

				Transacao transactionToCreate = new Transacao();
				transactionToCreate.criaTransacao(currentTransactionRead, numberOfTransactions);
				this.informedTransactions.add(transactionToCreate);
				this.numberOfTransactions++;
			}
		}

		System.out.println("\nVoce informou " + (this.numberOfTransactions) + " transacoes\n");

	}

	private void readHistory() {

		System.out.println("\nOBS: As transacoes constituintes ja necessitam estar cadastradas");
		System.out.print("Digite a historia: ");

		// String lida
		this.historyRead = reader.nextLine();
		informedHistory.criarHistoriaInicial(historyRead);

		// this.informedHistory.printHistoria();
	}

	private void readFile() {
		
		int currentLine = 0;
		
		try {
			FileReader arq = new FileReader("C:\\Users\\Samuel\\Desktop\\Escalonador\\transactions.txt");
			BufferedReader readArq = new BufferedReader(arq);

			// le a primeira linha
			String line = readArq.readLine(); 
			Transacao transactionToStore = new Transacao();
			
			System.out.println("\nLinhas Lidas:");
			
			// a variável "line" recebe o valor "null" quando o processo
			// de repetição atingir o final do arquivo texto
			while (line != null) {
				System.out.printf("%s\n", line);
				
				transactionToStore = new Transacao();
				transactionToStore.criaTransacao(line, currentLine);
				
				this.storedTransactions.add(transactionToStore);
				currentLine++;
				
				// lê da segunda até a última linha
				line = readArq.readLine(); 
			}

			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

	}
}
