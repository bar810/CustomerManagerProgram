package utils.SQLQueries;

import entities.Customer;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import static model.GlobalProperties._logger;
import static utils.Utils.getCustomerSession;
import static utils.Utils.isNotNullOrEmpty;

/**
 * @author bbrownsh
 * @since 12/24/2018
 */
public class SQLQueriesAgainstCustomer {
    public static int insertCustomerToDB(Customer customer) {
        _logger.debug("Inserting Customer to DB: "+customer.toString());
        Session customerSession= getCustomerSession();
        Integer id = -1;
        try {
            customerSession.beginTransaction();
            id=(Integer)customerSession.save(customer);
            customerSession.getTransaction().commit();

        }catch(Exception e){
            _logger.error("Exception thrown while trying to insert customer: "+e.toString());
            return -1;
        }
        System.out.println("Customer inserted!");
        _logger.debug("Customer inserted successfully to DB. ID: "+customer.getCustomerID());
        return customer.getCustomerID();
    }
    public static int insertRangeOfCustomersToDB(List<Customer> customers) {
        _logger.debug("Inserting range of Customers to DB. size: "+customers.size());
        int counter=0;
        for(Customer c: customers){
            int retVal=insertCustomerToDB(c);
            if(retVal!=-1){
                counter++;
            }
        }
        if(counter==customers.size()){
            _logger.debug("All the customers are inserted to DB successfully");
        }else{
            _logger.warning("not all the customers are inserted to DB successfully");
        }
        return counter;
    }
    public static Customer getCustomerByID(int id){
        _logger.debug("getting Customer from DB by ID: "+id);
        Session customerSession= getCustomerSession();
        Customer customer=null;
        try {
            customerSession.beginTransaction();
            customer=customerSession.get(Customer.class,id);
            customerSession.getTransaction().commit();

        }catch(Exception e){
            _logger.error("Exception while getting customer by ID: "+e.toString());
            return null;
        }
        if(customer==null){
            _logger.warning("Customer not found ! ID: "+id);
        }
        else{
            _logger.debug("Customer getting succeeded !");
        }
        return customer;
    }
    public static List<Customer> getAllCustomersFromDB(){
        _logger.debug("Getting all customers from DB");
        Session customerSession= getCustomerSession();
        List customers=new ArrayList<Customer>();
        try {
            customerSession.beginTransaction();
            customers=customerSession.createQuery("from Customer").getResultList();
            customerSession.getTransaction().commit();
        }catch(Exception e){
            _logger.error("Exception while trying to get all customers from DB");
            return null;
        }
        _logger.debug("Getting all customers query succeeded. customers founds: "+customers.size());
        return customers;
    }
    public static List<Customer> getAllCustomersFromDBWithConditions(String ID,String firstName,String lastName,String mail,String phone){
        Session customerSession= getCustomerSession();
        boolean putFirstPrefix=false;
        String query="from Customer";
        if(isNotNullOrEmpty(ID)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.customerID='"+ID+"'";
        }
        if(isNotNullOrEmpty(firstName)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.firstName='"+firstName+"'";
        }
        if(isNotNullOrEmpty(lastName)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.lastName='"+lastName+"'";
        }
        if(isNotNullOrEmpty(mail)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.mailAddress='"+mail+"'";
        }
        if(isNotNullOrEmpty(phone)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.phoneNumber='"+phone+"'";
        }
        _logger.debug("Getting all customers from DB with condition. query: "+query);
        List customers=new ArrayList<Customer>();
        try {
            customerSession.beginTransaction();
            customers=customerSession.createQuery(query).getResultList();
            customerSession.getTransaction().commit();

        }catch(Exception e){
            _logger.error("Exception while trying to get all customers from DB with conditions");
            return null;
        }
        _logger.debug("Getting all customers with condition query succeeded. customers founds: "+customers.size());
        return customers;
    }
    public static void updateCustomerFirstName(int customerID,String newFirstName){
        _logger.debug("update customer first name. Customer ID: "+customerID+". new name: "+newFirstName);
        Session customerSession= getCustomerSession();
        customerSession.beginTransaction();
        Customer customer=customerSession.get(Customer.class,customerID);
        if(customer==null){
            _logger.warning("Customer not found");
        }else{
            customer.setFirstName(newFirstName);
            customerSession.getTransaction().commit();
            _logger.debug("Customer updated successfully. customer: "+customer.toString());
        }
    }
    public static void updateCustomerLastName(int customerID,String newLastName){
        _logger.debug("update customer last name. Customer ID: "+customerID+". new name: "+newLastName);
        Session customerSession= getCustomerSession();
        customerSession.beginTransaction();
        Customer customer=customerSession.get(Customer.class,customerID);
        if(customer==null){
            _logger.warning("Customer not found");
        }else{
            customer.setLastName(newLastName);
            customerSession.getTransaction().commit();
            _logger.debug("Customer updated successfully. customer: "+customer.toString());
        }
    }
    public static void updateCustomerPhone(int customerID,String newPhone){
        _logger.debug("update customer phone number. Customer ID: "+customerID+". new name: "+newPhone);
        Session customerSession= getCustomerSession();
        customerSession.beginTransaction();
        Customer customer=customerSession.get(Customer.class,customerID);
        if(customer==null){
            _logger.warning("Customer not found");
        }else{
            customer.setPhoneNumber(newPhone);
            customerSession.getTransaction().commit();
            _logger.debug("Customer updated successfully. customer: "+customer.toString());
        }
    }
    public static void updateCustomerMail(int customerID,String newMail){
        _logger.debug("update customer mail. Customer ID: "+customerID+". new name: "+newMail);
        Session customerSession= getCustomerSession();
        customerSession.beginTransaction();
        Customer customer=customerSession.get(Customer.class,customerID);
        if(customer==null){
            _logger.warning("Customer not found");
        }else{
            customer.setMailAddress(newMail);
            customerSession.getTransaction().commit();
            _logger.debug("Customer updated successfully. customer: "+customer.toString());
        }
    }
    public static void removeOneCustomer(int customerID){
        _logger.debug("removing customer. ID: "+customerID);
        Session customerSession= getCustomerSession();
        customerSession.beginTransaction();
        Customer customer=customerSession.get(Customer.class,customerID);
        if(customer==null){
            _logger.warning("Customer not found");
        }else{
            customerSession.delete(customer);
            customerSession.getTransaction().commit();
            _logger.debug("Customer removed successfully");
        }
    }
    public static void removeAllCustomerTable(){
        _logger.debug("Removing all customers from DB");
        List<Customer> list=getAllCustomersFromDB();
        for(Customer c: list){
            removeOneCustomer(c.getCustomerID());
        }
        _logger.debug("Removing all customers succeeded");
    }
}
