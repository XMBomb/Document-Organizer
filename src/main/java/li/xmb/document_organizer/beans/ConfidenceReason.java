package li.xmb.document_organizer.beans;

public class ConfidenceReason
{
	private String name;
	private int factor;
	public ConfidenceReason ( int factor, String name )
	{
		this.factor = factor;
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName ()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName ( String name )
	{
		this.name = name;
	}
	/**
	 * @return the factor
	 */
	public int getFactor ()
	{
		return factor;
	}
	/**
	 * @param factor the factor to set
	 */
	public void setFactor ( int factor )
	{
		this.factor = factor;
	}

}
