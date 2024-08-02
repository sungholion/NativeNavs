import React from "react";
import { Routes, Route, useSearchParams } from "react-router-dom";
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

function App() {
  const param = useSearchParams();
  return (
    <>
      <Routes>
        <Route path="/main" element={<Main />} />
        <Route path="/tour" element={<Tour />}>
          <Route path="create" element={<TourCreate />} />
          <Route path="edit/:tour_id" element={<TourEdit />} />
          <Route path="detail/:tour_id" element={<Outlet />}>
            <Route index element={<Detail />} />
            <Route
              path="reviews"
              element={
                <Review
                  navigateToReviewPhotoFragment={
                    navigateToTourReviewPhotoFragment
                  }
                />
              }
            />
            <Route path="reviewphotos" element={<ReviewPhotos />} />
            <Route
              path="reviews/create"
              element={
                <div>
                  <ReviewCreate />
                </div>
              }
            />
          </Route>
        </Route>
        <Route path="/nav/:user_id" element={<Nav />}>
          <Route
            path="reviews"
            element={
              <Review
                navigateToReviewPhotoFragment={navigateToNavReviewPhotoFragment}
              />
            }
          />
          <Route path="reviewphotos" element={<ReviewPhotos />} />
          <Route path="tourlist" element={<div>투어목록</div>} />
        </Route>
        <Route path="/trav/:user_id" element={<Trav />}>
          <Route
            path="reviews"
            element={
              <Review
                navigateToReviewPhotoFragment={
                  navigateToTravReviewPhotoFragment
                }
              />
            }
          />
          <Route path="reviewphotos" element={<ReviewPhotos />} />
          <Route path="wishlist" element={<WishList />} />
          <Route
            path="reservation_list"
            element={<div>예약리스트 및 완료된 Tour</div>}
          />
        </Route>
        <Route path="/reservation/:tour_id" element={<Reservation />}>
          <Route path="list" element={<div>해당 투어에 대한 예약 목록</div>} />
          <Route path="create" element={<div>예약 하기</div>} />
          <Route path="detail/:res_id" element={<div>예약 상세 정보</div>} />
        </Route>
        <Route path="/privacy_terms" element={<Privacy_Terms />} />
        <Route path="/team" element={<Team />} />
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
