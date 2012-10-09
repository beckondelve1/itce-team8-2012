package net.biyee.android.ONVIF;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Namespace(reference="http://www.onvif.org/ver10/schema")
@Root(strict=false)
public class Security
{

  @Element
  public Boolean AccessPolicyConfig;

  @Element
  public Boolean KerberosToken;

  @Element
  public Boolean OnboardKeyGeneration;

  @Element
  public Boolean RELToken;

  @Element
  public Boolean SAMLToken;

  @Element(name="TLS1.1")
  public Boolean TLS11;

  @Element(name="TLS1.2")
  public Boolean TLS12;

  @Element(name="X.509Token")
  public Boolean X509Token;
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.android.ONVIF.Security
 * JD-Core Version:    0.6.0
 */