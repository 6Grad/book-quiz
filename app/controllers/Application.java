package controllers;

import play.*;
import play.data.validation.Email;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.i18n.Messages;
import play.mvc.*;
import play.mvc.Router.Route;

import java.util.*;

import org.apache.commons.lang.StringUtils;

import ch.sechsgrad.fb.FB;
import ch.sechsgrad.fb.FBException;
import ch.sechsgrad.fb.SignedRequest;

import models.Participant;
import net.sf.oval.constraint.NotEmpty;

public class Application extends Controller {

  public static void dispatch(String signed_request) throws FBException {

    if (signed_request != null) {
      SignedRequest sr = FB.getSignedRequest(signed_request);

      session.put("locale", sr.getLocale());

      if (sr.isAdmin()) {
        admin();
      }

      if (sr.liked()) {
        fan();
      } else {
        notfan();
      }

    }

    error("only to be used within a Facebook Page Tab");

  }

  public static void admin() {

    render();
  }

  public static void redirect() {
    render();
  }

  public static void participate(@Required @Email String mailAddress, @Required String userName) {
    if (validation.hasErrors()) {
      params.flash();
      validation.keep();
      fan();
    }

    /* store mailAddress */
    Participant p = Participant.find("byMailAddress", mailAddress).first();

    if (!validation.equals(p, null).ok) {
      params.flash();
      validation.addError("mailAddress", "duplicate");
      validation.keep();
      fan();
    }

    p = new Participant();
    p.mailAddress = mailAddress;
    p.name = userName;
    p.validateAndCreate();

    session.put("id", p.id);

    thanks();

  }

  /* Page 1 */
  public static void notfan() {
    render();
  }

  /* Page 2 */
  public static void fan() {
    render();
  }

  /* Page 3 */
  public static void thanks() {

    String locale = session.get("locale");
    if (StringUtils.isEmpty(locale)) {
      locale = SignedRequest.DEFAULT_LOCALE;
    }
    render(locale);
  }

  public static void export() {

    List<Participant> participants = Participant.all().fetch();

    String result = "";

    for (Participant p : participants) {
      result += p.name + "\t\t" + p.mailAddress + "\n";
    }

    renderText(result);

  }

}
