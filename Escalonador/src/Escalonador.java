import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
	
	// Objeto Historia gerado pela aplicacao
	private Historia generatedHistory;
	
	// Array de operacoes em espera
	private ArrayList<Operacao> delayOperations;
	
	// Dados nas transacoes
	private HashSet<Character> data;
	
	// Transacoes no escalonador
	private HashSet<Integer> transactions;
	
	// Dados com bloqueio exclusivo
	private ArrayList<Operacao> exclusiveLock;
	
	// Dados com bloqueio compartilhado
	private ArrayList<Operacao> sharedLock;
	
	// Historia final
	private ArrayList<String> finalHistory;
	
	// Operacoes a serem removidas ao receber commit
	private ArrayList<Operacao> operationsToRemove;

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
		this.generatedHistory = new Historia();
		this.delayOperations = new ArrayList<Operacao>();
		this.data = new HashSet<Character>();
		this.transactions = new HashSet<Integer>();
		this.exclusiveLock = new ArrayList<Operacao>();
		this.sharedLock = new ArrayList<Operacao>();
		this.finalHistory = new ArrayList<String>();
		this.operationsToRemove = new ArrayList<Operacao>();
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

		System.out.println("\n\nEscolha uma Opcao:\n");
		System.out.println("1- Ler Transacoes do Arquivo");			
		System.out.println("2- Cadastrar Transacoes");
		System.out.println("3- Digitar Historia Inicial");
		System.out.println("4- Mostrar Transacoes Cadastradas");
		System.out.println("5- Mostrar Transacoes Armazenadas");
		System.out.println("6- Gerar Historia de Execucao");
		System.out.println("7- Executar Escalonador");
		System.out.print("Opcao: ");
	}

	public void readOption() {

		option = reader.nextLine();

		while (!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4") && !option.equals("5")  && !option.equals("6")  && !option.equals("7")) {
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
		
		case "6":
			this.generateHistory();
		break;
		
		case "7":
			
			this.finalHistory = new ArrayList<String>();
			System.out.println("\n\n################## ESCALONADOR ##################\n");
			String option = new String();
			
			while(!option.equals("1") && !option.equals("2")) {
				System.out.print("Digite 1 para utilizar a historia cadastrada e 2 para utilizar a historia gerada: ");
				option = reader.nextLine();
			}
			
			if(option.equals("1")) {

				this.informedHistory.copyToShuffledOperations();
				this.scheduler(this.informedHistory.getShuffledOperations(), this.informedHistory.getOperationsSize());
			} else {
				this.scheduler(this.generatedHistory.getShuffledOperations(), this.generatedHistory.getOperationsSize());
			}			
			
		break;

		default:
			System.out.println("Ocorreu um erro, encerrando...");
		}
	}

	private void generateHistory() {
		
		String optionToGenerateHistory = "0";		
		
		System.out.println("\nGerar Historia de Execucao:\n");		
		showOptionsToGenerateHistory();
		
		optionToGenerateHistory = reader.nextLine();
		
		while(!optionToGenerateHistory.equals("1") && !optionToGenerateHistory.equals("2")) {
			System.out.println("\nOpcao Invalida!");			
			showOptionsToGenerateHistory();
			optionToGenerateHistory = reader.nextLine();			
		}
		
		System.out.print("\nOpcao Escolhida: ");
		System.out.println(optionToGenerateHistory);
		
		if(optionToGenerateHistory.equals("1")) {
			this.generatedHistory.createExecutionSequence(informedTransactions);
		} else {
			this.generatedHistory.createExecutionSequence(storedTransactions);
		}				
	}

	private void showOptionsToGenerateHistory() {
		System.out.println("1- Transacoes Informadas pelo Usuario");
		System.out.println("2- Transacoes Armazenadas");
		System.out.print("Escolha uma opcao: ");
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
		
		// Limpa Transacoes armazenadas
		this.storedTransactions.clear();

		System.out.println("\nDigite Enter para proxima transacao e -1 se todas as transacoes foram informadas");

		while (!this.currentTransactionRead.equals("-1")) {

			System.out.print("Digite a transacao T" + this.numberOfTransactions + ": ");
			// Transacao lida
			currentTransactionRead = reader.nextLine();

			if (!currentTransactionRead.equalsIgnoreCase("-1")) {

				Transacao transactionToCreate = new Transacao();
				transactionToCreate.criaTransacao(currentTransactionRead, numberOfTransactions);
				
				// Adiciona ao hashset
				// Nao repetira elementos pois e um hashset
				this.createDataList(currentTransactionRead);
				
				this.informedTransactions.add(transactionToCreate);
				this.numberOfTransactions++;
			}
		}

		System.out.println("\nVoce informou " + (this.numberOfTransactions) + " transacoes\n");

	}

	private void readHistory() {

		System.out.print("Digite a historia: ");

		// String lida
		this.historyRead = reader.nextLine();
		informedHistory.createInitialHistory(historyRead);

		// this.informedHistory.printHistoria();
	}

	private void readFile() {
		
		int currentLine = 0;
		
		// Limpa Transacoes armazenadas
		this.storedTransactions.clear();
		
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
				
				// Cria dados
				this.createDataList(line);
				
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

	private void createDataList(String line) {

		// Para cada posicao na linha
		for(int i =0 ; i<line.length() ; i++) {
			
			// Adiciona ao hashset
			if(line.charAt(i) == '[') {
				// Nao repetira elementos pois e um hashset
				this.data.add(line.charAt(i+1));
			}
		}		
	}
	
	private void scheduler(Operacao[] operations, int iterationLimit) {
		
		// Mantem em hashset as diferentes transacoes atuais, utilizado para retirar operacoes do delay
		transactions.clear();
		for(int j=0 ; j<iterationLimit ; j++) {
			transactions.add(operations[j].getTransacao());
		}
					
		// Para cada operacao na historia
		for(int i=0 ; i<iterationLimit ; i++) {

			// Verificar se pode tirar alguma operacao do delay
			// Mantem em loop ate retornar falso
			while(analizeDelayedOperations()) {
				System.out.println("\n\nHistoria atualizada:");
				showFinalHistory();
				System.out.println("\n");
			}		

			System.out.println("\n\n\nAnalisando " + operations[i].getOperacao() + "\n");
			
			if(this.verifyIfIsInDelay(operations[i].getOperacao())) {
				System.out.println("A operacao " + operations[i].getTransacao() + " possui alguma operacao em delay!");
				System.out.println("Adicionada a operacao " + operations[i].getOperacao() + " na lista de delay\n");
				this.delayOperations.add(operations[i]);
				showDelayedOperations();
				System.out.println("\n\nHistoria atualizada:");
				showFinalHistory();
				System.out.println("\n\nPressione Enter para continuar...");
				String pauseScheduler = reader.nextLine();
				continue;
			}
			
			showDelayedOperations();	
			schedulerHandleCurrentOperation(operations[i]);			
		
			System.out.println("\n\nHistoria atualizada:");
			showFinalHistory();
			
			System.out.println("\n\nPressione Enter para continuar...");
			String pauseScheduler = reader.nextLine();
		}		
	}

	private void schedulerHandleCurrentOperation(Operacao operation) {
		
		// Pega operacao
		switch(operation.getOperacao().charAt(0)) {
			// Read
			case 'r':
				
				// Verificar se ha bloqueio exclusivo por outra transacao
				if(verifyIfIsExclusiveLockedForOtherTransaction(operation)) {
					
					System.out.println("Ha outra transacao com bloqueio exclusivo, operacao " + operation.getOperacao() + " adicionada na lista de delay");
					
					// Adicionar para delay
					this.delayOperations.add(operation);
					break;
				}
				
				// Se transacao possuir bloqueio exclusivo ou compartilhado sobre dado
				if(verifyIfIsExclusiveLockedForATransaction(operation) || verifyIfIsSharedLockedForATransaction(operation)){
					
					System.out.println("Transacao ja possui bloqueio sobre o dado");
					
					// Add na historia final
					finalHistory.add(operation.getOperacao());
					break;
				}
				
				System.out.println("Pedir bloqueio compartilhado");
				
				// Pedir bloqueio compartilhado
				this.sharedLock.add(operation);
				// Adiciona bloqueio compartilhado na historia final
				this.finalHistory.add("ls" + operation.getTransacao() + "[" + operation.getData() + "]");
				// Adiciona operacao na historia final
				this.finalHistory.add(operation.getOperacao());		
				
			break;
			
			// Write
			case 'w':
				
				// Verificar se ha bloqueio exclusivo ou compartilhado por outra transacao
				if(verifyIfIsExclusiveLockedForOtherTransaction(operation) || verifyIfIsSharedLockedForOtherTransaction(operation)) {
					
					System.out.println("Ha outra transacao com bloqueio, operacao " + operation.getOperacao() + " adicionada na lista de delay");
					
					// Adicionar para delay
					this.delayOperations.add(operation);
					break;
				}
				
				// Se operacao possuir bloqueio exclusivo sobre dado
				if(verifyIfIsExclusiveLockedForATransaction(operation)) {
					
					System.out.println("Transacao ja possui bloqueio exclusivo sobre o dado");
					
					// Add na historia final
					finalHistory.add(operation.getOperacao());
					break;
				}
				
				// Se transacao possuir bloqueio compartilhado sobre dado
				if(verifyIfIsSharedLockedForATransaction(operation)) {
					
					// Upgrade de bloqueio compartilhado para exclusivo
					System.out.println("Transacao ja possui bloqueio compartilhado, upgrade para bloqueio exclusivo");						
					// Remove bloqueio compartilhado da transacao sobre dado
					this.removeFromSharedLock(operation);
				}
				
				System.out.println("\nPedir bloqueio exclusivo");
				
				// Pedir bloqueio exclusivo
				this.exclusiveLock.add(operation);					
				// Adiciona bloqueio exclusivo na historia final
				this.finalHistory.add("lx" + operation.getTransacao() + "[" + operation.getData() + "]");
				// Adiciona operacao na historia final
				this.finalHistory.add(operation.getOperacao());
									
			break;
			
			// Commit
			case 'c':
				
				// Verificar operacoes em delay da transacao
				for (Operacao op : delayOperations) {
					
					// Se encontrar alguma operacao da transacao em delay
					if(op.getTransacao() == operation.getTransacao()) {
						
						System.out.println("Ha operacoes da transacao " + op.getTransacao() + " na lista de espera!");
						System.out.println("Nao e possivel realizar o commit nesse momento");
						System.out.println(operation.getOperacao() + " adicionado para delay");
						this.delayOperations.add(operation);
						break;
					}
				}
				
				// Se nao houver operacoes em delay
				this.finalHistory.add(operation.getOperacao());
				removeExclusiveLocksAfterCommit(operation);
				removeSharedLocksAfterCommit(operation);					

			break;
		}
	}
	
	private boolean schedulerHandleDelayedOperation(Operacao operation) {
		
		// Pega operacao
		switch(operation.getOperacao().charAt(0)) {
			// Read
			case 'r':
				
				// Verificar se ha bloqueio exclusivo por outra transacao
				if(verifyIfIsExclusiveLockedForOtherTransaction(operation)) {
					
					// Nada a fazer
					return false;
					//break;
				}
				
				// Se transacao possuir bloqueio exclusivo ou compartilhado sobre dado
				if(verifyIfIsExclusiveLockedForATransaction(operation) || verifyIfIsSharedLockedForATransaction(operation)){
					
					System.out.println("Transacao ja possui bloqueio sobre o dado");					
					// Add na historia final
					finalHistory.add(operation.getOperacao());
					// Remove do delay
					delayOperations.remove(operation);
					return true;
					//break;
				}
				
				System.out.println("Pedir bloqueio compartilhado");				
				// Pedir bloqueio compartilhado
				this.sharedLock.add(operation);
				// Adiciona bloqueio compartilhado na historia final
				this.finalHistory.add("ls" + operation.getTransacao() + "[" + operation.getData() + "]");
				// Adiciona operacao na historia final
				this.finalHistory.add(operation.getOperacao());
				// Remove do delay
				delayOperations.remove(operation);
				return true;
				
			//break;
			
			// Write
			case 'w':
				
				// Verificar se ha bloqueio exclusivo ou compartilhado por outra transacao
				if(verifyIfIsExclusiveLockedForOtherTransaction(operation) || verifyIfIsSharedLockedForOtherTransaction(operation)) {
					
					// Nada a fazer
					return false;
				}
				
				// Se operacao possuir bloqueio exclusivo sobre dado
				if(verifyIfIsExclusiveLockedForATransaction(operation)) {
					
					System.out.println("Transacao ja possui bloqueio exclusivo sobre o dado");					
					// Add na historia final
					finalHistory.add(operation.getOperacao());
					// Remove do delay
					delayOperations.remove(operation);
					return true;
					//break;
				}
				
				// Se transacao possuir bloqueio compartilhado sobre dado
				if(verifyIfIsSharedLockedForATransaction(operation)) {
					
					// Upgrade de bloqueio compartilhado para exclusivo
					System.out.println("Transacao ja possui bloqueio compartilhado, upgrade para bloqueio exclusivo");						
					// Remove bloqueio compartilhado da transacao sobre dado
					this.removeFromSharedLock(operation);
				}
				
				System.out.println("\nPedir bloqueio exclusivo");
				
				// Pedir bloqueio exclusivo
				this.exclusiveLock.add(operation);					
				// Adiciona bloqueio exclusivo na historia final
				this.finalHistory.add("lx" + operation.getTransacao() + "[" + operation.getData() + "]");
				// Adiciona operacao na historia final
				this.finalHistory.add(operation.getOperacao());
				// Remove do delay
				delayOperations.remove(operation);
				return true;
									
			//break;
			
			// Commit
			case 'c':
				
				// Verificar operacoes em delay da transacao
				for (Operacao op : delayOperations) {
					
					// Se encontrar alguma operacao da transacao em delay
					if(op.getTransacao() == operation.getTransacao()) {
						
						// Nada a fazer
						return false;
					}
				}
				
				// Se nao houver operacoes em delay
				this.finalHistory.add(operation.getOperacao());
				removeExclusiveLocksAfterCommit(operation);
				removeSharedLocksAfterCommit(operation);
				delayOperations.remove(operation);
				return true;

			//break;
				
			default:
				return false;
		}
	}
	
	private boolean analizeDelayedOperations() {
		
		for (Integer transactionNro : transactions) {
			for (Operacao op : delayOperations) {
				if(op.getTransacao() == transactionNro) {
					System.out.println("\nEncontrei " + op.getOperacao() + "\n");
					// Se retornar falso, vai verificar proxima transacao
					if(schedulerHandleDelayedOperation(op)) {
						System.out.println("\nA operacao " + op.getOperacao() + " foi removida do delay\n");
						return true;
					} else {
						break;
					}
				}				
			}
		}
		return false;
	}

	private void showDelayedOperations() {
		// Mostra operacoes em delay
		if(delayOperations.isEmpty()) {
			System.out.println("Lista de operacoes em delay: vazia\n");
		} else {
			System.out.print("Lista de operacoes em delay: ");
			for (Operacao op : delayOperations) {
				op.printOperation();	
			}
		}
	}

	private boolean verifyIfIsInDelay(String currentOperation) {

		// Para cada operacao em delay
		for (Operacao op : delayOperations) {
			// Se for a mesma transacao
			if(op.getTransacao() == currentOperation.charAt(1) -'0') {
				return true;
			}
		}
		return false;
	}

	private void removeSharedLocksAfterCommit(Operacao operation) {

		// Limpar array
		operationsToRemove.clear();		

		// Liberar bloqueios compartilhados
		for (Operacao op : sharedLock) {			
			if(op.getTransacao() == operation.getTransacao()) {
				// Evitar modification exception
				operationsToRemove.add(op);
			}
		}
		
		for (Operacao op : operationsToRemove) {
			// Remove do bloqueio compartilhado
			sharedLock.remove(op);
			// Adiciona remocao na historia final
			this.finalHistory.add("us" + op.getTransacao() + "[" + op.getData() + "]");
		}
	}

	private void removeExclusiveLocksAfterCommit(Operacao operation) {
		
		// Limpar array
		operationsToRemove.clear();		
		
		// Liberar bloqueios exclusivos
		for (Operacao op : exclusiveLock) {			
			if(op.getTransacao() == operation.getTransacao()) {
				
				// Evitar modification exception
				operationsToRemove.add(op);
			}
		}

		for (Operacao op : operationsToRemove) {
			// Remove do bloqueio exclusivo
			exclusiveLock.remove(op);
			// Adiciona remocao na historia final
			this.finalHistory.add("ux" + op.getTransacao() + "[" + op.getData() + "]");
		}
	}
	
	private void removeFromSharedLock(Operacao operation) {
		
		// Comparar transacao e o dado para efetuar remocao
		// Verifica todas as operacoes armazenadas em sharedLock
		for (Operacao op : sharedLock) {

			// Mesma transacao e mesmo dado
			if(op.getTransacao() == operation.getTransacao() && op.getData() == operation.getData()) {
				System.out.println("\nRemovi bloqueio compartilhado da transacao " + op.getTransacao() + " sobre o dado " + op.getData());
				sharedLock.remove(op);
				break;
			}
		}
	}

	// Retorna se ha bloqueio exclusivo para transacao especifica 
	// Recebe operacao a verificar
	private boolean verifyIfIsExclusiveLockedForATransaction(Operacao operation) {
		
		//System.out.println(operation.getData());
		
		// procura no array de operacoes que possuem bloqueio exclusivo
		for (Operacao op : exclusiveLock) {
			if(op.compareToUpgradeLock(operation) == 0) {
				return true;
			}
		}
		return false;
	}
	
	// Verifica se o dado possui bloqueio exclusivo por outra transacao
	private boolean verifyIfIsExclusiveLockedForOtherTransaction(Operacao operation) {
		
		for (Operacao op : exclusiveLock) {
			// Se o dado for o mesmo e a transacao for diferente
			if(op.getData() == operation.getData() && op.getTransacao() != operation.getTransacao()) {
				return true;
			}
		}
		return false;
	}
	
	// Retorna se ha bloqueio compartilhado para transacao especifica 
	// Recebe operacao a verificar
	private boolean verifyIfIsSharedLockedForATransaction(Operacao operation) {
			
		// procura no array de operacoes que possuem bloqueio compartilhado
		for (Operacao op : sharedLock) {
			if(op.compareToUpgradeLock(operation) == 0) {
				return true;
			}
		}
		return false;
	}
	
	// Verifica se o dado possui bloqueio compartilhado por outra transacao
	private boolean verifyIfIsSharedLockedForOtherTransaction(Operacao operation) {
		
		for (Operacao op : sharedLock) {
			// Se o dado for o mesmo e a transacao for diferente
			if(op.getData() == operation.getData() && op.getTransacao() != operation.getTransacao()) {
				return true;
			}
		}
		return false;
	}
	
	public void showFinalHistory() {
		
		for (String s : finalHistory) {
			System.out.print(s + " ");
		}
	}
}

