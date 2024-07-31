import "./App.css";
import { Routes, Route, useSearchParams } from "react-router-dom";
import Main from "./page/Main";
import Nav from "./page/Nav";
import Privacy_Terms from "./page/Privacy_Terms";
import Reservation from "./page/Reservation";
import Tour from "./page/Tour";
import Trav from "./page/Trav";
import Team from "./page/Team";
import Detail from "./page/Detail";
import Edit from "./page/Edit";
import Tour_Create from "./subpage/tour_create/Tour_Create";
import WishList from "./page/WishList";
import Review from "./page/Review";
import { Outlet } from "react-router-dom";
import ReviewPhotos from "./page/ReviewPhotos";

function App() {
  const param = useSearchParams();
  return (
    <>
      <Routes>
        <Route path="/main" element={<Main />} />
        <Route path="/tour" element={<Tour />}>
          <Route path="create" element={<Tour_Create />} />
          <Route path="edit/:tour_id" element={<Edit />} />
          <Route path="detail/:tour_id" element={<Outlet />}>
            <Route index element={<Detail />} />
            <Route path="review" element={<Review />} />
            <Route path="reviewphotos" element={<ReviewPhotos />} />
            <Route path="reviews/create" element={<div>투어 리뷰 작성 페이지</div>} />
          </Route>
        </Route>
        <Route path="/nav/:user_id" element={<Nav />}>
          <Route path="reviews" element={<div>리뷰들</div>} />
          <Route path="tourlist" element={<div>투어목록</div>} />
        </Route>
        <Route path="/trav/:user_id" element={<Trav />}>
          <Route path="reviews" element={<div>Trav_내가작성한 리뷰 페이지</div>} />
          <Route path="wishlist" element={<WishList />} />
          <Route path="reservation_list" element={<div>예약리스트 및 완료된 Tour</div>} />
        </Route>
        <Route path="/reservation/:tour_id" element={<Reservation />}>
          <Route path="create" element={<div>예약 하기</div>} />
          <Route path="detail/:res_id" element={<div>예약 상세 정보</div>} />
        </Route>
        <Route path="/privacy_terms" element={<Privacy_Terms />} />
        <Route path="/team" element={<Team />} />
        <Route path="*" element={<div>잘못된 접근</div>} />
      </Routes>
    </>
  );
}

export default App;
