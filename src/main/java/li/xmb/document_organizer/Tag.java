package li.xmb.document_organizer;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;

public class Tag implements Comparable<Tag>{
	private Element tag;
	private int confidenceLevel;
	private final Map<String, Integer> confidenceReasons = new HashMap<>();

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

	public void increaseConfidenceLevel(final int amount, final String propertyName) {
		this.confidenceLevel += amount;
		addConfidenceReason(propertyName, amount);
	}

	public void setConfidenceLevel(final int confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}

	public Map<String, Integer> getConfidenceReasons() {
		return confidenceReasons;
	}
	
	public void addConfidenceReason(final String name, final int value){
		this.confidenceReasons.put(name, value);
	}

	@Override
	public int compareTo(final Tag tag) {
		final int compareConfidenceLevel = tag.getConfidenceLevel();

		return compareConfidenceLevel - getConfidenceLevel();
	}
	
	@Override
	public String toString(){
		return getTag().text()+";"+getConfidenceLevel();
	}

}