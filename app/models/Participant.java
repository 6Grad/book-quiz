package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
public class Participant extends Model {
  
  @Required
  public String mailAddress;

  @Required
  public String name;

}
  
  

