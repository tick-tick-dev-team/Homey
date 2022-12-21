package com.ticktack.homey.service.bgm;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface IBgmService {
    public Optional<Object> findById (String bmg_id);
}
