import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useMediaQuery } from '@mui/material';
import { logoutUser } from 'store/user';
import { RiAccountCircleLine } from 'react-icons/ri';
import { MdOutlineMenuBook } from 'react-icons/md';
import { BiCool } from 'react-icons/bi';
import { FiLogOut } from 'react-icons/fi';
import { IoMenu } from 'react-icons/io5';

const TabMenu = ({ tab, setTab, active, handleToggle }) => {
  const [toggle, setToggle] = useState(false);
  const isMobile = useMediaQuery('(max-width:480px)');

  useEffect(() => {
    if (isMobile) {
      setToggle(true);
    } else {
      setToggle(false);
    }
  }, [isMobile]);

  return (
    <>
      <SideNavigation className="navigation" active={active}>
        <Logo>
          <Link to="/" style={{ paddingLeft: '15px' }}>
            <img
              src={process.env.PUBLIC_URL + '/images/logo.png'}
              alt="site logo"
              width="25px"
              height="100%"
            />
            <Title>booklog</Title>
          </Link>
          {toggle && (
            <Toggle onClick={handleToggle}>
              <IoMenu />
            </Toggle>
          )}
        </Logo>
        <NavUl>
          <NavLi className={tab === 0 ? 'tab' : ''} tab={tab === 0}>
            <Link
              onClick={() => {
                setTab(0);
                if (isMobile) handleToggle();
              }}
            >
              <Icon>
                <RiAccountCircleLine />
              </Icon>
              <NavMenu>계정</NavMenu>
            </Link>
          </NavLi>
          <NavLi className={tab === 1 ? 'tab' : ''} tab={tab === 1}>
            <Link
              onClick={() => {
                setTab(1);
                if (isMobile) handleToggle();
              }}
            >
              <Icon>
                <MdOutlineMenuBook />
              </Icon>
              <NavMenu>콘텐츠</NavMenu>
            </Link>
          </NavLi>
          <NavLi className={tab === 2 ? 'tab' : ''} tab={tab === 2}>
            <Link
              onClick={() => {
                setTab(2);
                if (isMobile) handleToggle();
              }}
            >
              <Icon>
                <BiCool />
              </Icon>
              <NavMenu>활동</NavMenu>
            </Link>
          </NavLi>
          <NavLi>
            <Link to="/" onClick={() => logoutUser()}>
              <Icon>
                <FiLogOut />
              </Icon>
              <NavMenu>로그아웃</NavMenu>
            </Link>
          </NavLi>
        </NavUl>
      </SideNavigation>
    </>
  );
};

const Logo = styled.div`
  width: 100%;
  height: 60px;
  margin-bottom: 50px;
  display: flex;
  align-items: center;
  justify-content: space-between;

  & > a {
    display: flex;
    align-items: center;
    width: fit-content;
  }
`;

const Title = styled.span`
  text-transform: uppercase;
  padding-left: 25px;
  font-weight: 700;
  font-size: 1.5rem;
  letter-spacing: 1px;
  color: var(--white-color);
`;

const Toggle = styled.div`
  position: relative;
  width: 60px;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2rem;
  cursor: pointer;
  color: var(--white-color);
`;

const SideNavigation = styled.div`
  position: fixed;
  height: 100vh;
  width: ${props => (props.active ? '80px' : '300px')};
  padding: 0 0 1rem 1rem;
  background-color: var(--purple-color);
  transition: 0.5s;

  @media (max-width: 480px) {
    width: 100%;
    left: ${props => (props.active ? '0' : '-100%')};
    z-index: 1000;
  }
`;

const NavUl = styled.ul``;

const NavLi = styled.li`
  position: relative;
  width: 100%;
  font-weight: bold;
  margin-bottom: 20px;
  border-top-left-radius: 30px;
  border-bottom-left-radius: 30px;
  background-color: ${props =>
    props.tab ? 'var(--white-color)' : 'var(--purple-color)'};

  &:hover {
    background-color: var(--white-color);
  }

  &.tab > a::before {
    content: '';
    position: absolute;
    right: 0;
    top: -50px;
    width: 50px;
    height: 50px;
    background-color: transparent;
    border-radius: 50%;
    box-shadow: 35px 35px 0 10px var(--white-color);
    pointer-events: none;
  }

  &.tab > a::after {
    content: '';
    position: absolute;
    right: 0;
    bottom: -50px;
    width: 50px;
    height: 50px;
    background-color: transparent;
    border-radius: 50%;
    box-shadow: 35px -35px 0 10px var(--white-color);
    pointer-events: none;
  }

  &:hover > a::before {
    content: '';
    position: absolute;
    right: 0;
    top: -50px;
    width: 50px;
    height: 50px;
    background-color: transparent;
    border-radius: 50%;
    box-shadow: 35px 35px 0 10px var(--white-color);
    pointer-events: none;
  }

  &:hover > a::after {
    content: '';
    position: absolute;
    right: 0;
    bottom: -50px;
    width: 50px;
    height: 50px;
    background-color: transparent;
    border-radius: 50%;
    box-shadow: 35px -35px 0 10px var(--white-color);
    pointer-events: none;
  }

  & > a {
    position: relative;
    display: block;
    width: 100%;
    display: flex;
    text-decoration: none;
    color: ${props =>
      props.tab ? 'var(--purple-color)' : 'var(--white-color)'};
    font-size: 18px;
    align-items: center;
  }

  &:hover > a {
    color: var(--purple-color);
  }
`;

const Icon = styled.span`
  position: relative;
  display: block;
  min-width: 60px;
  height: 60px;
  line-height: 65px;
  text-align: center;
  font-size: 1.75rem;
`;

const NavMenu = styled.span`
  position: relative;
  display: block;
  padding: 0 10px;
  height: 60px;
  line-height: 60px;
  text-align: start;
  white-space: nowrap;
`;

export default TabMenu;
