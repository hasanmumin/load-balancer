package com.balancer.service.controller;

import com.balancer.service.domain.Machine;
import com.balancer.service.service.MachineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "machines")
public class MachineController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MachineController.class);

    @Autowired
    private MachineService machineService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Machine> findAll() {
        return machineService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Machine create(@RequestBody Machine machine) {
        return machineService.create(machine);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{oid}")
    public Machine delete(@PathVariable("oid") String oid) {
        return machineService.delete(oid);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{oid}")
    public Machine update(@RequestBody Machine machine, @PathVariable("oid") String oid) {
        return machineService.update(machine, oid);
    }

}
