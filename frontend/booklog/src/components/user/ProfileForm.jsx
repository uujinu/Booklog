import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import { Controller, useForm } from 'react-hook-form';
import CustomInput from 'components/common/input';
import CustomButton from 'components/common/button';
import { dateChange } from 'utils/calendar';
import { DatePicker } from '@mui/x-date-pickers';
import { nameReg } from 'utils/regexData';
import { checkName } from 'api/user';
import { useUserSignUpMutation } from 'hooks/queries/user';

export const ProfileForm = ({ value, setValues }) => {
  const {
    register,
    watch,
    control,
    formState: { errors },
    handleSubmit
  } = useForm({
    mode: 'onChange',
    defaultValues: {
      name: '',
      birthday: ''
    }
  });

  const name = watch('name');
  const birthday = watch('birthday');
  const [nameCheckCompleted, setNameCheckCompleted] = useState(false);
  const [nameBtnDisabled, setNameBtnDisabled] = useState(true);
  const [summitBtnDisabled, setSubmitBtnDisabled] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    setNameCheckCompleted(false);
    if (errors.name || name === '') {
      setNameBtnDisabled(true);
    } else setNameBtnDisabled(false);
  }, [errors.name, name]);

  useEffect(() => {
    if (nameCheckCompleted) {
      setValues({
        ...value,
        name: name,
        birthday: birthday
      });
    }
  }, [nameCheckCompleted, setValues, value, name, birthday]);

  useEffect(() => {
    if (nameCheckCompleted && birthday !== '') {
      setSubmitBtnDisabled(false);
    } else setSubmitBtnDisabled(true);
  }, [nameCheckCompleted, birthday]);

  const handleNameCheck = e => {
    e.preventDefault();
    checkName(name)
      .then(() => {
        setNameCheckCompleted(true);
        alert('사용 가능한 닉네임입니다.');
      })
      .catch(e => {
        if (e.response.data.code === 409) {
          setNameCheckCompleted(false);
          alert(e.response.data.message);
        } else alert('오류가 발생했습니다. 다시 시도해주세요.');
      });
  };

  const timeout = () => {
    setTimeout(() => {
      navigate('/', { replace: true });
    }, 1500);
    return () => {
      clearTimeout(timeout);
    };
  };

  const signUpMutation = useUserSignUpMutation(
    () => {
      alert('회원가입이 완료되었습니다.');
      timeout();
    },
    () => {
      alert('회원가입에 실패했습니다.');
      navigate(0, { replace: true });
    }
  );

  const handleStep = () => {
    signUpMutation.mutate(value);
  };

  return (
    <Form onSubmit={handleSubmit(handleStep)}>
      <InputCheckBox>
        <ICWrapper>
          <CustomInput
            id="name"
            placeholder="Nickname"
            {...register('name', {
              required: '닉네임을 입력해주세요',
              pattern: {
                value: nameReg,
                message: '닉네임은 한글, 영문, 숫자 조합 2~10자 이내입니다.'
              }
            })}
          />
          <CustomButton
            disabled={nameBtnDisabled ? 'disabled' : ''}
            color={`var(--purple-color)`}
            text={'중복확인'}
            onClick={handleNameCheck}
          />
        </ICWrapper>
        {errors.name && <Msg role="alert">{errors.name.message}</Msg>}
      </InputCheckBox>
      <CalendarBox>
        <Controller
          name="birthday"
          control={control}
          defaultValue={''}
          render={({ field }) => {
            return (
              <DatePicker
                value={field.value}
                label="생년월일"
                slotProps={{
                  textField: {
                    required: true
                  }
                }}
                format="YYYY / MM / DD"
                onChange={date => {
                  field.onChange(dateChange(date));
                }}
              />
            );
          }}
        />
      </CalendarBox>
      <CustomButton
        type="submit"
        disabled={summitBtnDisabled ? 'disabled' : ''}
        color={`var(--purple-color)`}
        text={'회원가입'}
      />
    </Form>
  );
};

const Form = styled.form`
  height: 100%;
  justify-content: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-bottom: 70px;

  & > div {
    position: relative;
  }
`;

const InputCheckBox = styled.div`
  width: 100%;
`;

const ICWrapper = styled.div`
  display: flex;

  & > button {
    margin-bottom: 15px;
    width: 150px;
  }
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

const CalendarBox = styled.div`
  display: flex;
  width: 100%;
  padding: 15px 0 30px 0;
  & > div {
    width: 100%;
  }
`;
