import dayjs from 'dayjs';

const datePickerFormat = 'YYYY-MM-DD';
export const DatePickerUtils = {
  format: datePickerFormat,
  parse: value => dayjs(value, datePickerFormat, true).toDate()
};
export const dateChange = data => {
  return dayjs(data).format(datePickerFormat);
};
