package com.ticktack.homey.repository.bgm;
import java.util.Optional;
public interface IBgmRepo {
    Optional<Object> findById(int id);
}
