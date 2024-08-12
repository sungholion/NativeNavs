import React from "react";
import Slider from "react-slick";
import styles from "./Review_Item_img.module.css";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import styled from "styled-components";

const StyledSlider = styled(Slider)`
  .slick-slide {
    height: auto;
    padding-right: 2vw;
    padding-left: 2vw;
  }
  .slick-track {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    align-items: stretch;
  }
`;

export default function Review_Item_img({ imageList }) {
  var settings = {
    className: "center",
    centerMode: imageList.length > 1,
    dots: true,
    infinite: imageList.length > 2,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: false,
    variableWidth: true,
  };
  return (
    <div>
      {imageList.length === 1 ? (
        <div className={styles.item_img_one}>
          <img src={imageList[0]} alt="" />
        </div>
      ) : (
        <StyledSlider {...settings}>
          {imageList.map((imageItem, i) => {
            return (
              <div key={i}>
                <img src={imageItem} className={styles.item_img} alt="" />
              </div>
            );
          })}
        </StyledSlider>
      )}
    </div>
  );
}
