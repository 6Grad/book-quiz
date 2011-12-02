package ch.sechsgrad.fb;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;

/**
 * http://developers.facebook.com/docs/authentication/signed_request/
 * 
 * @author Cédric Reginster <cedric.reginster@6grad.ch>
 * 
 */
public class SignedRequest {
  
  public static final String DEFAULT_LOCALE="de_DE";

  public User user;
  public String algorithm;
  public long issued_at;
  public long user_id;
  public String oauth_token;
  public long expires;
  public Page page;
  public long profile_id;

  // TODO
  // public String app_data;

  public static class User {
    public String country;
    public String locale;
    public Age age;

    public static class Age {
      public int min;
      public int max;
    }

  }

  public static class Page {
    public long id;
    public boolean liked;
    public boolean admin;
  }
  
  private SignedRequest() {}
  
  public boolean liked() {
    return page != null && page.liked;
  }
  
  public boolean isAdmin() {
    return page != null && page.admin;
  }
  
  public String getLocale() {
    if (user != null) {
      return user.locale;
    } else {
      return DEFAULT_LOCALE;
    }
  }
  
  
  
  public static SignedRequest newInstance(String encrypted) throws FBException {
    
    String[] tmp = encrypted.split("\\.");
    if (tmp == null || tmp.length != 2) {
      throw new FBException("invalid signed_request! " + encrypted);
    }
    
    checkSig(tmp[0]);
    String decoded = decode(tmp[1]);
    
    Gson g = new Gson();
    return g.fromJson(decoded, SignedRequest.class);
    
  }

  private static String decode(String encoded) throws FBException {

    String result = "";

    try {
      result = new String(Base64.decodeBase64(encoded), "utf-8");

    } catch (UnsupportedEncodingException e) {
      throw new FBException("could not decode base64 encoded json", e);
    }

    return result;

  }
  
  /**
   * TODO check signature
   * @param in
   * @return
   * @throws FBException
   */
  private static boolean checkSig(String in) throws FBException {
    return true;
  }

}
