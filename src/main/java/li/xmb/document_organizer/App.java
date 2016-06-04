package li.xmb.document_organizer;

import java.util.ArrayList;

import li.xmb.document_organizer.config.Config;

public class App 
{
    public static void main( final String[] args )
    {
        final ArrayList<?> keyWords = Config.getDefault().getProperty("keywords", ArrayList.class);
        for(final Object str : keyWords){
        	System.out.println(str.getClass());
        }
    }
}
