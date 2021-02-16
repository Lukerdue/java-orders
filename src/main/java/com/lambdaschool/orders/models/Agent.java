package com.lambdaschool.orders.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="agents")
public class Agent
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long agentcode;

    @Column(nullable=false)
    private String agentname;
    private double commission;
    private String country;

    @Column(unique = true)
    private String phone;
    private String workingarea;

    @OneToMany(mappedBy = "agent", cascade= CascadeType.ALL, orphanRemoval = true)
    List<Customer> customers = new ArrayList<>();

    public Agent() {
    }

    public Agent(long agentcode, String agentname, double commission, String country, String phone, String workingarea) {
        this.agentcode = agentcode;
        this.agentname = agentname;
        this.commission = commission;
        this.country = country;
        this.phone = phone;
        this.workingarea = workingarea;
    }

    public Agent(String agentname, String country , double commission, String phone, String workingarea) {

        this.agentname = agentname;
        this.commission = commission;
        this.country = country;
        this.phone = phone;
        this.workingarea = workingarea;
    }

    public long getAgentcode() {
        return agentcode;
    }

    public void setAgentcode(long agentcode) {
        this.agentcode = agentcode;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkingarea() {
        return workingarea;
    }

    public void setWorkingarea(String workingarea) {
        this.workingarea = workingarea;
    }
}
