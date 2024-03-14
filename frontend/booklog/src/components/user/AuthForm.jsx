import styled from '@emotion/styled';
import { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import CustomInput from 'components/common/input';
import CustomButton from 'components/common/button';
import { emailReg, passwordReg } from 'utils/regexData';
import { checkEmail, getCode, emailVerification } from 'api/user';

export const AuthForm = ({ value, setValues, setStep }) => {
  const {
    register,
    watch,
    formState: { errors },
    reset,
    setValue,
    handleSubmit
  } = useForm({
    mode: 'onChange',
    defaultValues: {
      email: '',
      code: '',
      password: '',
      passwordCheck: ''
    }
  });

  const email = watch('email');
  const code = watch('code');
  const password = watch('password');
  const passwordCheck = watch('passwordCheck');
  const [emailInputDisabled, setEmailInputDisabled] = useState(false);
  const [emailBtnDisabled, setEmailBtnDisabled] = useState(true);
  const [codeInputDisabled, setCodeInputDisabled] = useState(true);
  const [codeBtnDisabled, setCodeBtnDisabled] = useState(true);
  const [codeBtnText, setCodeBtnText] = useState('코드전송');
  const [emailCodeError, setEmailCodeError] = useState(false);
  const [emailCodeErrorMsg, setEmailCodeErrorMsg] = useState('');
  const [pwCheckInputDisabled, setPWCheckInputDisabled] = useState(true);
  const [minutes, setMinutes] = useState(1);
  const [seconds, setSeconds] = useState(0);
  const [intervalTime, setIntervalTime] = useState(0);
  const [emailCheckCompleted, setEmailCheckCompleted] = useState(false);
  const [codeCheckCompleted, setCodeCheckCompleted] = useState(false);
  const [passwordCheckCompleted, setPasswordCheckCompleted] = useState(false);
  const [stepBtnDisabled, setStepBtnDisabled] = useState(true);

  useEffect(() => {
    const timer = setInterval(() => {
      if (seconds > 0) {
        setSeconds(seconds - intervalTime);
      }

      if (seconds === 0) {
        if (minutes === 0) {
          clearInterval(timer);
          setCodeError(true, '인증코드가 만료되었습니다.');
          if (intervalTime === 1) {
            setCodeInputAndBtn(true, true);
            setValue('code', '');
          }
        } else {
          setMinutes(minutes - intervalTime);
          setSeconds(59);
        }
      }
    }, 1000);
    return () => clearInterval(timer);
  }, [minutes, seconds, intervalTime, setValue]);

  useEffect(() => {
    if (emailCheckCompleted && codeCheckCompleted && passwordCheckCompleted) {
      setStepBtnDisabled(false);
    } else setStepBtnDisabled(true);
  }, [emailCheckCompleted, codeCheckCompleted, passwordCheckCompleted]);

  useEffect(() => {
    setEmailCheckCompleted(false);

    if (errors.email || !emailReg.test(email)) {
      setEmailBtnDisabled(true);
    } else setEmailBtnDisabled(false);
  }, [email, errors.email]);

  useEffect(() => {
    if (errors.code || code === '' || emailCodeError) {
      setCodeBtnDisabled(true);
    } else setCodeBtnDisabled(false);
  }, [errors.code, code, emailCodeError]);

  useEffect(() => {
    setPasswordCheckCompleted(false);
    if (errors.password || !passwordReg.test(password)) {
      setPWCheckInputDisabled(true);
    } else setPWCheckInputDisabled(false);
  }, [password, errors.password]);

  useEffect(() => {
    setPasswordCheckCompleted(false);
    if (!errors.passwordCheck && passwordCheck !== '') {
      setPasswordCheckCompleted(true);
    }
  }, [passwordCheck, errors.passwordCheck]);

  const emailInputAndBtnDisabled = () => {
    setEmailInputDisabled(true);
    setEmailBtnDisabled(true);
  };

  const setCodeError = (isError, msg) => {
    setEmailCodeError(isError);
    setEmailCodeErrorMsg(msg);
  };

  const setCodeInputAndBtn = (isInputDisabled, isBtnDisabled) => {
    setCodeInputDisabled(isInputDisabled);
    setCodeBtnDisabled(isBtnDisabled);
  };

  const resetTimer = () => {
    setMinutes(1);
    setSeconds(0);
  };

  const handleEmailCheck = e => {
    e.preventDefault();
    checkEmail(email)
      .then(res => {
        if (res.data.result === 'SUCCESS') {
          setValues({
            ...value,
            email: email
          });
          setEmailCheckCompleted(true);
          setCodeBtnDisabled(false);
          emailInputAndBtnDisabled();
          setCodeBtnText('코드전송');
          alert('사용 가능한 이메일입니다.');
        }
      })
      .catch(e => {
        if (e.response.data.code === 409) {
          alert(e.response.data.message);
        } else alert('오류가 발생했습니다. 다시 시도해보세요.');
        setEmailCheckCompleted(false);
      });
  };

  const getEmailCode = () => {
    getCode(email)
      .then(res => {
        setCodeInputAndBtn(false, true);
        setCodeBtnText('확인');
        setCodeError(false, '');
        resetTimer();
        setIntervalTime(1);
        alert(res.data.data);
      })
      .catch(() => {
        alert('이메일 전송에 실패했습니다. 다시 시도해주세요.');
      });
  };

  const emailCodeConfirm = () => {
    emailVerification({
      email: value.email,
      code: code
    })
      .then(res => {
        setIntervalTime(0);
        setCodeInputAndBtn(true, true);
        setCodeError(false, '');
        setCodeCheckCompleted(true);
        alert(res.data.data);
      })
      .catch(() => {
        alert('코드가 일치하지 않습니다.');
      });
  };

  const handleCodeCheck = e => {
    e.preventDefault();
    if (codeBtnText === '코드전송') {
      getEmailCode();
    } else if (codeBtnText === '확인') {
      emailCodeConfirm();
    }
  };

  const handleResend = e => {
    e.preventDefault();
    reset({ code: '' });
    setCodeError(false, '');
    getEmailCode();
  };

  const handleStep = () => {
    setValues({
      ...value,
      password: password
    });
    setStep(1);
  };

  return (
    <Form onSubmit={handleSubmit(handleStep)}>
      <InputCheckBox>
        <ICWrapper>
          <CustomInput
            id="email"
            type="email"
            placeholder="Email"
            {...register('email', {
              required: '이메일을 입력해주세요.',
              pattern: {
                value: emailReg,
                message: '이메일 형식이 올바르지 않습니다.'
              }
            })}
            disabled={emailInputDisabled}
          />
          <CustomButton
            disabled={emailBtnDisabled ? 'disabled' : ''}
            color={`var(--purple-color)`}
            text={'중복확인'}
            onClick={handleEmailCheck}
          />
        </ICWrapper>
        {errors.email && <Msg role="alert">{errors.email.message}</Msg>}
        <ICWrapper>
          <CodeInput>
            <CustomInput
              id="code"
              placeholder="Email Code"
              {...register('code', {
                required: '코드를 입력해주세요.'
              })}
              disabled={codeInputDisabled}
            />
            {!codeInputDisabled && (
              <TimeLimit>
                {minutes.toString().padStart(2, '0')}:
                {seconds.toString().padStart(2, '0')}
              </TimeLimit>
            )}
          </CodeInput>
          <CustomButton
            disabled={codeBtnDisabled ? 'disabled' : ''}
            color={`var(--purple-color)`}
            text={codeBtnText}
            onClick={handleCodeCheck}
          />
        </ICWrapper>
        {!codeCheckCompleted && codeBtnText === '확인' && (
          <ResendEmail>
            <button onClick={handleResend}>코드 재전송</button>
          </ResendEmail>
        )}
        {(emailCodeError || errors.code) && (
          <Msg role="alert">{emailCodeErrorMsg || errors.code?.message}</Msg>
        )}
      </InputCheckBox>
      <CustomInput
        id="password"
        type="password"
        placeholder="Password"
        {...register('password', {
          required: '비밀번호를 입력해주세요.',
          pattern: {
            value: passwordReg,
            message: '비밀번호는 영문자, 숫자, 특수문자 조합 8~15자 이내입니다.'
          }
        })}
        autocomplete={'off'}
      />
      {errors.password && <Msg role="alert">{errors.password.message}</Msg>}
      <CustomInput
        id="password-check"
        type="password"
        placeholder="Password 확인"
        {...register('passwordCheck', {
          required: '비밀번호를 입력해주세요.',
          validate: value =>
            value === watch('password') || '비밀번호가 일치하지 않습니다.'
        })}
        autocomplete={'off'}
        disabled={pwCheckInputDisabled}
      />
      {errors.passwordCheck && (
        <Msg role="alert">{errors.passwordCheck.message}</Msg>
      )}
      <CustomButton
        type="submit"
        disabled={stepBtnDisabled ? 'disabled' : ''}
        color={`var(--purple-color)`}
        text={'>>'}
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

const CodeInput = styled.div`
  display: flex;
  position: relative;
  width: 100%;
`;

const ResendEmail = styled.div`
  font-size: 11px;
  padding-left: 15px;
  font-weight: bold;
  position: relative;
  bottom: 12px;
  text-align: right;

  & > button {
    padding-left: 2px;
    font-size: 11px;
    font-weight: bold;
    background: #e1e1e1;
    border-radius: 4px;
  }
`;

const TimeLimit = styled.span`
  position: absolute;
  font-size: 14px;
  color: #f44336;
  top: 23%;
  right: 5%;
`;
