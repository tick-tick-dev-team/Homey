package com.ticktack.homey.repository.bgm;

import java.util.Optional;

import javax.persistence.EntityManager;

public class BgmRepository implements IBgmRepo {

    private final EntityManager em;

    public BgmRepository(EntityManager em){
        super();
        this.em = em;
    }

    @Override
    public Optional<Object> findById(int id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }
    
}
