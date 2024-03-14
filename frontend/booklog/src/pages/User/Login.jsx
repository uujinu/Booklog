import Header from 'components/common/Header';
import LoginForm from 'components/user/LoginForm';
import styled from '@emotion/styled';

const Login = () => {
  return (
    <>
      <Header display={false} />
      <Container>
        <LoginForm />
      </Container>
    </>
  );
};

const Container = styled.section`
  @media (min-width: 992px) {
    width: 600px;
  }
  width: 100%;
  height: 100vh;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 140px;
  @media (max-width: 992px) {
    padding: 0 20px;
  }
`;

export default Login;
