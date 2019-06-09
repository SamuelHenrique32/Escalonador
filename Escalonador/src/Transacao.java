import java.util.ArrayList;

public class Transacao {

	// para transacoes sem espaco
	private String[] transactionSplited;
	
	// Numero da transacao
	private int transactionNumber;

	// Operacoes que constituem a transacao
	private ArrayList<Operacao> operations;

	public Transacao() {
		super();
		this.transactionSplited = new String[Escalonador.getMaxTransactionLength()];
		this.transactionNumber = 0;
		this.operations = new ArrayList<Operacao>();
	}

	public ArrayList<Operacao> getOperations() {
		return operations;
	}

	public void criaTransacao(String currentTransactionRead, int numberOfTransactions) {
		
		// verificar e o final da transacao
		if(!currentTransactionRead.equalsIgnoreCase("-1")) {
			
			this.transactionNumber = numberOfTransactions;
			
			// corta no espaço e joga em vetor
			this.transactionSplited = currentTransactionRead.split(" ");	
			
			// percorre vetor cortado
			for(int i=0 ; i< this.transactionSplited.length ; i++) {
				
				// cria objeto e manda para array
				Operacao opToCreate = new Operacao(this.transactionSplited[i], this.transactionNumber, i);
				
				operations.add(opToCreate);

			}		
		}		
	}
	
	// Retorna operacao na posicao informada
	public Operacao getOperationAtPos(int pos) {
		
		for (Operacao op : operations) {
			if(op.getPosicaoNaTransacao() == pos) {
				return op;
			}
		}
		return null;
	}
	
	public void printTransacao() {
		
		System.out.println("Transacao numero: " + this.transactionNumber);
		System.out.println("Operacoes constituintes:\n");
		
		for (Operacao op : operations) {
			System.out.println("Operacao: " + op.getOperacao());
			System.out.println("Pertence à transacao: " + op.getTransacao());
			System.out.println("Posicao na transacao: " + op.getPosicaoNaTransacao());
			System.out.println();
		}
	}
}
