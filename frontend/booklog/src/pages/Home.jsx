import React from 'react';
import styled from '@emotion/styled';
import Header from 'components/common/Header';
import SearchForm from 'components/common/SearchForm';

const Home = () => {
  return (
    <>
      <Header />
      <Container>
        <ContentWrapper>
          <HeaderTitle>
            Booklog
            <br />
            기록하는 독서
          </HeaderTitle>
          <HeaderText>Ultimate Reading</HeaderText>
          <SearchInput>
            <InputTit>독서기록을 검색해보세요</InputTit>
            <SearchForm />
          </SearchInput>
        </ContentWrapper>
      </Container>
    </>
  );
};

const Container = styled.div`
  @media (max-width: 992px) {
    padding: 1rem;
  }
  margin-top: 60px;
  min-height: 75vh;
  flex-direction: column;
  color: var(--white-color);
  display: flex;
  align-items: center;
  text-align: center;
  padding: 3rem;
`;

const ContentWrapper = styled.div`
  @media (max-width: 992px) {
    width: 100%;
  }
  margin-top: 20px;
  color: var(--purple-color);
  width: 770px;
`;

const HeaderTitle = styled.h2`
  font-size: 2rem;
  font-family: 'GmarketSansMedium';
  text-align: right;
`;

const HeaderText = styled.p`
  margin-top: 1rem;
  margin-bottom: 2rem;
  opacity: 0.8;
  max-width: 770px;
  font-family: 'Pacifico';
`;

const SearchInput = styled.div`
  @media (max-width: 992px) {
    padding: 1rem;
  }

  background:
    linear-gradient(#fec92473, #6a24fe7d),
    url(/images/01.png) center/cover no-repeat;
  padding: 50px;
  display: flex;
  justify-content: center;
  border-radius: 20px;
  flex-direction: column;
  align-items: center;
`;

const InputTit = styled.span`
  font-size: 1rem;
  color: var(--white-color);
`;

export default Home;
