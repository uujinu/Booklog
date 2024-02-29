import styled from '@emotion/styled';
import NavBar from './NavBar';

const Header = () => {
  return (
    <Container>
      <NavBar />
    </Container>
  );
};

const Container = styled.header`
  position: relative;
  top: 0;
  left: 0;
  z-index: 100;
  width: 100%;
  color: var(--white-color);
  background-color: #6a24fe;
`;

export default Header;
