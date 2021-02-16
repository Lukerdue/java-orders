package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "agentServices")
public class AgentServiceImpl implements AgentService{
    @Autowired
    private AgentsRepository agentrepo;

    @Override
    public Agent save(Agent agent) {
        return agentrepo.save(agent);
    }
}
