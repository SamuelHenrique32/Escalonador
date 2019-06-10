import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Collections;

public class Historia {

	private ArrayList<Operacao> operations;
	//private ArrayList<Operacao> shuffledOperations;
	private Operacao[] shuffledOperations;
	
	// Para a historia sem espaços
	private String[] historySplited;	

	public Historia() {

		operations = new ArrayList<Operacao>();
		//shuffledOperations = new ArrayList<Operacao>();
		this.shuffledOperations = new Operacao[Escalonador.maxHistorySize];
		this.historySplited = new String[Escalonador.maxHistorySize];
	}

	// Criar quando informada historia na opcao 2 do menu
	public void createInitialHistory(String historyRead) {
		
		// String com split
		this.historySplited = historyRead.split(" ");
		
		// Posicoes preenchidas: historySplited.length-1
		for(int i=0 ; i< this.historySplited.length ; i++) {

			//para cada posicao, cria operacao e adiciona ao array de operacoes
			operations.add(new Operacao(this.historySplited[i], 0, 0));
			
		}
	}	

	// Metodo toString modificado
	public void printHistoria() {
	
		for (Operacao op : operations) {
			System.out.println("Operacao: " + op.getOperacao());
			//System.out.println("Transacao: " + op.getTransacao());
			//System.out.println("Posicao na transacao: " + op.getPosicaoNaTransacao());
		}
	}
	
	//Gera uma historia com base nas transacoes
	public void createExecutionSequence(ArrayList<Transacao> transactions) {
		
		// Limpar operacoes
		this.operations.clear();

		// Adicionar todas operacoes de todas as transacoes no ArrayList		
		for (Transacao t : transactions) {
			//t.printTransacao();
			for (Operacao op : t.getOperations()) {
				operations.add(op);
			}
		}
		
		System.out.println("\nFormei a historia:\n");
		for (Operacao op : operations) {
			op.printOperation();
		}
		
		// Sem considerar ordem das operacoes nas transacoes
		System.out.println("\n\nEmbaralhei a historia:\n");
		Collections.shuffle(operations);
		for (Operacao op : operations) {
			op.printOperation();
		}
		System.out.println();
		
		// Numero de transacoes recebidas por parametro
		int numberOfTransactions = transactions.size();
		
		// Posicao na transacao, utilizado para fazer a troca
		int posAtTransaction = 0;
		
		// Itera para todas as transacoes
		for(int i=0 ; i < numberOfTransactions ; i++) {
			// Recupera transacao na posicao do Array
			Transacao currentTransaction = transactions.get(i);
						
			//System.out.println("\n");
			//System.out.println("Posicoes das operacoes pertencentes a transacao " + i + "\n");
			// Itera em todas as operacoes da historia
			for (Operacao op : operations) {
				// Se a operacao pertencer a transacao atual
				if(op.getTransacao() == i) {
					
					// Mostra posicao da operacao na historia
					//System.out.print(operations.indexOf(op));
					
					// Mostra posicao na transacao que deve estar na posicao da historia
					//System.out.print(" - " + posAtTransaction + " - ");
					
					// Mostra operacao da transacao atual que deve ocupar posicao na historia
					//currentTransaction.getOperationAtPos(posAtTransaction).printOperation();	
					
					//System.out.print("\nTrocar ");
					//op.printOperation();
					//System.out.println(" por: ");
					//currentTransaction.getOperationAtPos(posAtTransaction).printOperation();
					
					shuffledOperations[operations.indexOf(op)] = currentTransaction.getOperationAtPos(posAtTransaction);
										
					posAtTransaction++;
				}
			}
			posAtTransaction = 0;
		}
		
		System.out.println("\n\nOrganizei a ordem das operacoes:\n");
		for(int i=0 ; i<operations.size() ; i++){
			shuffledOperations[i].printOperation();
		}
	}
}
