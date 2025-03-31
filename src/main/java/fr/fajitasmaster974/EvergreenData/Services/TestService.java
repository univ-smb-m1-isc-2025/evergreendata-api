package fr.fajitasmaster974.EvergreenData.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.fajitasmaster974.EvergreenData.Entities.Test;
import fr.fajitasmaster974.EvergreenData.Repositories.TestRepository;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    public Test saveTest(Test test) {
        return testRepository.save(test);
    }
}