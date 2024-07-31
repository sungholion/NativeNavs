import React from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import styled from "styled-components";
import styles from "./Carousel.module.css";

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
  .slick-dots li {
    margin: 0 5px;
  }
`;

export default function Carousel({ images }) {
  const settings = {
    centerMode: false,
    infinite: true,
    dots: true,
    speed: 750,
    slidesToShow: 1,
    slidesToScroll: 1,
    adaptiveHeight: false,
    variableWidth: false, // Set to false to make slides have equal width
  };

  return (
    <div className={styles.carouselContainer}>
      <StyledSlider {...settings}>
        {images.map((image, index) => (
          <div key={index}>
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
