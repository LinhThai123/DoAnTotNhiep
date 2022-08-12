package vn.techmaster.doan_woodshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.techmaster.doan_woodshop.entity.Feeback;


import java.util.Optional;

@Service
public interface ContactService {

    <S extends Feeback> S save(S entity);

    Page<Feeback> findAllByTitleContains (String keyword, Pageable pageable);

    Optional<Feeback> findById(String id);

    void delete(Feeback entity);
}
