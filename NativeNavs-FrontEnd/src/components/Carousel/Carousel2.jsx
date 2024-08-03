import React from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import styled from "styled-components";
import styles from "./Carousel2.module.css";
import language from "../../assets/language.png";

const StyledSlider = styled(Slider)`
  .slick-slide {
    height: auto;
    padding: 10px;
    border: 1px solid #d9d9d9;
    border-radius: 10px;
    margin: 5px
  }
  .slick-track {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    align-items: stretch;
  }
  .slick-dots {
    display: none !important;
  }
`;

export default function Carousel2({ tourData }) {
  const settings = {
    centerMode: tourData.length > 1, // 투어 데이터가 1개 이상일 때 중앙 모드 활성화
    centerPadding: tourData.length > 1 ? "20px" : "0",
    infinite: true, // 투어 데이터가 1개 이상일 때 무한 스크롤 활성화
    arrows: false, // 슬라이더 화살표 버튼 비활성화
    speed: 750, // 슬라이더 전환 속도 (밀리초)
    slidesToShow: 1, // 한 번에 보여줄 슬라이드 수
    slidesToScroll: 1, // 한 번에 스크롤할 슬라이드 수
    adaptiveHeight: false, // 슬라이드 높이를 콘텐츠에 맞게 조정하지 않음
    variableWidth: false, // 슬라이드 너비를 고정된 너비로 설정 (가변 너비 비활성화)
  };

  if (!tourData || !Array.isArray(tourData) || tourData.length === 0) {
    return null;
  }

  if (tourData.length === 1) {
    // 예정된 투어가 하나일 때 : 캐러셀을 사용하지 않고 단일 컴포넌트로 렌더링
    const tour = tourData[0];
    return (
      <div className={styles.singleTourContainer}>
        <div className={styles.singletourInfoContainer}>
          <div className={styles.singtourInfoTopContainer}>
            <img
              src={tour.thumbnail}
              alt="single-slide"
              className={styles.singlecarouselImage}
            />
          </div>
          <div className={styles.tourInfoBottomContainer}>
            <div className={styles.tourLeftInfo}>
              <p className={styles.carouselTitle}>{tour.title}</p>
              <p className={styles.carouselDate}>
                {tour.date.toLocaleDateString("ko-KR")}
              </p>
              <p className={styles.carouselAverage}>
                ★ {tour.reviewAverage}
              </p>
            </div>
            <div className={styles.tourRightInfo}>
              <div className={styles.navImageNickname}>
                <img
                  src={tour.navImage}
                  alt="Nav 이미지"
                  className={styles.navImage}
                />
                <p className={styles.navNickname}>{tour.navNickname}</p>
              </div>
              <div className={styles.navLanguage}>
                <img
                  className={styles.navLanguageImage}
                  src={language}
                  alt=""
                />
                <p className={styles.navLanguageText}>
                  {tour.languages.length === 1
                    ? tour.languages[0]
                    : `${tour.languages[0]} 외 ${
                        tour.languages.length - 1
                      }개국어`}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  // 예정된 투어가 여러 개일 때 : 캐러셀
  return (
    <div className={styles.carouselContainer}>
      <StyledSlider {...settings}>
        {tourData.map((tour, index) => (
          <div key={index} className={styles.slide}>
            <div className={styles.tourInfoContainer}>
              <div className={styles.tourInfoTopContainer}>
                <img
                  src={tour.thumbnail}
                  alt={`slide-${index}`}
                  className={styles.carouselImage}
                />
              </div>
              <div className={styles.tourInfoBottomContainer}>
                <div className={styles.tourLeftInfo}>
                  <p className={styles.carouselTitle}>{tour.title}</p>
                  <p className={styles.carouselDate}>
                    {tour.date.toLocaleDateString("ko-KR")}
                  </p>
                  <p className={styles.carouselAverage}>
                    ★ {tour.reviewAverage}
                  </p>
                </div>
                <div className={styles.tourRightInfo}>
                  <div className={styles.navImageNickname}>
                    <img
                      src={tour.navImage}
                      alt="Nav 이미지"
                      className={styles.navImage}
                    />
                    <p className={styles.navNickname}>{tour.navNickname}</p>
                  </div>
                  <div className={styles.navLanguage}>
                    <img
                      className={styles.navLanguageImage}
                      src={language}
                      alt=""
                    />
                    <p className={styles.navLanguageText}>
                      {tour.languages.length === 1
                        ? tour.languages[0]
                        : `${tour.languages[0]} 외 ${
                            tour.languages.length - 1
                          }개국어`}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </StyledSlider>
    </div>
  );
}
