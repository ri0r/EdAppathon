package parser;
public abstract class FeedParserFactory {
	static String feedUrl = "http://www.highways.gov.uk/rssfeed/rss.xml";
	
	public static FeedParser getParser(){
		return new AndroidSaxFeedParser(feedUrl);
	}
	
	// Called by getParser() which I changed to call AndroidSaxFeedParser as the default
//	public static FeedParser getParser(ParserType type){
//		switch (type){
//			case SAX:
//				return new SaxFeedParser(feedUrl);
//			case DOM:
//				return new DomFeedParser(feedUrl);
//			case ANDROID_SAX:
//				return new AndroidSaxFeedParser(feedUrl);
//			case XML_PULL:
//				return new XmlPullFeedParser(feedUrl);
//			default: return null;
//		}
//	}
}
