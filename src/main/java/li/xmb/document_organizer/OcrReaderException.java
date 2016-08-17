package li.xmb.document_organizer;

public class OcrReaderException extends RuntimeException{
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
