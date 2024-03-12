import { useState } from 'react';
import { useForm } from 'react-hook-form';
import styled from '@emotion/styled';
import { Link, useNavigate } from 'react-router-dom';
import CustomButton from 'components/common/button';
import CustomInput from 'components/common/input';
import { useUserLoginMutation } from 'hooks/queries/user';
import { loginUser } from 'store/user';

const LoginForm = () => {
  const {
    register,
    handleSubmit,
    formState: { isSubmitting, errors }
  } = useForm();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const loginMutation = useUserLoginMutation(
    res => {
      loginUser(res.data);
      navigate('/');
    },
    () => {
      setEmail('');
      setPassword('');
      alert('아이디 또는 비밀번호가 일치하지 않습니다.');
    }
  );

  return (
    <FormWrapper>
      <Form
        onSubmit={handleSubmit(data => {
          loginMutation.mutate(data);
        })}
      >
        <Label htmlFor="Login">Login</Label>
        <CustomInput
          id="email"
          type="email"
          placeholder="Email"
          {...register('email', {
            required: '이메일을 입력해주세요.'
          })}
          value={email}
          onChange={e => setEmail(e.target.value)}
        />
        {errors.email && <Msg role="alert">{errors.email.message}</Msg>}
        <CustomInput
          id="password"
          type="password"
          placeholder="Password"
          {...register('password', {
            required: password === '' ? '비밀번호를 입력해주세요.' : ''
          })}
          autocomplete={'off'}
          value={password}
          onChange={e => setPassword(e.target.value)}
        />
        {errors.password && <Msg role="alert">{errors.password.message}</Msg>}
        <CustomButton
          type="submit"
          disabled={isSubmitting}
          color={`var(--purple-color)`}
          text={'로그인'}
        />
        <LinkWrapper>
          <OtherLink ta={`left`}>
            <Link to="/password-reset">비밀번호 재설정</Link>
          </OtherLink>
          <OtherLink ta={`right`}>
            <Link to="/signup">아직 회원이 아니세요?</Link>
          </OtherLink>
        </LinkWrapper>
      </Form>
    </FormWrapper>
  );
};

const FormWrapper = styled.div`
  max-width: 400px;
  width: 100%;
`;

const Form = styled.form`
  height: 100%;
  justify-content: center;
  display: flex;
  flex-direction: column;
  align-items: center;

  & > div {
    position: relative;
  }
`;

const Label = styled.label`
  font-size: 2rem;
  width: 100%;
  text-align: left;
  margin-bottom: 10px;
  font-weight: bold;
  color: var(--purple-color);
`;

const Msg = styled.p`
  font-size: 11px;
  font-weight: bold;
  color: red;
  width: 100%;
  padding-left: 15px;
  position: relative;
  bottom: 12px;
`;

const LinkWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  padding: 0 10px;
`;

const OtherLink = styled.div`
  font-size: 15px;
  width: 100%;
  text-align: ${props => props.ta};

  & > a:hover {
    text-decoration: underline;
  }
`;

export default LoginForm;
