import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import CustomButton from 'components/common/button';
import { updateUser } from 'store/user';
import { updatePassword, updateName, updateIntro } from 'api/user';
import { passwordReg } from 'utils/regexData';

const Account = data => {
  const userInfo = Object.values(data)[0];
  const [user, setUser] = useState(userInfo);
  const [name, setName] = useState('');
  const [nameInput, setNameInput] = useState(false);
  const [password, setPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [newPasswordCheck, setNewPasswordCheck] = useState('');
  const [pwInput, setPWInput] = useState(false);
  const [intro, setIntro] = useState('');
  const [introInput, setIntroInput] = useState(false);

  useEffect(() => {
    setUser(userInfo);
  }, [data, user, setUser, userInfo]);

  const btnStyle = {
    width: 'fit-content',
    padding: '0 10px'
  };

  const handleNameChange = e => {
    e.preventDefault();

    if (e.target.innerText === '확인') {
      updateName({ name: name })
        .then(res => {
          updateUser(JSON.stringify(res.data));
          alert('닉네임이 변경되었습니다.');
        })
        .catch(e => {
          if (e.response.data.code === 409) {
            alert(e.response.data.message);
          } else alert('오류가 발생했습니다.');
        });
    }

    setName('');
    setNameInput(!nameInput);
  };

  const checkPWInput = () => {
    if (password === '' || newPassword === '' || newPasswordCheck === '') {
      alert('입력이 완료되지 않았습니다.');
      return false;
    }

    if (!passwordReg.test(newPassword)) {
      alert('비밀번호의 양식이 올바르지 않습니다.');
      return false;
    }

    if (newPassword !== newPasswordCheck) {
      alert('비밀번호가 일치하지 않습니다.');
      return false;
    }
    return true;
  };

  const handlePWChange = e => {
    e.preventDefault();

    if (e.target.innerText === '변경') {
      setPassword('');
      setNewPassword('');
      setNewPasswordCheck('');
      setPWInput(!pwInput);
      return;
    }

    if (pwInput) {
      if (checkPWInput()) {
        updatePassword({
          email: user.email,
          password: password,
          newPassword: newPassword
        })
          .then(() => {
            alert('비밀번호가 변경되었습니다.');
            setPassword('');
            setNewPassword('');
            setNewPasswordCheck('');
            setPWInput(!pwInput);
          })
          .catch(e => {
            if (e.response.data.code === 401) {
              alert('회원 인증에 실패했습니다.');
            } else alert('오류가 발생했습니다.');
          });
      }
    }
  };

  const handlePWChangeCancel = () => {
    setPassword('');
    setNewPassword('');
    setNewPasswordCheck('');
    setPWInput(!pwInput);
  };

  const handleIntro = e => {
    e.preventDefault();

    if (e.target.innerText === '확인') {
      updateIntro({
        introduction: intro
      })
        .then(res => {
          updateUser(JSON.stringify(res.data));
          alert('소개글이 변경되었습니다.');
        })
        .catch(() => {
          alert('오류가 발생했습니다.');
        });
    }
    setIntro('');
    setIntroInput(!introInput);
  };

  return (
    <Container>
      <UserAccount>
        <AuthBox>
          <ImgBox>
            <User>
              <img
                src={process.env.PUBLIC_URL + '/images/profile_basic.png'}
                alt="profile"
                width="100%"
              />
            </User>
          </ImgBox>
          <UserInfo>
            <InfoBox>
              <Label>이 메 일</Label>
              <InfoText>{user.email}</InfoText>
            </InfoBox>
            <InfoBox>
              <Label>닉 네 임</Label>
              <InfoText>
                {nameInput ? (
                  <InfoInput
                    value={name}
                    onChange={e => setName(e.target.value)}
                  />
                ) : (
                  user.name
                )}
              </InfoText>
              <CustomButton
                style={btnStyle}
                text={nameInput ? '확인' : '변경'}
                onClick={handleNameChange}
              />
            </InfoBox>
            <InfoBox>
              <Label>비밀번호</Label>
              {pwInput ? (
                <InfoText>
                  <InfoInput
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    placeholder="기존 비밀번호"
                  />
                </InfoText>
              ) : (
                <InfoText>***********</InfoText>
              )}
              <CustomButton
                style={btnStyle}
                text={pwInput ? '확인' : '변경'}
                onClick={handlePWChange}
              />
              {pwInput && (
                <CancelBtn>
                  <CustomButton
                    style={{
                      ...btnStyle,
                      background: 'var(--light-black-color)'
                    }}
                    text={'취소'}
                    onClick={handlePWChangeCancel}
                  />
                </CancelBtn>
              )}
            </InfoBox>
          </UserInfo>
        </AuthBox>
        {pwInput && (
          <>
            <PwBox>
              <InfoBox>
                <InfoText>
                  <InfoInput
                    value={newPassword}
                    onChange={e => setNewPassword(e.target.value)}
                    placeholder="새로운 비밀번호"
                  />
                </InfoText>
              </InfoBox>
              <InfoBox>
                <InfoText>
                  <InfoInput
                    value={newPasswordCheck}
                    onChange={e => setNewPasswordCheck(e.target.value)}
                    placeholder="새로운 비밀번호 확인"
                  />
                </InfoText>
              </InfoBox>
              <Msg>영문자, 숫자, 특수문자 조합 8~15자 이내</Msg>
            </PwBox>
          </>
        )}
        <IntroWrapper>
          <Label>소개글</Label>
          <IntroBox>
            <TextBox>
              {introInput ? (
                <InfoInput
                  value={intro}
                  onChange={e => setIntro(e.target.value)}
                />
              ) : userInfo.introduction === '' ? (
                '소개글을 작성해주세요.'
              ) : (
                userInfo.introduction
              )}
            </TextBox>
            <IntroBtn>
              <CustomButton
                style={btnStyle}
                text={introInput ? '확인' : '변경'}
                onClick={handleIntro}
              />
            </IntroBtn>
          </IntroBox>
        </IntroWrapper>
      </UserAccount>
    </Container>
  );
};

const Container = styled.div`
  width: 100%;
  padding: 10px;
  display: flex;
  height: fit-content;
  justify-content: center;
`;

const UserAccount = styled.div`
  width: 600px;
  padding: 20px;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 15px;

  @media (max-width: 480px) {
    width: 100%;
    flex-direction: column;
    gap: 10px;
  }
`;

const AuthBox = styled.div`
  display: flex;
  align-items: center;

  @media (max-width: 480px) {
    width: 100%;
    flex-direction: column;
  }
`;

const ImgBox = styled.div`
  width: 100px;
  display: flex;
  justify-content: center;
`;

const User = styled.div`
  position: relative;
  width: 80px;
  height: 80px;
  border-radius: 50%;
  overflow: hidden;
`;

const UserInfo = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-left: 20px;
  gap: 10px;
  width: 100%;

  @media (max-width: 480px) {
    margin: 0;
  }
`;

const InfoBox = styled.div`
  display: flex;
  position: relative;
  font-size: 1.2rem;

  @media (max-width: 480px) {
    flex-direction: column;

    & > button {
      position: absolute;
      top: 38px;
      right: 10px;
    }
  }
`;

const Label = styled.div`
  width: 80px;
  margin-right: 20px;
  text-align: center;
`;

const InfoText = styled.div`
  width: 220px;
  height: 32px;
  padding: 0 15px;
  margin-right: 15px;
  border-radius: 10px;
  background: var(--grey-color);

  @media (max-width: 480px) {
    width: 100%;
  }
`;

const InfoInput = styled.input`
  width: 100%;
  background: transparent;
  border: none;
  height: 100%;
  outline: none;
  display: flex;
`;

const PwBox = styled.div`
  display: flex;
  flex-direction: column;
  padding: 10px 0 0 135px;
  gap: 10px;

  @media (max-width: 480px) {
    width: 100%;
    padding: 0;

    & > button {
      position: absolute;
    }
  }
`;

const Msg = styled.p`
  width: fit-content;
  font-size: 11px;
  font-weight: bold;
  color: var(--purple-color);
  position: relative;
  padding-left: 15px;
`;

const CancelBtn = styled.div`
  position: absolute;
  right: 20px;
  top: 42px;

  & > button {
    height: 32px;
  }

  @media (max-width: 480px) {
    right: 10px;
    top: 150px;

    & > button {
      height: fit-content;
    }
  }
`;

const IntroBox = styled.div`
  width: 100%;
  & > button {
    height: 32px;
    text-align: right;
  }
`;

const IntroWrapper = styled.div`
  display: flex;
  width: 100%;
  padding: 20px 45px;
  margin: 20px 20px 10px 20px;
  font-size: 1.2rem;

  @media (max-width: 480px) {
    width: 100%;
    flex-direction: column;
    padding: 0;
    margin: 0;
  }
`;

const TextBox = styled.div`
  border-radius: 10px;
  background: var(--grey-color);
  padding: 20px;
  font-size: 1rem;
`;

const IntroBtn = styled.div`
  text-align: right;
  padding: 10px 4px 0 0;

  & > button {
    height: 32px;
  }

  @media (max-width: 480px) {
    padding: 0 8px 0 0;
    & > button {
      height: fit-content;
    }
  }
`;

export default Account;
