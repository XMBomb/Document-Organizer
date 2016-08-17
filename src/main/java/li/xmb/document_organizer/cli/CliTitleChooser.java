package li.xmb.document_organizer.cli;

import java.util.List;
import java.util.Scanner;

import li.xmb.document_organizer.beans.Tag;
import li.xmb.document_organizer.title.utils.TitleUtil;

public class CliTitleChooser {

	private static final int NUM_LIST_ITEMS = 10;
	private final Scanner scn = new Scanner(System.in);
	private final List<Tag> titles;
	private int offset = 0;

	public CliTitleChooser(final List<Tag> titles) {
		super();
		this.titles = titles;
	}
	
	public Tag decideTitle() {
		initInfo();

		final String choice = getNextChoice();
		final Tag bestTag = titles.get(Integer.valueOf(choice));

//		System.out.println("You chose (" + choice + "):" + TitleUtil.normalizeTitle(bestTag.getTag().text()));
		return bestTag;

	}
	
	private void initInfo(){
		System.out.println("Choose the title from the following list or press 'n' to show the next "+NUM_LIST_ITEMS+", go back with 'b'.\n"
				+ "_____________________________________________________________________________\n");
		getNextItems();
	}

	private String getNextChoice() {
		String choice = scn.next();
		switch (choice) {
			case "n":
				if (offset <= this.titles.size()){
					offset += NUM_LIST_ITEMS;
					getNextItems();
				}else{
					System.out.println("Can't go forward any further");
				}
				choice = getNextChoice();
				break;
			case "b":
				if (offset >= NUM_LIST_ITEMS){
					offset -= NUM_LIST_ITEMS;
					getNextItems();
				}else{
					System.out.println("Can't go back any further");
				}
				choice = getNextChoice();

				break;
		default:
				final int choiceNum;
				try{
					choiceNum = Integer.valueOf(choice);
					if ((choiceNum >= 0) && choiceNum < this.titles.size()){
						break;
					}
				}catch(final NumberFormatException nfe){}
				System.out.println("Please either a number or \"n\" or \"b\" ");
				choice = getNextChoice();
				break;
		}
		return choice;
	}

	private void getNextItems() {
		for (int i = offset; i < offset + NUM_LIST_ITEMS; i++) {
			try {
				final Tag tag = titles.get(i);
				System.out.println(i + ": " + TitleUtil.normalizeTitle(tag.getTag().text()) + " || confidence: " + tag.getConfidenceLevel());
			} catch (final IndexOutOfBoundsException e) {
			}
		}
	}

}
