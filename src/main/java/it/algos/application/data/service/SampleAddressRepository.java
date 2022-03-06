package it.algos.application.data.service;

import com.vaadin.flow.spring.annotation.*;
import it.algos.application.data.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

@SpringComponent
public interface SampleAddressRepository extends JpaRepository<SampleAddress, UUID> {

}