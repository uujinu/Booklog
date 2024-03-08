import { useQuery, useMutation } from 'react-query';
import { signUp, login, getMyInfo, getUserInfo } from 'api/user';

// 회원가입
export const useUserSignUpMutation = (successAction, errorAction) => {
  return useMutation(signUp, {
    onSuccess: res => {
      successAction(res);
    },
    onError: error => {
      errorAction(error);
    }
  });
};

// 로그인
export const useUserLoginMutation = (successAction, errorAction) => {
  return useMutation(login, {
    onSuccess: res => {
      successAction(res.data);
    },
    onError: error => {
      errorAction(error);
    }
  });
};
