// 이미지 url 부르는 함수 - 이미지 미리보기 전용
// 파일 객체인 경우 -> 이미지를 base64변환 후 upLoadFunc로 전달
// 이미지 url인 경우 -> url 그대로 upLoadFunc로 전달
export const getImageUrl = (image, upLoadFunc) => {
  if (!image || !upLoadFunc || typeof upLoadFunc !== "function") {
    return;
  }
  if (image instanceof File) {
    const reader = new FileReader();
    reader.readAsDataURL(image);
    reader.onloadend = () => {
      upLoadFunc(reader.result);
    };
  } else if (typeof image === "string") {
    upLoadFunc(image);
  }
};
