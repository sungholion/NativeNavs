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
    arrows: false, 
    speed: 500,
    slidesToShow: 1, 
    slidesToScroll: 1,
    adaptiveHeight: false,
    variableWidth: false, 
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
