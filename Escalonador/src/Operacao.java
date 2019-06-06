public class Operacao {

	private String operacao;
	private char transacao;
	private int posicaoNaTransacao;
	
	public Operacao(String operacao, char transacao, int posicaoNaTransacao) {
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
	
	public char getTransacao() {
		return transacao;
	}
	
	public void setTransacao(char transacao) {
		this.transacao = transacao;
	}
	
	public int getPosicaoNaTransacao() {
		return posicaoNaTransacao;
	}
	
	public void setPosicaoNaTransacao(int posicaoNaTransacao) {
		this.posicaoNaTransacao = posicaoNaTransacao;
	}
	
}
