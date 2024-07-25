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
function App() {
  const param = useSearchParams();
  return (
    <>
      <Routes>
        <Route path="/main" element={<Main />} />
        <Route path="/tour" element={<Tour />}>
          <Route path="create" element={<div>create</div>}></Route>
          <Route path="edit/:tour_id" element={<Edit />} />
          <Route path="detail/:tour_id" element={<Detail />}>
            <Route path="" element={<div>상세내용</div>}></Route>
            <Route path="reviews" element={<div>투어 리뷰 페이지</div>} />
            <Route
              path="reviews/create"
              element={<div>투어 리뷰 작성 페이지</div>}
            />
          </Route>
        </Route>
        <Route path="/nav/:user_id" element={<Nav />}>
          <Route path="reviews" element={<div>리뷰들</div>}></Route>
          <Route path="tourlist" element={<div>투어목록</div>}></Route>
        </Route>
        <Route path="/trav/:user_id" element={<Trav />}>
          <Route
            path="reviews"
            element={<div>Trav_내가작성한 리뷰 페이지</div>}
          />
          <Route path="wishlist" element={<div>위시리시트</div>} />
          <Route
            path="reservation_list"
            element={<div>예약리스트 및 완료된 Tour</div>}
          />
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
