package com.example.demo.service.Implementation;

import com.example.demo.entity.Concact;
import com.example.demo.repository.ConcactRepository;
import com.example.demo.service.ConcactService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcactServiceImplementation implements ConcactService {

    private ConcactRepository concactRepository;

    public ConcactServiceImplementation(ConcactRepository concactRepository) {
        this.concactRepository = concactRepository;
    }


    @Override
    public List<Concact> findAllConcacts() {
        List<Concact> concacts = concactRepository.findAll();
        return concacts;
    }

    @Override
    public Concact createConcact(Concact concact) {
return concactRepository.save(concact);
    }

    @Override
    public String deleteConcact(Long concactId) {
        if(concactRepository.findById(concactId).isPresent()){
            concactRepository.deleteById(concactId);
            return "Concact deleted sucessfuly";
        }
return "Concact not exist";
    }
}
