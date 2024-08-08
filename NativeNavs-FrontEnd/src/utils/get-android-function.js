// get-android-function.js

// 투어 리뷰 보기로 이동
export function navigateToTourReviewPhotoFragment(tour_id) {
  if (
    window.Android &&
    typeof window.Android.navigateToTourReviewPhotoFragment === "function"
  ) {
    window.Android.navigateToTourReviewPhotoFragment(tour_id);
  } else {
    console.log("navigateToTourReviewPhotoFragment function is not defined");
  }
}

// Nav 리뷰 보기로 이동
export function navigateToNavReviewPhotoFragment(nav_id) {
  if (
    window.Android &&
    typeof window.Android.navigateToNavReviewPhotoFragment === "function"
  ) {
    window.Android.navigateToNavReviewPhotoFragment(nav_id);
  } else {
    console.log("navigateToNavReviewPhotoFragment function is not defined");
  }
}

// Trav 리뷰 보기로 이동
export function navigateToTravReviewPhotoFragment(trav_id) {
  if (
    window.Android &&
    typeof window.Android.navigateToTravReviewPhotoFragment === "function"
  ) {
    window.Android.navigateToTravReviewPhotoFragment(trav_id);
  } else {
    console.log("navigateToTravReviewPhotoFragment function is not defined");
  }
}

// 위시리스트 상세보기로 이동
export function navigateToWishDetailFragment(tour_id, user_id) {
  if (
    window.Android &&
    typeof window.Android.navigateToWishDetailFragment === "function"
  ) {
    window.Android.navigateToWishDetailFragment(tour_id, user_id);
  } else {
    console.log("navigateToWishDetailFragment function is not defined");
  }
}

// 투어 상세보기로 이동
export function navigateToTourDetailFragment(tour_id, user_id) {
  if (
    window.Android &&
    typeof window.Android.navigateToTourDetailFragment === "function"
  ) {
    window.Android.navigateToTourDetailFragment(tour_id, user_id);
  } else {
    console.log("navigateToTourDetailFragment function is not defined");
  }
}

// Trav 위시리스트 -> 메인 페이지로 이동
export function navigateFromWishToTourListFragment() {
  if (
    window.Android &&
    typeof window.Android.navigateFromWishToTourListFragment === "function"
  ) {
    window.Android.navigateFromWishToTourListFragment();
  } else {
    console.log("navigateFromWishToTourListFragment function is not defined");
  }
}

// 투어 작성 -> 투어 상세보기로 이동
export function moveFromTourRegisterToTourDetailFragment(tourId, navId) {
  if (
    window.Android &&
    typeof window.Android.moveFromTourRegisterToTourDetailFragment ===
      "function"
  ) {
    window.Android.moveFromTourRegisterToTourDetailFragment(tourId, navId);
  } else {
    console.log(
      "moveFromTourRegisterToTourDetailFragment function is not defined"
    );
  }
}

// 투어 작성 실패 시 안드로이드에서 경고 이펙트 주는 함수
export function showRegisterFailDialog() {
  if (
    window.Android &&
    typeof window.Android.showRegisterFailDialog === "function"
  ) {
    window.Android.showRegisterFailDialog();
  } else {
    console.log("showRegisterFailDialog function is not defined");
  }
}
// Trav 예약 리스트 페이지 -> 예약 상세 페이지로 이동
export function navigateToReservationListFragmentReservationDetail(
  tour_id,
  reservation_id
) {
  if (
    window.Android &&
    typeof window.Android.navigateToReservationListFragmentReservationDetail ===
      "function"
  ) {
    window.Android.navigateToReservationListFragmentReservationDetail(
      tour_id,
      reservation_id
    );
  } else {
    console.log(
      "navigateToReservationListFragmentReservationDetail function is not defined"
    );
  }
}

// Trav 예약 상세 페이지 -> 채팅방으로 이동
export function navigateToReservationDetailChattingRoom(chat_id) {
  if (
    window.Android &&
    typeof window.Android.navigateToReservationDetailChattingRoom === "function"
  ) {
    window.Android.navigateToReservationDetailChattingRoom(chat_id);
  } else {
    console.log(
      "navigateToReservationDetailChattingRoom function is not defined"
    );
  }
}

// Trav 예약 상세 페이지 -> 뒤로가기
export function navigateBack() {
  if (window.Android && typeof window.Android.navigateBack === "function") {
    window.Android.navigateBack();
  } else {
    console.log("navigateBack function is not defined");
  }
}

// 투어 상세 페이지 -> 투어 수정 페이지로 이동
export function navigateToTourModifyFragment(tour_id) {
  if (
    window.Android &&
    typeof window.Android.navigateToTourModifyFragment === "function"
  ) {
    window.Android.navigateToTourModifyFragment(tour_id);
  } else {
    console.log("navigateToTourModifyFragment function is not defined");
  }
}
// 투어 상세 페이지 -> 메인 페이지 이동
export function navigateToTourListFragment() {
  if (
    window.Android &&
    typeof window.Android.navigateToTourListFragment === "function"
  ) {
    window.Android.navigateToTourListFragment();
  } else {
    console.log("navigateToTourListFragment function is not defined");
  }
}
