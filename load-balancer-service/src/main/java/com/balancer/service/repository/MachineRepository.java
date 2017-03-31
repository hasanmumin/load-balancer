package com.balancer.service.repository;

import com.balancer.service.domain.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, String> {
    public Machine findByName(String name);

    public List<Machine> findByNameStartingWith(String name);
}
