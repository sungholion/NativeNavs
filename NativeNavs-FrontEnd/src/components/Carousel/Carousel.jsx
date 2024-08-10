import React, { useEffect, useState } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import styled from "styled-components";
import styles from "./Carousel.module.css";
import Heart from "../Heart/Heart";
import axios from "axios";

const StyledSlider = styled(Slider)`
  .slick-slide {
    height: auto;
  }
  .slick-track {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    align-items: stretch;
  }
  .slick-dots {
    position: absolute;
    bottom: 10px;
    width: 100%;
    display: flex !important;
    justify-content: center;
    padding: 0;
  }
`;

export default function Carousel({ images, user, tourId }) {
  const settings = {
    centerMode: false,
    centerPadding: "0",
    infinite: true,
    dots: true,
    arrows: false, // 슬라이더 화살표 버튼 비활성화
    speed: 500,
    slidesToShow: 1, // 한 번에 보여줄 슬라이드 개수
    slidesToScroll: 1, // 한 번에 스크롤되는 슬라이드 개수
    adaptiveHeight: false,
    variableWidth: false, // 각 슬라이드의 너비가 일정하지 않도록 설정할 수 있음 -> false로 일정하게 두기
    // 트랙 너비 = 슬라이드 너비 * 슬라이드 개수
  };

  return (
    <div className={styles.carouselContainer}>
      <StyledSlider {...settings}>
        {images.map((image, index) => (
          <div style={styles.heartContainer} key={index}>
            <img
              src={image}
              alt={`slide-${index}`}
              className={styles.carouselImage}
            />
          </div>
        ))}
      </StyledSlider>
    </div>
  );
}
