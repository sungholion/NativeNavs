package com.nativenavs.wishlist.service;

import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.tour.repository.TourRepository;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
import com.nativenavs.wishlist.entity.WishlistEntity;
import com.nativenavs.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;

    public void addWishlist(int userId, int tourId){
        TourEntity tour = tourRepository.findById(tourId).orElseThrow(()->new IllegalArgumentException("Tour not found"));
        UserEntity user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("User not found"));
        boolean isWished = wishlistRepository.existsByUserIdAndTourId(userId, tourId);
        if(isWished){
           throw new IllegalArgumentException("Tour is already in wishlist");
        }

        WishlistEntity wishlistEntity = new WishlistEntity(user,tour);
        wishlistRepository.save(wishlistEntity);
    }



    public List<TourDTO> findWishlist(int userId){
        List<WishlistEntity> wishes = wishlistRepository.findByUserId(userId);

        return wishes.stream()
                .map(wishlistEntity -> {
                    TourEntity tourEntity = tourRepository.findById(wishlistEntity.getTour().getId()).orElse(null);
                    UserEntity userEntity = userRepository.findById(userId)
                            .orElseThrow(() -> new NoSuchElementException("User not found"));
                    // UserDTO로 변환
                    UserDTO userDTO = UserDTO.toUserDTO(userEntity);
                    return tourEntity != null ? TourDTO.toTourDTO(tourEntity,userDTO) : null;
                }).filter(tourDTO -> tourDTO != null) // null을 필터링
                .collect(Collectors.toList());
    }

    public boolean checkIsWishlist(int userId, int tourId) {
        return wishlistRepository.existsByUserIdAndTourId(userId, tourId);
    }

    public void removeWishlist(int userId,int tourId){
        WishlistEntity wish = wishlistRepository.findByUserIdAndTourId(userId, tourId);
        if (wish != null) {
            wishlistRepository.delete(wish);
        }
    }




}
