package ch.sechsgrad.fb;

public class FB {

  public static SignedRequest getSignedRequest(String encrypted) throws FBException {
    return SignedRequest.newInstance(encrypted);
  }
}
