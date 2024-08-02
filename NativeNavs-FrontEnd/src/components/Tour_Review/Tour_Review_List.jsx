import React from "react";
import Tour_Review_Item from "./Tour_Review_Item";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import styled from "styled-components";
import styles from "./Tour_Review_List.module.css";

const StyledSlider = styled(Slider)`
  .slick-slide {
    height: auto;
    padding-right: 1vw;
    padding-left: 1vw;
  }
  .slick-track {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    align-items: stretch;
  }
`;

export default function Tour_Review_List({ infoList }) {
  const settings = {
    centerMode: infoList.length > 1,
    infinite: infoList.length > 2,
    dots: false,
    speed: 750,
    slidesToShow: 1,
    slidesToScroll: 1,
    variableWidth: true,
    daptiveHeight: true,
  };
  return (
    <div>
      {infoList.length === 1 ? (
        <div className={styles.only_one}>
          <Tour_Review_Item {...infoList[0]} />
        </div>
      ) : (
        <div className={`slider-container`}>
          <StyledSlider {...settings}>
            {infoList.map((Item) => {
              return (
                <div key={Item.id}>
                  <Tour_Review_Item {...Item} />
                </div>
              );
            })}
          </StyledSlider>
        </div>
      )}
    </div>
  );
}
