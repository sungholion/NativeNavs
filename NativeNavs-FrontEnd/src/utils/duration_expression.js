export const duration_expression = (d1, d2) => {
  // d1, d2 : Date() 객체
  const diffDate = Math.abs(d1.getTime() - d2.getTime());

  //년 단위
  const year = Math.floor(diffDate / (1000 * 60 * 60 * 24 * 365));
  const month = Math.floor(diffDate / (1000 * 60 * 60 * 24 * 30));
  const week = Math.floor(diffDate / (1000 * 60 * 60 * 24 * 7));
  const day = Math.floor(diffDate / (1000 * 60 * 60 * 24));
  const hour = Math.floor(diffDate / (1000 * 60 * 60));
  const minute = Math.floor(diffDate / (1000 * 60));
  if (year > 0) return `${year}년`;
  if (month > 0) return `${month}달`;
  if (week > 0) return `${week}주`;
  if (day > 0) return `${day}일`;
  if (hour > 0) return `${hour}시간`;
  return `${minute}분`;
};
