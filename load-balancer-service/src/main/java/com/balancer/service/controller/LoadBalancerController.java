package com.balancer.service.controller;

import com.balancer.service.domain.Machine;
import com.balancer.service.model.json.response.ResponseMessage;
import com.balancer.service.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "balances")
public class LoadBalancerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MachineService machineService;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<ResponseMessage> router(@RequestBody List<String> machines) {

        List<ResponseMessage> responseMessages = new ArrayList<>();

        machines.forEach(machine -> {
            Machine triggerMachine = machineService.findByName(machine);

            if (triggerMachine != null) {
                trigger(triggerMachine, responseMessages);
            } else {
                responseMessages.add(new ResponseMessage(machine, "No match with any Machine", ResponseMessage.Status.FAILURE));
            }
        });

        return responseMessages;

    }


    @RequestMapping(method = RequestMethod.POST, value = {"{startingWith}"})
    public List<ResponseMessage> router(@PathVariable("startingWith") String startingWith) {

        List<ResponseMessage> responseMessages = new ArrayList<>();

        List<Machine> machines = machineService.findByNameStartingWith(startingWith);

        machines.forEach(machine -> {
            trigger(machine, responseMessages);
        });
        return responseMessages;
    }


    private void trigger(Machine triggerMachine, List<ResponseMessage> responseMessages) {
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(triggerMachine.getName());
            restTemplate.exchange(triggerMachine.getUrl(), HttpMethod.POST,
                    requestEntity, Void.class);
            responseMessages.add(new ResponseMessage(triggerMachine.getName(), ""));
        } catch (Exception e) {
            responseMessages.add(new ResponseMessage(triggerMachine.getName(), e.getMessage(), ResponseMessage.Status.FAILURE));
        }
    }

}
