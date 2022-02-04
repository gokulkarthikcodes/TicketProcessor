package com.sync.ticketprocessor.controller;

import com.sync.ticketprocessor.entity.Process;
import com.sync.ticketprocessor.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/process")
public class ProcessController {

    @Autowired
    ProcessService processService;

    @RequestMapping(value = "/getbyname/{processName}", method = RequestMethod.GET)
    public Process getProcessByName(@PathVariable String processName)    {
        return processService.getProcessByName(processName);
    }

    @RequestMapping(value = "/getbyid/{processId}", method = RequestMethod.GET)
    public Process getProcessById(@PathVariable Integer processId)    {
        return processService.getProcessById(processId);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Process> getAllProcess(){
       return processService.getAllProcess();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Process saveProcess(@RequestBody Process process)    {
        return processService.saveProcess(process);
    }

    @RequestMapping(value = "/getbycreatedby/{createdBy}", method = RequestMethod.GET)
    public List<Process> getProcessByCreatedBy(@PathVariable String createdBy)
    {
        return processService.getProcessByCreatedBy(createdBy);
    }


}
