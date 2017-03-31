package com.balancer.service.service;

import com.balancer.service.domain.Machine;
import com.balancer.service.repository.MachineRepository;
import com.balancer.service.service.base.JpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService extends JpaService<Machine, String> {

    private MachineRepository repository;

    @Autowired
    public MachineService(MachineRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Machine findByName(String name) {
        return repository.findByName(name);
    }

    public List<Machine> findByNameStartingWith(String name) {
        return repository.findByNameStartingWith(name);
    }

}
