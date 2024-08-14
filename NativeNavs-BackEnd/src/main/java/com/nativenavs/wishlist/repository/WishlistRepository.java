package com.nativenavs.wishlist.repository;

import com.nativenavs.wishlist.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface WishlistRepository extends JpaRepository<WishlistEntity,Integer> {

    List<WishlistEntity> findByUserId(int userId);

    WishlistEntity findByUserIdAndTourId(int userId, int tourId);

    boolean existsByUserIdAndTourId(int userId, int tourId);

    int countByTourId(int tourId);
}
