
package controllers;
import java.io.Serializable;
import models.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

import javax.enterprise.inject.spi.Bean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import javax.inject.Inject;
import javax.inject.Named;
import services.PersonService;

/**
 *
 * @author dev
 */
//@Named
//@SessionScoped
@ManagedBean
public class PersonController implements Serializable{
    private int id;
    private String name;
    private List<Person> persons;
  @ManagedProperty(value = "#{personservice}")
    private PersonService personService;
  private Map<String,Object> sessionMap=FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    @PostConstruct
    public void init(){
//        persons=new ArrayList<>();
//        persons.add(new Person(1, "Angela"));
//        persons.add(new Person(2, "Milo"));
        
 
         persons=personService.getPersons();
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
    
    public String create(){
        Person p=new Person();
        p.setName(name);
        personService.create(p);
        return "index?faces-redirect=true";
    }
    public  String delete(){
        Map<String,String> params=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int id=Integer.parseInt(params.get("delete"));
        personService.delete(id);
        return "index?faces-redirect=true";
    }
    public String update(Person p){
        System.out.println("Avail"+p);
    Map<String,String> params=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    String id=params.get("update");
   // Person p=new Person();
//    p.setId(Integer.parseInt(id));
//    p.setName(name);
//        System.out.println("youg"+this.id);
    personService.update(p);
     return "index?faces-redirect=true";
    }
       public  String edit(){
           
           FacesContext fc=FacesContext.getCurrentInstance();
           Map<String,String> params=fc.getExternalContext().getRequestParameterMap();
           int id=Integer.parseInt(params.get("edit1"));
          Person p=personService.get(id);
           System.out.println(p);
           sessionMap.put("editedperson", p);
        return "/edit.xhtml?faces-redirect=true";
    }



   
    
    
}
