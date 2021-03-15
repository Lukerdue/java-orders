package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.AgentsRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "agentServices")
public class AgentServiceImpl implements AgentService{
    @Autowired
    private AgentsRepository agentrepo;

    @Autowired
    private PaymentRepository payrepo;

    @Transactional
    @Override
    public Agent save(Agent tempagent)
    {
        Agent newAgent = new Agent();

        //put or post
        if(tempagent.getAgentcode() != 0){
            //put
            agentrepo.findById(tempagent.getAgentcode())
                    .orElseThrow(()-> new EntityNotFoundException("Agent " + tempagent.getAgentcode() + " was not found"));
            newAgent.setAgentcode(tempagent.getAgentcode());
        }
        newAgent.setAgentname(tempagent.getAgentname());
        newAgent.setCommission(tempagent.getCommission());
        newAgent.setCountry(tempagent.getCountry());
        newAgent.setPhone(tempagent.getPhone());
        newAgent.setWorkingarea(tempagent.getWorkingarea());

        for(Customer c:tempagent.getCustomers()){
            Customer tempCustomer = new Customer();
            tempCustomer.setCustname(c.getCustname());
            tempCustomer.setCustcity(c.getCustcity());
            tempCustomer.setCustcountry(c.getCustcountry());
            tempCustomer.setGrade(c.getGrade());
            tempCustomer.setOpeningamt(c.getOpeningamt());
            tempCustomer.setOutstandingamt(c.getOutstandingamt());
            tempCustomer.setPhone(c.getPhone());
            tempCustomer.setReceiveamt(c.getReceiveamt());

            for(Order o:c.getOrders()){
                Order tempOrder = new Order();
                tempOrder.setAdvanceamount(o.getAdvanceamount());
                tempOrder.setOrdamount(o.getOrdamount());
                tempOrder.setOrderdescription(o.getOrderdescription());

                for(Payment p:o.getPayments()){
                    Payment pay = payrepo.findById(p.getPaymentid())
                            .orElseThrow(()->new EntityNotFoundException("Could not find payment" + p.getPaymentid()));
                    o.getPayments().add(pay);
                }

                tempOrder.setCustomer(c);
                c.getOrders().add(o);
            }

            tempCustomer.setAgent(newAgent);
        }

        return agentrepo.save(newAgent);
    }

    @Transactional
    @Override
    public List<Agent> findAllAgents() {
        List<Agent> agentList = new ArrayList<>();
        agentrepo.findAll().iterator().forEachRemaining(agentList::add);
        return agentList;
    }

    @Transactional
    @Override
    public Agent findById(long id) {
        Agent agent  = agentrepo.findById(id)
                .orElseThrow(()->new EntityNotFoundException("agent "+id+" not found"));
        return agent;
    }

    @Transactional
    @Override
    public Agent findByName(String name) {
        Agent agent = agentrepo.findAgentByAgentname(name);
        if (agent == null)
        {
            throw new EntityNotFoundException("Agent "+ name+ " not found");
        }
        return agent;
    }

    @Transactional
    @Override
    public void DeleteAllAgents() {
        agentrepo.deleteAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        if(agentrepo.findById(id).isPresent())
        {
            agentrepo.deleteById(id);
        }
        else{
            throw new EntityNotFoundException("Agent " + id +" was not found");
        }
    }

    @Override
    public void update(long id, Agent agent) {
        return;
    }
}
