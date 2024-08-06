const TIME_ZONE = 9 * 60 * 60 * 1000; // 9시간
export const getFromattedDatetime = (date) => {
  return new Date(date.getTime() + TIME_ZONE).toISOString().slice(0, -5);
};
