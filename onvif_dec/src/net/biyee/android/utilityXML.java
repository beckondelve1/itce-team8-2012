package net.biyee.android;

import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class utilityXML
{
  public static Boolean getBooleanByTagNameNS(Element paramElement, String paramString1, String paramString2)
  {
    Boolean localBoolean = null;
    if (paramElement != null)
    {
      NodeList localNodeList = paramElement.getElementsByTagNameNS(paramString1, paramString2);
      if (localNodeList.getLength() == 1)
        localBoolean = Boolean.valueOf(Boolean.parseBoolean(((Element)localNodeList.item(0)).getTextContent()));
    }
    return localBoolean;
  }

  public static Integer getIntByTagNameNS(Element paramElement, String paramString1, String paramString2)
  {
    Integer localInteger = null;
    if (paramElement != null)
    {
      NodeList localNodeList = paramElement.getElementsByTagNameNS(paramString1, paramString2);
      if (localNodeList.getLength() == 1)
        localInteger = Integer.valueOf(Integer.parseInt(((Element)localNodeList.item(0)).getTextContent()));
    }
    return localInteger;
  }

  public static String getStringByTagNameNS(Element paramElement, String paramString1, String paramString2)
  {
    String str = "";
    if (paramElement != null)
    {
      NodeList localNodeList = paramElement.getElementsByTagNameNS(paramString1, paramString2);
      if (localNodeList.getLength() == 1)
        str = ((Element)localNodeList.item(0)).getTextContent();
    }
    return str;
  }

  public static String nodeToString(Node paramNode)
  {
    StringWriter localStringWriter = new StringWriter();
    try
    {
      Transformer localTransformer = TransformerFactory.newInstance().newTransformer();
      localTransformer.setOutputProperty("omit-xml-declaration", "yes");
      localTransformer.transform(new DOMSource(paramNode), new StreamResult(localStringWriter));
      label43: return localStringWriter.toString();
    }
    catch (TransformerException localTransformerException)
    {
      break label43;
    }
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.utilityXML
 * JD-Core Version:    0.6.0
 */