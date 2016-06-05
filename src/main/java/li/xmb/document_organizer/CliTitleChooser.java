package li.xmb.document_organizer;

import java.util.List;
import java.util.Scanner;

import li.xmb.document_organizer.utils.TitleUtil;

public class CliTitleChooser {

	private static final int NUM_LIST_ITEMS = 10;
	private final Scanner scn = new Scanner(System.in);
	private final List<Tag> bestTitles;
	private int offset = 0;

	public CliTitleChooser(final List<Tag> bestTitles) {
		super();
		this.bestTitles = bestTitles;
	}
	
	public Tag decideTitle() {
		initInfo();

		final String choice = getNextChoice();
		final Tag bestTag = bestTitles.get(Integer.valueOf(choice));

		System.out.println("You chose (" + choice + "):" + TitleUtil.normalizeTitle(bestTag.getTag().text()));
		return bestTag;

	}
	
	private void initInfo(){
		System.out.println("Choose the title from the following list or press 'n' to show the next "+NUM_LIST_ITEMS+", go back with 'b'.\n" + "_____________________________________________________________________________\n");
		getNextTopTen();
	}

	private String getNextChoice() {
		String choice = scn.next();
		switch (choice) {
			case "n":
				if (offset <= this.bestTitles.size()){
					offset += NUM_LIST_ITEMS;
					getNextTopTen();
				}else{
					System.out.println("Can't go forward any farther");
				}
				choice = getNextChoice();
				break;
			case "b":
				if (offset >= NUM_LIST_ITEMS){
					offset -= NUM_LIST_ITEMS;
					getNextTopTen();
				}else{
					System.out.println("Can't go back any further");
				}
				choice = getNextChoice();

				break;
		default:
				final int choiceNum;
				try{
					choiceNum = Integer.valueOf(choice);
					if ((choiceNum >= 0) && choiceNum < this.bestTitles.size()){
						break;
					}
				}catch(final NumberFormatException nfe){}
				System.out.println("Please enter something valid");
				choice = getNextChoice();
				break;
		}
		return choice;
	}

	private void getNextTopTen() {
		for (int i = offset; i < offset + NUM_LIST_ITEMS; i++) {
			try {
				final Tag tag = bestTitles.get(i);
				System.out.println(i + ": " + TitleUtil.normalizeTitle(tag.getTag().text()) + " || confidence: " + tag.getConfidenceLevel());
			} catch (final IndexOutOfBoundsException e) {
			}
		}
	}

}
