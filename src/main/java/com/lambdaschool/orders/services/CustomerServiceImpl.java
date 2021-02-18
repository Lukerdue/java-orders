package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.AgentsRepository;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value="customerServices")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomersRepository custrepo;
    @Autowired
    PaymentRepository payrepo;
    @Autowired
    AgentsRepository agentrepo;

    @Transactional
    @Override
    public Customer save(Customer tempcustomer) {
        Customer newcust = new Customer();

        //put or post?
        if(tempcustomer.getCustcode() != 0){
            custrepo.findById(tempcustomer.getCustcode())
                    .orElseThrow(()-> new EntityNotFoundException("Customer with id: "+ tempcustomer.getCustcode()+" was not found"));
            newcust.setCustcode(tempcustomer.getCustcode());
        }

        newcust.setReceiveamt(tempcustomer.getReceiveamt());
        newcust.setPhone(tempcustomer.getPhone());
        newcust.setOutstandingamt(tempcustomer.getOutstandingamt());
        newcust.setOpeningamt(tempcustomer.getOpeningamt());
        newcust.setGrade(tempcustomer.getGrade());
        newcust.setCustcountry(tempcustomer.getCustcountry());
        newcust.setCustcity(tempcustomer.getCustcity());
        newcust.setCustname(tempcustomer.getCustname());
        newcust.setWorkingarea(tempcustomer.getWorkingarea());
        newcust.setPaymentamt(tempcustomer.getPaymentamt());

        for(Order o: tempcustomer.getOrders()){
            Order temporder = new Order();
            temporder.setOrderdescription(o.getOrderdescription());
            temporder.setOrdamount(o.getOrdamount());
            temporder.setAdvanceamount(o.getAdvanceamount());

            for(Payment p: o.getPayments()){
                Payment pay = payrepo.findById(p.getPaymentid())
                        .orElseThrow(()->new EntityNotFoundException("payment with id: "+p.getPaymentid()+"not found"));
                temporder.getPayments().add(pay);
            }
            temporder.setCustomer(newcust);
            newcust.getOrders().add(temporder);
        }
        if(agentrepo.findById(tempcustomer.getAgent().getAgentcode()).isPresent()){
            Agent agent = agentrepo.findById(tempcustomer.getAgent().getAgentcode())
                    .orElseThrow(()->new EntityNotFoundException("agent "+tempcustomer.getAgent().getAgentcode()+" was not found and could not be added"));
            newcust.setAgent(agent);
        }


        return custrepo.save(newcust);
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> custList = new ArrayList<>();
        custrepo.findAll().iterator().forEachRemaining(custList::add);
        return custList;
    }

    @Override
    public Customer getCustomerById(long id) {
        Customer customer = custrepo.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Customer with id: "+id+" not found"));
        return customer;
    }

    @Override
    public List<Customer> custNameLike(String name) {
        List<Customer> customers = custrepo.findCustomersByCustnameContainingIgnoreCase(name);
        return customers;
    }

    @Override
    public void deleteCustomer(long custcode) {
        custrepo.deleteById(custcode);
    }

    @Override
    public Customer updateCustomer(long custcode, Customer customer) {
        Customer updateCust = custrepo.findById(custcode)
                .orElseThrow(()-> new EntityNotFoundException("Customer "+custcode+" not found"));


        if(customer.hasvaluereceiveamt) {updateCust.setReceiveamt(customer.getReceiveamt());}
        if(customer.getPhone() != null) {updateCust.setPhone(customer.getPhone());}
        if(customer.hasvalueoutstandingamt) {updateCust.setOutstandingamt(customer.getOutstandingamt());}
        if(customer.hasvalueopeningamt){updateCust.setOpeningamt(customer.getOpeningamt());}
        if(customer.getGrade() != null) {updateCust.setGrade(customer.getGrade());}
        if(customer.getCustcountry() != null) {updateCust.setCustcountry(customer.getCustcountry());}
        if(customer.getCustcity() != null){updateCust.setCustcity(customer.getCustcity());}
        if(customer.getCustname() != null){updateCust.setCustname(customer.getCustname());}
        if(customer.getAgent() != null ){updateCust.setAgent(customer.getAgent());}
        if(customer.getWorkingarea() != null){updateCust.setWorkingarea(customer.getWorkingarea());}
        if(customer.hasvaluepaymentamt){updateCust.setPaymentamt(customer.getPaymentamt());}

        if(customer.getOrders().size() > 0) {
            updateCust.getOrders().clear();
            for (Order o : customer.getOrders()) {
                Order temporder = new Order();
                temporder.setCustomer(updateCust);
                temporder.setOrderdescription(o.getOrderdescription());
                temporder.setOrdamount(o.getOrdamount());
                temporder.setAdvanceamount(o.getAdvanceamount());

                for (Payment p : o.getPayments()) {
                    Payment pay = payrepo.findById(p.getPaymentid())
                            .orElseThrow(() -> new EntityNotFoundException("payment with id: " + p.getPaymentid() + "not found"));
                    o.getPayments().add(pay);
                }
            }
        }
        return updateCust;
    }
}
