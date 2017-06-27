package elezioni;

public class CandidatoNonValido extends Exception {
	private static final long serialVersionUID = 1L;

	public CandidatoNonValido() {
		super();
	}

	public CandidatoNonValido(String msg) {
		super(msg);
	}
}
