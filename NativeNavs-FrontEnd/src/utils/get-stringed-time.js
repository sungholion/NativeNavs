export const getStringedTime = (date) => {
  const hour = date.getHours();
  const minute = date.getMinutes();

  const str_hour = hour < 10 ? `0${hour}` : `${hour}`;
  const str_minute = minute < 10 ? `0${minute}` : `${minute}`;

  return `${str_hour}:${str_minute}`;
};
