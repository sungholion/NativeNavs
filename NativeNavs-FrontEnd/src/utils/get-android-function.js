// get-android-function.js

// 투어 리뷰 사진 보기로 이동
export function navigateToTourReviewPhotoFragment(tourId) {
  if (window.Android && typeof window.Android.navigateToTourReviewPhotoFragment === 'function') {
    window.Android.navigateToTourReviewPhotoFragment(tourId);
  } else {
    console.log('navigateToTourReviewPhotoFragment function is not defined');
  }
}

// Nav 리뷰 사진 보기로 이동
export function navigateToNavReviewPhotoFragment(navId) {
  if (window.Android && typeof window.Android.navigateToNavReviewPhotoFragment === 'function') {
    window.Android.navigateToNavReviewPhotoFragment(navId);
  } else {
    console.log('navigateToNavReviewPhotoFragment function is not defined');
  }
}

// Trav 리뷰 사진 보기로 이동
export function navigateToTravReviewPhotoFragment(travId) {
  if (window.Android && typeof window.Android.navigateToTravReviewPhotoFragment === 'function') {
    window.Android.navigateToTravReviewPhotoFragment(travId);
  } else {
    console.log('navigateToTravReviewPhotoFragment function is not defined');
  }
}

// 위시리스트 상세보기로 이동
export function navigateToWishDetailFragment(tourId) {
  if (window.Android && typeof window.Android.navigateToWishDetailFragment === 'function') {
    window.Android.navigateToWishDetailFragment(tourId);
  } else {
    console.log('navigateToWishDetailFragment function is not defined');
  }
}

// 투어 상세보기로 이동
export function navigateToTourDetailFragment(tour_id, user_id) {
  if (window.Android && typeof window.Android.navigateToTourDetailFragment === 'function') {
    window.Android.navigateToTourDetailFragment(tour_id, user_id);
  } else {
    console.log('navigateToTourDetailFragment function is not defined');
  }
}
