package com.nativenavs.stamp.repository;

import com.nativenavs.stamp.entity.StampEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampRepository extends JpaRepository<StampEntity, Integer> {
}
