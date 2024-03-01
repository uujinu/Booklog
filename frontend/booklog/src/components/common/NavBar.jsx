import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { HiOutlineMenuAlt3 } from 'react-icons/hi';
import styled from '@emotion/styled';

const NavBar = ({ display = true, ...props }) => {
  const [toggleMenu, setToggleMenu] = useState(false);
  const handleNavBar = () => {
    setToggleMenu(!toggleMenu);
  };

  return (
    <Container>
      <NavBarContent>
        <Logo>
          <Link to="/">
            <img
              src={process.env.PUBLIC_URL + '/images/logo.png'}
              alt="site logo"
              width="25px"
              height="100%"
            />
            <Title>booklog</Title>
          </Link>
        </Logo>
        <TogglerBtn type="button" onClick={handleNavBar}>
          <HiOutlineMenuAlt3
            size={35}
            style={{
              color: `${toggleMenu ? '#f7a8a8' : '#d9176d'}`
            }}
          />
        </TogglerBtn>
      </NavBarContent>
      {display && (
        <NavBarCollapse toggleMenu={toggleMenu}>
          <NavBarUl>
            <NavItem>
              <Link to="/">home</Link>
            </NavItem>
            <NavItem>
              <Link to="login">login</Link>
            </NavItem>
          </NavBarUl>
        </NavBarCollapse>
      )}
    </Container>
  );
};

const Container = styled.nav`
  display: flex;
  padding: 1rem 0;
  box-shadow:
    rgba(0, 0, 0, 0.1) 0px 10px 15px -3px,
    rgba(0, 0, 0, 0.05) 0px 4px 6px -2px;
`;

const NavBarContent = styled.div`
  display: flex;
  width: 100%;
  padding: 0 10px;
`;

const Logo = styled.div`
  width: 100%;
  & > a {
    display: flex;
    align-items: center;
    width: fit-content;
  }
`;

const Title = styled.span`
  text-transform: uppercase;
  padding-left: 7px;
  font-weight: 700;
  font-size: 1.5rem;
  letter-spacing: 1px;
  color: var(--white-color);
`;

const TogglerBtn = styled.button`
  transition: var(--transition);
  z-index: 11;
  @media (min-width: 992px) {
    display: none;
  }
`;

const NavBarCollapse = styled.div`
  transform: ${props =>
    props.toggleMenu ? 'translateX(0)' : 'translateX(100%)'};
  position: fixed;
  right: 0;
  top: 0;
  z-index: 10;
  background-color: var(--white-color);
  height: 100%;
  width: 280px;
  display: flex;
  padding: 10rem 3rem 0 3rem;
  transition: var(--transition);

  @media (min-width: 992px) {
    position: relative;
    height: auto;
    padding: 0;
    width: 100%;
    background-color: transparent;
    transform: translateX(0);
    justify-content: flex-end;
    padding-right: 10px;
  }
`;

const NavBarUl = styled.ul`
  display: flex;
  flex-direction: column;
  text-transform: uppercase;

  @media (min-width: 992px) {
    flex-direction: row;
  }
`;

const NavItem = styled.li`
  margin-bottom: 0;
  margin-left: 1.2rem;

  & > a {
    color: var(--white-color);
    font-size: 1.7rem;
    font-weight: bold;
    opacity: 0.8;

    @media (min-width: 992px) {
      font-size: 1.2rem;
    }
  }

  @media (min-width: 992px) {
    margin-left: 2rem;
  }
`;

export default NavBar;
