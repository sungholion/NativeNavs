import React, { useEffect, useState } from "react";
import { Routes, Route } from "react-router-dom";
import Main from "./page/Main";
import Nav from "./page/Nav";
import Privacy_Terms from "./page/Privacy_Terms";
import Reservation from "./page/Reservation";
import Tour from "./page/Tour";
import Trav from "./page/Trav";
import Team from "./page/Team";
import Detail from "./page/Detail";
import WishList from "./page/WishList";
import Review from "./page/Review";
import { Outlet } from "react-router-dom";
import ReviewPhotos from "./page/ReviewPhotos";
import ReviewCreate from "./page/ReviewCreate";
import {
  navigateToTourReviewPhotoFragment,
  navigateToNavReviewPhotoFragment,
  navigateToTravReviewPhotoFragment,
} from "./utils/get-android-function";
import { getStaticImage } from "./utils/get-static-image";
import TourCreate from "./page/TourCreate";
import TourEdit from "./page/TourEdit";
import ReservationList from "./page/ReservationList";
import ReservationDetail from "./page/ReservationDetail";
import ReservationListForTour from "./page/ReservationListForTour";
import NavTourList from "./page/NavTourList";
import ReservationCreate from "./page/ReservationCreate";

function App() {
  // 유저 정보 파싱 및 localStorage에 저장
  useEffect(() => {
    window.getUserData = (userJson) => {
      console.log("Received userJson:", userJson);
      try {
        localStorage.setItem("user", userJson); // 유저 정보를 localStorage에 저장
        console.log("userJson 저장 성공");
      } catch (error) {
        console.error("userJson 저장 실패", error);
      }
    };
  }, []);

  // 검색 정보 파싱 및 localStorage에 저장
  useEffect(() => {
    window.getSearchData = (searchJson) => {
      console.log("Received searchJson:", searchJson);
      try {
        localStorage.setItem("search", searchJson); // 유저 정보를 localStorage에 저장
        console.log("searchJson 저장 성공");
      } catch (error) {
        console.error("searchJson 저장 실패", error);
      }
    };
  }, []);

  return (
    <>
      <Routes>
        {/* 메인 페이지 */}
        <Route path="/main" element={<Main />} />

        {/* 투어 페이지 */}
        <Route path="/tour" element={<Tour />}>
          {/* 투어 생성 페이지 */}
          <Route path="create" element={<TourCreate />} />
          {/* 투어 수정 페이지 */}
          <Route path="edit/:tour_id" element={<TourEdit />} />
          {/* 투어 상세 페이지 */}
          <Route path="detail/:tour_id" element={<Outlet />}>
            <Route index element={<Detail />} />
            {/* 투어 리뷰 페이지 */}
            <Route
              path="reviews"
              element={
                <Review
                  keyword={"tour"}
                  navigateToReviewPhotoFragment={
                    navigateToTourReviewPhotoFragment
                  }
                />
              }
            />
            {/* 투어의 리뷰 사진 전체보기 페이지 */}
            <Route path="reviewphotos" element={<ReviewPhotos />} />
            {/* 투어에 대한 리뷰 작성 페이지 */}
            <Route
              path="reviews/create/:reservation_id"
              element={
                <div>
                  <ReviewCreate />
                </div>
              }
            />
          </Route>
        </Route>

        {/* Nav 프로필 페이지 */}
        <Route path="/nav/:user_id" element={<Nav />}>
          {/* Nav에 대한 리뷰 페이지 */}
          <Route
            path="reviews"
            element={
              <Review
                keyword={"guide"}
                navigateToReviewPhotoFragment={navigateToNavReviewPhotoFragment}
              />
            }
          />
          {/* Nav에 대한 리뷰 사진 전체보기 페이지 */}
          <Route path="reviewphotos" element={<ReviewPhotos />} />
          {/* Nav가 등록한 투어 목록 페이지 */}
          <Route path="tourlist" element={<NavTourList />} />
        </Route>

        {/* Trav 프로필 페이지 */}
        <Route path="/trav/:user_id" element={<Trav />}>
          {/* Trav가 작성한 리뷰 페이지 */}
          <Route
            path="reviews"
            element={
              <Review
                keyword={"user"}
                navigateToReviewPhotoFragment={
                  navigateToTravReviewPhotoFragment
                }
              />
            }
          />
          {/* Trav가 작성한 리뷰 사진 전체보기 페이지 */}
          <Route path="reviewphotos" element={<ReviewPhotos />} />
          {/* Trav의 위시리스트 페이지 */}
          <Route path="wishlist" element={<WishList />} />
          {/* Trav의 예약 리스트 및 완료된 투어 페이지 */}
          <Route path="reservation_list" element={<ReservationList />} />
        </Route>

        {/* 투어 예약 페이지 */}
        <Route path="/reservation/:tour_id" element={<Reservation />}>
          {/* 해당 투어에 대한 예약 목록 페이지 */}
          <Route path="list" element={<ReservationListForTour />} />
          {/* 예약 하기 페이지 */}
          <Route path="create/:trav_id" element={<ReservationCreate />} />
          {/* 예약 상세 정보 페이지 */}
          <Route path="detail/:res_id" element={<ReservationDetail />} />
        </Route>

        {/* 개인정보 보호 및 이용 약관 페이지 */}
        <Route path="/privacy_terms" element={<Privacy_Terms />} />

        {/* 팀 소개 페이지 */}
        <Route path="/team" element={<Team />} />

        {/* 잘못된 경로 혹은 미구현된 페이지 */}
        <Route
          path="*"
          element={
            <div>
              <div>잘못된 경로 혹은 미구현된 페이지</div>
              <img
                src={getStaticImage("router-img")}
                alt=""
                style={{ width: "90vw" }}
              />
            </div>
          }
        />
      </Routes>
    </>
  );
}

export default App;
