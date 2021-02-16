package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agents")
public class AgentController {

    @Autowired
    private AgentService agentservice;

    //http://localhose:2019/agents
    @GetMapping(value="/", produces = {"application/json"})
    public ResponseEntity<?> getAllAgents()
    {
        List<Agent> agentList = agentservice.findAllAgents();
        return new ResponseEntity<>(agentList, HttpStatus.OK);
    }

    //http://localhost:2019/agents/id/{id}
    @GetMapping(value="/id/{agentid}", produces={"application/json"})
    public ResponseEntity<?> findAgentById(@PathVariable long agentid)
    {
        Agent agent = agentservice.findById(agentid);
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }

    //http://localhost:2019/agents/name/{String name}
    @GetMapping(value="/name/{agentname}", produces = {"application/json"})
    public ResponseEntity<?> findAgentByName(@PathVariable String agentname)
    {
        Agent agent = agentservice.findByName(agentname);
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }


}
