package li.xmb.document_organizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class KeywordParser {
	private static final String FOLDER_NAME_ATTRIBUTE = "folder-name";
	private static final String CATEGORY_NODE = "category";
	private static final Object FOLDER_NODE = "folder";
	private static final Object KEYWORD_NODE = "keyword";

	public void parseFile(final Path file) {
		final File configGile = new File("conf//folder-config.xml");
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			final Document doc = dBuilder.parse(configGile);
			doc.getDocumentElement().normalize();
			final NodeList categories = doc.getElementsByTagName(CATEGORY_NODE);
			for (int i = 0; i < categories.getLength(); i++) {
				final Node category = categories.item(i);
				final String categoryFolderName = category.getAttributes().getNamedItem(FOLDER_NAME_ATTRIBUTE).getNodeValue();
				createFolder(categoryFolderName, 0);
				recurseThroughFolders(category, 1);
			}


		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void recurseThroughFolders(final Node node, int depth) {
		// use iterator-based for loop, as for each is not supported by Nodes
		for (int j = 0; j < node.getChildNodes().getLength(); j++) {
			final Node folderNodeChild = node.getChildNodes().item(j);
			
			if(folderNodeChild.hasAttributes()){
				final String folderName = folderNodeChild.getAttributes().getNamedItem(FOLDER_NAME_ATTRIBUTE).getNodeValue();
				if (folderName != null){
					createFolder(folderName, depth);
				}
			}
			

			if (folderNodeChild.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			if (folderNodeChild.getNodeName().equals(KEYWORD_NODE)) {
				checkKeyWord(folderNodeChild.getFirstChild().getNodeValue());
			}

			if (folderNodeChild.getNodeName().equals(FOLDER_NODE)) {
				recurseThroughFolders(folderNodeChild, ++depth);
			}

		}
	}

	private void createFolder(final String folderName, final int depth){
		System.out.println(depth+" folder:"+folderName);
	}
	
	private void checkKeyWord(final String keyWord){
		System.out.println("keyword: "+keyWord);
	}

}
