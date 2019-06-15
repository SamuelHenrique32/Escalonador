import java.nio.charset.Charset;

public class Operacao implements Comparable<Operacao>{

	private String operacao;
	// Transacao a que pertence
	private int transacao;
	private int posicaoNaTransacao;
	
	public Operacao(String operacao, int transacao, int posicaoNaTransacao) {
		super();
		this.operacao = operacao;
		this.transacao = transacao;
		this.posicaoNaTransacao = posicaoNaTransacao;
	}

	public String getOperacao() {
		return operacao;
	}
	
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	
	public int getTransacao() {
		return transacao;
	}
	
	public void setTransacao(int transacao) {
		this.transacao = transacao;
	}
	
	public int getPosicaoNaTransacao() {
		return posicaoNaTransacao;
	}
	
	public void setPosicaoNaTransacao(int posicaoNaTransacao) {
		this.posicaoNaTransacao = posicaoNaTransacao;
	}
	
	public char getData() {
		return this.operacao.charAt(3);
	}

	public void printOperation() {
		
		System.out.print(this.operacao + " ");
		//System.out.println("Transacao: " + this.transacao);
		//System.out.println("Posicao na Transacao:" + this.posicaoNaTransacao + "\n");		
	}

	@Override
	public int compareTo(Operacao outraOperacao) {
		
		System.out.println("Comparar " + this.getOperacao() + " com " + outraOperacao.getOperacao() + "\n");

		if(!this.getOperacao().equals(outraOperacao.getOperacao())){
			return -1;
		}
		
		if(this.getTransacao() != outraOperacao.getTransacao()) {
			return -1;
		}
		
//		if(this.getPosicaoNaTransacao() != outraOperacao.getPosicaoNaTransacao()) {
//			return -1;
//		}
		
		return 0;
	}
	
	// Utilizado para upgrade de transacoes em que necessita saber apenas se a transacao e o dado sao os mesmos
	public int compareToUpgradeLock(Operacao outraOperacao) {
		
		System.out.println("Comparar " + this.getOperacao() + " com " + outraOperacao.getOperacao() + "\n");

		if(this.getTransacao() != outraOperacao.getTransacao()) {
			return -1;
		}
		
		if(this.getData() != outraOperacao.getData()) {
			return -1;
		}
		
		return 0;
	}
}
