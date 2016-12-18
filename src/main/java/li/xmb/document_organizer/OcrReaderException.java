package li.xmb.document_organizer;

public class OcrReaderException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6834746793577445587L;

	public OcrReaderException(final Exception e) {
		super(e);
	}
	
	public OcrReaderException(final String message) {
		super(message);
	}

	public OcrReaderException(final Exception e, final String message) {
		super(message, e);
	}
}
