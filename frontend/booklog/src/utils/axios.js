import axios from 'axios';
import { logoutUser, updateToken } from 'store/user';

export const baseURL = 'http://localhost:8080/api';

export const privateApi = axios.create({
  baseURL: baseURL,
  headers: {
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`
  }
});

export const publicApi = axios.create({ baseURL });

privateApi.interceptors.request.use(
  config => {
    const token = localStorage.getItem('accessToken');

    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    } else {
      config.withCredentials = false;
    }

    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// Token Refresh
export const refresh = async () => {
  const refreshToken = localStorage.getItem('refreshToken');
  if (refreshToken === '' || refreshToken === null)
    return Promise.reject('Invalid Refresh Token');

  return await axios.create({ baseURL }).post('/auth/reissueToken', {
    refreshToken: refreshToken
  });
};

privateApi.interceptors.response.use(
  response => response,
  async error => {
    const { config } = error;

    const status = error.response ? error.response.status : null;
    if (status === 401) {
      await refresh()
        .then(res => {
          const originRequest = config;
          const newAccessToken = res.data.accessToken;
          updateToken(res.data);

          axios.defaults.headers.common.Authorization = `Bearer ${newAccessToken}`;
          originRequest.headers.Authorization = `Bearer ${newAccessToken}`;
          return axios(originRequest);
        })
        .catch(e => {
          logoutUser();
          alert('로그인 연장에 실패했습니다. 다시 로그인해주세요.');
          window.location.replace('/login');
          return Promise.reject(e);
        });
    }
    alert('요청에 실패했습니다.');
    return Promise.reject(error);
  }
);
