export const getDateObjWithString = (date, time) => {
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
