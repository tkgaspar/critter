package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Entities.EmployeeRequest;
import com.udacity.jdnd.course3.critter.Repositories.EmployeeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeReqService {

    @Autowired
    EmployeeRequestRepository requestRepository;

    public Long saveRequest(EmployeeRequest request){
        return requestRepository.save(request).getId();
    }

    public List<EmployeeRequest> activeSchedules(){
        return requestRepository.findAll();
    }
}
