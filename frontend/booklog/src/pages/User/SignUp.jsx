import Header from 'components/common/Header';
import styled from '@emotion/styled';
import { SignUpCard } from 'components/user/SignUpCard';

export const SignUp = () => {
  return (
    <>
      <Header display={false} />
      <Container>
        <SignUpCard />
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
  padding: 0 100px;

  @media (max-width: 992px) {
    padding: 0 20px;
  }
`;
