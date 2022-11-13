package services;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import models.Person;
import utils.MyDB_Connection;

/**
 *
 * @author dev
 */
//@Named
//@ApplicationScoped

@ManagedBean(name = "personservice")
@ApplicationScoped
public class PersonService  {
private  MyDB_Connection db=new MyDB_Connection();
 Connection con=null;
 public List<Person> getPersons() throws SQLException{
          List<Person> persons;
          
         
         try {
            persons=new ArrayList<>();
            con=db.getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from persons");
            while(rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("email");
                Person p=new Person();
                p.setId(id);
                p.setName(name);
                persons.add(p);
            }
            return persons;
            
        } catch (SQLException ex) {
           System.out.print(ex);
        }finally{
             con.close();
         }
      return null;
    }
public void create(Person person) throws SQLException{
    MyDB_Connection db=new MyDB_Connection();
    Connection con=null;
    try{
        con=db.getConnection();
        String query="insert into persons(email) values(?)";
        PreparedStatement ps=con.prepareStatement(query);
        ps.setString(1, person.getName());
        ps.execute();
    }catch(SQLException e){
        System.out.println(e);
    }finally{
             con.close();
         }
}

    public Person get(int id) throws SQLException {
    Person p=new Person();
    try {
        
        con=db.getConnection();
        String query="select * from persons where id = ?";
        PreparedStatement ps=con.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs=ps.executeQuery();
   
        while(rs.next()){
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("email"));
        }
            
    } catch (SQLException ex) {
        System.out.println(ex);
        
    }finally{
             con.close();
         }
        return p;
    }

    public void update(Person p) throws SQLException {
    try {
         
        con=db.getConnection();
        String query="update persons set email = ? where id =?";
        PreparedStatement ps=con.prepareStatement(query);
        ps.setString(1, p.getName());
        ps.setInt(2, p.getId());
        ps.executeUpdate();
         System.out.println(p);
    } catch (SQLException ex) {
        System.out.println(ex);
    }finally{
             con.close();
         }
    
    }

    public void delete(int id) throws SQLException {
        
    try {
       
        con=db.getConnection();
        String query="delete from persons where id=?";
        PreparedStatement ps=con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
        
    } catch (SQLException ex) {
        System.out.println(ex);    }
    finally{
             con.close();
         }
        
    }


}
