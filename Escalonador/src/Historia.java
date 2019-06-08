import java.util.ArrayList;

public class Historia {

	private ArrayList<Operacao> operacoes;
	
	public Historia() {

		operacoes = new ArrayList<Operacao>();
	}

	// Criar quando informada historia na opcao 2 do menu
	// Ordem na transacao nao e relevante
	protected void criarHistoriaInicial(ArrayList<String> history) {
		
		for (String s : history) {
			Operacao opAtual = new Operacao(s, s.charAt(1), 0);
			operacoes.add(opAtual);
		}
	}
	

	// metodo toString modificado
	public void printHistoria() {
	
		for (Operacao op : operacoes) {
			System.out.println("Operacao: " + op.getOperacao());
			System.out.println("Transacao: " + op.getTransacao());
			//System.out.println("Posicao na transacao: " + op.getPosicaoNaTransacao());
		}
	}
}
