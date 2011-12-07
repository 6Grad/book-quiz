package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.UniqueConstraint;

import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
public class Participant extends Model {
  
  @Required
  @Column(unique=true)
  public String mailAddress;

  @Required
  public String name;

}
  
  

