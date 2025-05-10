package com.atomic.getTentor.service;

import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.TentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TentorService {

    @Autowired
    private TentorRepository tentorRepository;

    public List<Tentor> getAllTentors() {
        return tentorRepository.findAll();
    }
}
