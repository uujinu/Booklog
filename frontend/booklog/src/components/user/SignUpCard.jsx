import { useState } from 'react';
import styled from '@emotion/styled';
import { AuthForm } from './AuthForm';
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

  return (
    <FormWrapper>
      <Label htmlFor="SignUp">SignUp</Label>
      {(() => {
        switch (step) {
          default:
          case 0:
            return (
              <AuthForm
                value={values}
                setValues={setValues}
                setStep={setStep}
              />
            );
          case 1:
            return <ProfileForm value={values} setValues={setValues} />;
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
