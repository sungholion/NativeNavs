import React from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import styled from "styled-components";
import styles from "./Carousel2.module.css";

const StyledSlider = styled(Slider)`
  .slick-slide {
    height: auto;
    padding: 10px;
    border: 1px solid #d9d9d9;
    border-radius: 10px;
    margin: 5px;
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

export default function Carousel2({
  reservationsInProgress,
  navigateToReservationListFragmentReservationDetail,
  user,
}) {
  const settings = {
    centerMode: reservationsInProgress.length > 1,
    centerPadding: reservationsInProgress.length > 1 ? "20px" : "0",
    infinite: true, 
    arrows: false, 
    speed: 750,
    slidesToShow: 1,
    slidesToScroll: 1,
    adaptiveHeight: false,
    variableWidth: false,
  };

  if (
    !reservationsInProgress ||
    !Array.isArray(reservationsInProgress) ||
    reservationsInProgress.length === 0
  ) {
    return null;
  }

  const formatDate = (date) => {
    const options = { year: "numeric", month: "2-digit", day: "2-digit" };
    const dateString = new Date(date).toLocaleDateString("ko-KR", options);
    return dateString.replace(/\.$/, "");
  };

  const formatLanguages = (languages) => {
    return languages.split(",").map((lang) => lang.trim());
  };

  if (reservationsInProgress.length === 1) {
    const tour = reservationsInProgress[0];
    const formattedLanguages = formatLanguages(tour.guide.userLanguage);
    return (
      <div
        onClick={() =>
          navigateToReservationListFragmentReservationDetail(
            tour.tourId,
            tour.reservationId
          )
        }
        className={styles.singleTourContainer}
      >
        <div className={styles.singletourInfoContainer}>
          <div className={styles.singtourInfoTopContainer}>
            <img
              src={tour.thumbnailImage}
              alt="single-slide"
              className={styles.singlecarouselImage}
            />
          </div>
          <div className={styles.tourInfoBottomContainer}>
            <div className={styles.tourLeftInfo}>
              <p className={styles.carouselTitle}>{tour.tourTitle}</p>
              <p className={styles.carouselDate}>
                {formatDate(tour.reservationDate)}
              </p>
              <p className={styles.carouselAverage}>★ {tour.tourReviewScore}</p>
            </div>
            <div className={styles.tourRightInfo}>
              <div className={styles.navImageNickname}>
                <img
                  src={tour.guide.image}
                  alt="Nav 이미지"
                  className={styles.navImage}
                />
                <p className={styles.navNickname}>{tour.guide.nickname}</p>
              </div>
              <div className={styles.navLanguage}>
                🌏
                <p className={styles.navLanguageText}>
                  {formattedLanguages.length === 1
                    ? formattedLanguages[0]
                    : user && user.isKorean
                    ? `${formattedLanguages[0]} 외 ${
                        formattedLanguages.length - 1
                      }개 국어`
                    : `${formattedLanguages[0]} and ${
                        formattedLanguages.length - 1
                      } other`}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className={styles.carouselContainer}>
      <StyledSlider {...settings}>
        {reservationsInProgress.map((tour, index) => {
          const formattedLanguages = formatLanguages(tour.guide.userLanguage);
          return (
            <div
              onClick={() =>
                navigateToReservationListFragmentReservationDetail(
                  tour.tourId,
                  tour.reservationId
                )
              }
              key={index}
              className={styles.slide}
            >
              <div className={styles.tourInfoContainer}>
                <div className={styles.tourInfoTopContainer}>
                  <img
                    src={tour.thumbnailImage}
                    alt={`slide-${index}`}
                    className={styles.carouselImage}
                  />
                </div>
                <div className={styles.tourInfoBottomContainer}>
                  <div className={styles.tourLeftInfo}>
                    <p className={styles.carouselTitle}>{tour.tourTitle}</p>
                    <p className={styles.carouselDate}>
                      {formatDate(tour.reservationDate)}
                    </p>
                    <p className={styles.carouselAverage}>
                      ★ {tour.tourReviewScore}
                    </p>
                  </div>
                  <div className={styles.tourRightInfo}>
                    <div className={styles.navImageNickname}>
                      <img
                        src={tour.guide.image}
                        alt="Nav 이미지"
                        className={styles.navImage}
                      />
                      <p className={styles.navNickname}>{tour.navNickname}</p>
                    </div>
                    <div className={styles.navLanguage}>
                      
                      <p className={styles.navLanguageText}>
                        {formattedLanguages.length === 1
                          ? `🌏 ${formattedLanguages[0]}`
                          : user && user.isKorean
                          ? `🌏 ${formattedLanguages[0]} 외 ${
                              formattedLanguages.length - 1
                            }개 국어`
                          : `🌏 ${formattedLanguages[0]} and ${
                              formattedLanguages.length - 1
                            } other`}
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          );
        })}
      </StyledSlider>
    </div>
  );
}
