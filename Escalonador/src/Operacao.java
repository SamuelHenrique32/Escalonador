public class Operacao {

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

	public void printOperation() {
		
		System.out.print(this.operacao + " ");
		//System.out.println("Transacao: " + this.transacao);
		//System.out.println("Posicao na Transacao:" + this.posicaoNaTransacao + "\n");		
	}
	
}
