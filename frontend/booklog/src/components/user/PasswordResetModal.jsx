import { useEffect, useState } from 'react';
import styled from '@emotion/styled';
import { getPWResetEmail, checkCode } from 'api/user';
import CustomInput from 'components/common/input';
import CustomButton from 'components/common/button';
import { emailReg } from 'utils/regexData';
import { IoMdClose } from 'react-icons/io';
import { GoIssueClosed } from 'react-icons/go';

export const PasswordResetModel = props => {
  const [email, setEmail] = useState('');
  const [emailValidated, setEmailValidated] = useState(false);
  const [code, setCode] = useState('');
  const [codeBtnDisabled, setCodeBtnDisabled] = useState(true);
  const [emailMsg, setEmailMsg] = useState('');
  const [codeMsg, setCodeMsg] = useState('');
  const [completed, setCompleted] = useState(false);

  useEffect(() => {
    if (emailReg.test(email)) {
      setEmailValidated(true);
    } else setEmailValidated(false);
  }, [email]);

  useEffect(() => {
    if (emailValidated && code !== '') {
      setCodeBtnDisabled(false);
    } else setCodeBtnDisabled(true);
  }, [emailValidated, code]);

  const handleEmail = () => {
    getPWResetEmail(email)
      .then(res => {
        setEmailMsg(res.data.data);
      })
      .catch(e => {
        setEmailMsg(e.response.data.message);
      });
  };

  const handleCode = () => {
    checkCode({
      email: email,
      code: code
    })
      .then(() => {
        setCompleted(true);
      })
      .catch(e => {
        setCodeMsg(e.response.data.message);
      });
  };

  const init = () => {
    setEmail('');
    setCode('');
    setCodeBtnDisabled(true);
    setCodeMsg('');
    setEmailMsg('');
    setEmailValidated(false);
    setCompleted(false);
  };

  return (
    <ModalWrapper>
      <Modal>
        {!completed ? (
          <>
            <InfoText>임시 비밀번호 발급</InfoText>
            <InputWrapper>
              <CustomInput
                id="email"
                type="email"
                placeholder="Email"
                value={email}
                onChange={e => setEmail(e.target.value)}
              />
              <CustomButton
                disabled={!emailValidated ? 'disabled' : ''}
                color={
                  emailValidated
                    ? 'var(--purple-color)'
                    : 'var(--light-black-color)'
                }
                text={'인증 코드 발송'}
                onClick={handleEmail}
              />
              {emailMsg && <Msg>{emailMsg}</Msg>}
            </InputWrapper>
            <InputWrapper>
              <CustomInput
                id="code"
                placeholder="Code"
                value={code}
                onChange={e => setCode(e.target.value)}
              />
              <CustomButton
                disabled={codeBtnDisabled ? 'disabled' : ''}
                text={'확인'}
                onClick={handleCode}
              />
              {codeMsg && <Msg>{codeMsg}</Msg>}
            </InputWrapper>
          </>
        ) : (
          <InfoText>
            <Last>
              <GoIssueClosed />
            </Last>
            임시 비밀번호가
            <br /> 발급되었습니다.
          </InfoText>
        )}
        <CloseBtn
          onClick={() => {
            init();
            props.setModalOpen(false);
          }}
        >
          <IoMdClose />
        </CloseBtn>
      </Modal>
    </ModalWrapper>
  );
};

const ModalWrapper = styled.div`
  background-color: rgb(0 0 0 / 60%);
  width: 100%;
  height: 100vh;
  z-index: 10;
  position: fixed;
  top: 0;
  left: 0;
`;

const Modal = styled.div`
  width: 360px;
  height: 400px;
  z-index: 100;
  display: flex;
  flex-direction: column;
  gap: 20px;
  position: absolute;
  top: 50%;
  left: 50%;
  padding: 50px;
  background: var(--white-color);
  transform: translate(-50%, -50%);
  border-radius: 10px;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.25);
`;

const InfoText = styled.h3`
  font-size: 20px;
  text-align: center;
  color: var(--purple-color);
`;

const CloseBtn = styled.div`
  position: absolute;
  top: 0;
  right: 10px;
  cursor: pointer;
`;

const InputWrapper = styled.div`
  height: 100%;
  & > input {
    height: 40px;
    margin-bottom: 0;
  }

  & > button {
    height: 40px;
    padding: 0;
  }
`;

const Msg = styled.p`
  font-size: 12px;
  font-weight: bold;
  color: red;
  width: 100%;
  padding-left: 15px;
  position: relative;
`;

const Last = styled.div`
  padding-top: 30px;
  font-size: 80px;
  text-align: center;
`;
