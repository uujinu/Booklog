const setData = (key, data) => {
  localStorage.setItem(key, data);
};

// 로그인
export const loginUser = data => {
  const { USER, TOKEN } = data;
  setData('isLoggedIn', '1');
  setData('user', JSON.stringify(USER));
  setData('accessToken', TOKEN.accessToken);
  setData('refreshToken', TOKEN.refreshToken);
};

// 로그아웃
export const logoutUser = () => {
  localStorage.clear();
};

// 회원 정보 갱신
export const updateUser = user => {
  setData('user', user);
};

// 토큰 갱신
export const updateToken = token => {
  setData('accessToken', token.accessToken);
  setData('refreshToken', token.refreshToken);
};
