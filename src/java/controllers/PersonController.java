
package controllers;
import java.io.Serializable;
import java.sql.SQLException;
import models.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

import javax.enterprise.inject.spi.Bean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import services.PersonService;

/**
 *
 * @author dev
 */
//@Named
//@SessionScoped
@ManagedBean
@ViewScoped

public class PersonController implements Serializable{
    private int id;
    private String name;
    private List<Person> persons;
  @ManagedProperty(value = "#{personservice}")
    private PersonService personService;
  private Map<String,Object> sessionMap=FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    @PostConstruct
    public void init() {
        try {
            //        persons=new ArrayList<>();
//        persons.add(new Person(1, "Angela"));
//        persons.add(new Person(2, "Milo"));


persons=personService.getPersons();
        } catch (SQLException ex) {
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public PersonController() {
    }
    
//    public PersonController(int id, String name, List<Person> persons, PersonService personService) {
//        this.id = id;
//        this.name = name;
//        this.persons = persons;
//        this.personService = personService;
//    }
    
    

//    public PersonService getPersonService() {
//        return personService;
//    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }
    

    public List<Person> getPersons() {
        return persons;
    }
//
//    public void setPersons(List<Person> persons) {
//        this.persons = persons;
//    }
    



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String create() throws SQLException{
        Person p=new Person();
        p.setName(name);
        personService.create(p);
        return "index?faces-redirect=true";
    }
    public  String delete() throws SQLException{
        Map<String,String> params=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int id=Integer.parseInt(params.get("delete"));
        personService.delete(id);
        System.out.println("doene");
        return "index?faces-redirect=true";
    }
    public String update(Person p) throws SQLException{
        System.out.println("Available"+p);
//    Map<String,String> params=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//    String id=params.get("update");
   // Person p=new Person();
//    p.setId(Integer.parseInt(id));
//    p.setName(name);
//        System.out.println("youg"+this.id);
    personService.update(p);
     return "index?faces-redirect=true";
    }
       public  String edit() throws SQLException{
            System.out.println("methodss");
           FacesContext fc=FacesContext.getCurrentInstance();
           Map<String,String> params=fc.getExternalContext().getRequestParameterMap();
           int id=Integer.parseInt(params.get("edit1"));
          Person p=personService.get(id);
          
           sessionMap.put("editedperson", p);
        return "/index.xhtml?faces-redirect=true";
    }
//
//    public void onRowEdit(RowEditEvent event) {
//  FacesMessage msg = new FacesMessage("Car Edited", event.g);
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
//     
//    public void onRowCancel(RowEditEvent event) {
//       FacesMessage msg = new FacesMessage("Edit Cancelled", event.getObject().getId());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
//    
//public void onCellEdit(CellEditEvent event){
//     System.out.println("eafeadjkdfjdsakfkdnk");
//    Object oldValue=event.getOldValue();
//    Object newValue =event.getNewValue();
//    
//    if(newValue!=null && !newValue.equals(oldValue)){
//        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
//}
        public void onRowEdit(RowEditEvent event) throws SQLException {
        FacesMessage msg = new FacesMessage("Car Edited", event.getObject().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        personService.update((Person) event.getObject());
        
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    
}
