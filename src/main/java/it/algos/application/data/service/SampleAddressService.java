package it.algos.application.data.service;

import it.algos.application.data.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class SampleAddressService {

    private SampleAddressRepository repository;

    public SampleAddressService(@Autowired SampleAddressRepository repository) {
        this.repository = repository;
    }

    public Optional<SampleAddress> get(UUID id) {
        return repository.findById(id);
    }

    public SampleAddress update(SampleAddress entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<SampleAddress> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
