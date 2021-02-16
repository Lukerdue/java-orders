package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "agentServices")
public class AgentServiceImpl implements AgentService{
    @Autowired
    private AgentsRepository agentrepo;

    @Transactional
    @Override
    public Agent save(Agent agent) {
        return agentrepo.save(agent);
    }

    @Override
    public List<Agent> findAllAgents() {
        List<Agent> agentList = new ArrayList<>();
        agentrepo.findAll().iterator().forEachRemaining(agentList::add);
        return agentList;
    }

    @Override
    public Agent findById(long id) {
        Agent agent  = agentrepo.findById(id)
                .orElseThrow(()->new EntityNotFoundException("agent "+id+" not found"));
        return agent;
    }

    @Override
    public Agent findByName(String name) {
        Agent agent = agentrepo.findAgentByAgentname(name);
        if (agent == null)
        {
            throw new EntityNotFoundException("Agent "+ name+ " not found");
        }
        return agent;
    }
}
