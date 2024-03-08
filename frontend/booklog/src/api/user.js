import { publicApi, privateApi } from '../utils/axios';

export const signUp = data => {
  return publicApi.post('/users/signup', data);
};

export const login = user => {
  return publicApi.post('/users/signin', user);
};

export const getMyInfo = () => {
  return privateApi.get(`/users/info/my-info`);
};

export const getUserInfo = id => {
  return publicApi.get(`/users/info/${id}`);
};

export const deleteUser = id => {
  return privateApi.get(`/users/${id}`);
};
