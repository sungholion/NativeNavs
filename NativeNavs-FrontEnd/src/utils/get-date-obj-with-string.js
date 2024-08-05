export const getDateObjWithString = (date, time) => {
  // time : hh : mm 형태
  // date : yyyy-mm-dd 형태

  const hour = Number(time.substr(0, 2));
  const minute = Number(time.substr(3, 2));
  const subDateObj = new Date(date);

  return new Date(
    subDateObj.getFullYear(),
    subDateObj.getMonth(),
    subDateObj.getDay(),
    hour,
    minute
  );
};
