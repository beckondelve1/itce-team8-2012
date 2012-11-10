package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class JSoupTest {
	
	
	public static void main(String args[]) {
		String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
		Document doc = Jsoup.parse(html);
		Element link = doc.select("a").first();

		String text = doc.body().text(); // "An example link"
		String linkHref = link.attr("href"); // "http://example.com/"
		String linkText = link.text(); // "example""
		String linkOuterH = link.outerHtml();
		// "<a href="http://example.com"><b>example</b></a>"
		String linkInnerH = link.html(); // "<b>example</b>"

		System.out.println(text);
		System.out.println(linkHref);
		System.out.println(linkText);
		System.out.println(linkOuterH);
		System.out.println(linkInnerH);
	}
}
