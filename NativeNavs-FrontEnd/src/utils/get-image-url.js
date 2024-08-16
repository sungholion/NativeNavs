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
