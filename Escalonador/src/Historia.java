import java.util.ArrayList;

public class Historia {

	private ArrayList<Operacao> operations;
	
	// Para a historia sem espaços
	private String[] historySplited;	

	public Historia() {

		operations = new ArrayList<Operacao>();
		this.historySplited = new String[Escalonador.maxHistorySize];
	}

	// Criar quando informada historia na opcao 2 do menu
	protected void criarHistoriaInicial(String historyRead) {
		
		// String com split
		this.historySplited = historyRead.split(" ");
		
		// Posicoes preenchidas: historySplited.length-1
		for(int i=0 ; i< this.historySplited.length ; i++) {

			//para cada posicao, cria operacao e adiciona ao array de operacoes
			operations.add(new Operacao(this.historySplited[i], 0, 0));
			
		}
	}
	

	// metodo toString modificado
	public void printHistoria() {
	
		for (Operacao op : operations) {
			System.out.println("Operacao: " + op.getOperacao());
			//System.out.println("Transacao: " + op.getTransacao());
			//System.out.println("Posicao na transacao: " + op.getPosicaoNaTransacao());
		}
	}
}
