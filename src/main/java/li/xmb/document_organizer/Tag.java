package li.xmb.document_organizer;

import org.jsoup.nodes.Element;

public class Tag implements Comparable<Tag>{
	private Element tag;
	private int confidenceLevel;

	public Tag(final Element tag) {
		super();
		this.tag = tag;
	}

	public Element getTag() {
		return tag;
	}

	public void setTag(final Element tag) {
		this.tag = tag;
	}

	public int getConfidenceLevel() {
		return confidenceLevel;
	}

	public void increaseConfidenceLevel(final int amount) {
		this.confidenceLevel += amount;
	}

	public void setConfidenceLevel(final int confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}

	@Override
	public int compareTo(final Tag tag) {
		final int compareConfidenceLevel = tag.getConfidenceLevel();

		return compareConfidenceLevel - getConfidenceLevel();
	}

}