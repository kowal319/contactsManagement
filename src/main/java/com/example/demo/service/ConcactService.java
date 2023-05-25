package com.example.demo.service;

import com.example.demo.entity.Concact;

import java.util.List;


public interface ConcactService {
    List<Concact> findAllConcacts();
    Concact createConcact(Concact concact);
    String deleteConcact(Long concactId);

}
