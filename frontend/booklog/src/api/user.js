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

export const checkEmail = email => {
  return publicApi.get(`/users/email/${email}`);
};

export const checkName = name => {
  return publicApi.get(`/users/name/${name}`);
};

export const getCode = email => {
  return publicApi.get(`/users/signup/email?email=${email}`);
};

export const emailVerification = data => {
  return publicApi.post('/users/signup/email-verification', data);
};

export const getPWResetEmail = email => {
  return publicApi.get(`/users/password?email=${email}`);
};

export const checkCode = data => {
  return publicApi.post('/users/password', data);
};

export const resetPassword = data => {
  return publicApi.put('/users/password', data);
};
