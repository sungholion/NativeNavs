package com.nativenavs.stamp.dto;

import com.nativenavs.review.dto.ReviewResponseDTO;
import com.nativenavs.review.entity.ReviewEntity;
import com.nativenavs.review.entity.ReviewImageEntity;
import com.nativenavs.stamp.entity.StampEntity;
import com.nativenavs.stamp.entity.UserStampEntity;
import com.nativenavs.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StampDTO {
//    private int stampId;
    private String name;
    private String image;
    private LocalDate createdDate;


    public static StampDTO toStampDTO(UserStampEntity userStampEntity) {
        StampDTO stampDTO = new StampDTO();
//        stampDTO.setStampId(userStampEntity.);
        stampDTO.setName(userStampEntity.getStamp().getName());
        stampDTO.setImage(userStampEntity.getStamp().getImage());
        stampDTO.setCreatedDate(userStampEntity.getCreatedDate());
        return stampDTO;
    }
}
