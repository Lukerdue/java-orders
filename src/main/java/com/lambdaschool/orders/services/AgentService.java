package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.models.Customer;

import java.util.List;

public interface AgentService {
    Agent save(Agent agent);
    List<Agent> findAllAgents();
    Agent findById(long id);
    Agent findByName(String name);
    void DeleteAllAgents();
    void deleteById(long id);
    void update(long id, Agent agent);
}
