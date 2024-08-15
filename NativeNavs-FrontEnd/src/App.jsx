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
  useEffect(() => {
    window.getUserData = (userJson) => {
      console.log("Received userJson:", userJson);
      try {
        localStorage.setItem("user", userJson);
        console.log("userJson 저장 성공");
      } catch (error) {
        console.error("userJson 저장 실패", error);
      }
    };
  }, []);

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
                  keyword={"tour"}
                  navigateToReviewPhotoFragment={
                    navigateToTourReviewPhotoFragment
                  }
                />
              }
            />
            <Route
              path="reviewphotos"
              element={<ReviewPhotos keyword={"tour"} />}
            />
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

        <Route path="/nav/:user_id" element={<Nav />}>
          <Route
            path="reviews"
            element={
              <Review
                keyword={"nav"}
                navigateToReviewPhotoFragment={navigateToNavReviewPhotoFragment}
              />
            }
          />
          <Route
            path="reviewphotos"
            element={<ReviewPhotos keyword={"guide"} />}
          />
          <Route path="tourlist" element={<NavTourList />} />
        </Route>

        <Route path="/trav/:user_id" element={<Trav />}>
          <Route
            path="reviews"
            element={
              <Review
                keyword={"trav"}
                navigateToReviewPhotoFragment={
                  navigateToTravReviewPhotoFragment
                }
              />
            }
          />
          <Route path="wishlist" element={<WishList />} />
          <Route path="reservation_list" element={<ReservationList />} />
        </Route>

        <Route path="/reservation/:tour_id" element={<Reservation />}>
          <Route path="list" element={<ReservationListForTour />} />
          <Route path="create/:trav_id" element={<ReservationCreate />} />
          <Route path="detail/:res_id" element={<ReservationDetail />} />
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
