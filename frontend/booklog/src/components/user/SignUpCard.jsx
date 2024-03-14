import { useEffect, useState } from 'react';
import styled from '@emotion/styled';
import { redirect } from 'react-router-dom';
import { AuthForm } from './AuthForm';
import { useUserSignUpMutation } from 'hooks/queries/user';
import { ProfileForm } from './ProfileForm';

export const SignUpCard = () => {
  const initialValues = {
    email: '',
    name: '',
    password: '',
    birthday: ''
  };

  const [step, setStep] = useState(0);
  const [values, setValues] = useState(initialValues);

  useEffect(() => {
    console.log('values: ' + JSON.stringify(values));
  }, [values]);

  const signUpMutation = useUserSignUpMutation(
    res => {
      alert('회원가입이 완료되었습니다.');
    },
    () => {
      alert('회원가입에 실패했습니다.');
    }
  );

  return (
    <FormWrapper>
      <Label htmlFor="SignUp">SignUp</Label>
      {(() => {
        switch (step) {
          case 0:
            return (
              <AuthForm
                value={values}
                setValues={setValues}
                setStep={setStep}
              />
            );
          case 1:
            return (
              <ProfileForm
                value={values}
                setValues={setValues}
                setStep={setStep}
              />
            );
          default:
            return redirect('/');
        }
      })()}
    </FormWrapper>
  );
};

const FormWrapper = styled.div`
  max-width: 400px;
  width: 100%;
`;

const Label = styled.label`
  font-size: 2rem;
  width: 100%;
  text-align: left;
  margin-bottom: 10px;
  font-weight: bold;
  color: var(--purple-color);
`;
