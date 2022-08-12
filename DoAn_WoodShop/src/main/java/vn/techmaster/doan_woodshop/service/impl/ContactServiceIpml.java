package vn.techmaster.doan_woodshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import vn.techmaster.doan_woodshop.controller.anonymous.ContactController;
import vn.techmaster.doan_woodshop.entity.Feeback;
import vn.techmaster.doan_woodshop.entity.Post;
import vn.techmaster.doan_woodshop.exception.NotFoundException;
import vn.techmaster.doan_woodshop.repository.ContactRepository;
import vn.techmaster.doan_woodshop.service.ContactService;

import java.util.Optional;

@Component
public class ContactServiceIpml implements ContactService {

    @Autowired
    private ContactRepository contactRepository ;

    @Override
    public <S extends Feeback> S save(S entity) {
        return contactRepository.save(entity);
    }

    @Override
    public Page<Feeback> findAllByTitleContains(String keyword, Pageable pageable) {
        return contactRepository.findAllByTitleContains(keyword, pageable);
    }

    @Override
    public Optional<Feeback> findById(String id) {
        Optional<Feeback> feeback = contactRepository.findById(id);
        if (feeback.isPresent()) {
            return feeback;
        }
        throw new NotFoundException("Could not find any post with id " + id);
    }

    @Override
    public void delete(Feeback entity) {
        contactRepository.delete(entity);
    }
}
