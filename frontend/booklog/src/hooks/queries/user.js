import { useState } from 'react';
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

// 회원 정보 요청(private)
export const useGetMyInfoQuery = () => {
  const [user, setUser] = useState('');
  const { isLoading } = useQuery('USER_INFO', getMyInfo, {
    onSuccess: data => setUser(data.data.data)
  });

  return { user, isLoading };
};

// 회원 정보 요청(public)
export const useGetUserInfoQuery = id => {
  const [user, setUser] = useState('');
  const { isLoading } = useQuery('USER_INFO', getUserInfo(id), {
    onSuccess: data => setUser(data.data.data),
    onError: e => Promise.reject(e)
  });

  return {
    user,
    isLoading
  };
};
