import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import { useGetMyInfoQuery } from 'hooks/queries/user';
import { updateUser } from 'store/user';
import { IoMenu } from 'react-icons/io5';
import TabMenu from 'components/user/profile/TabMenu';
import Account from 'components/user/profile/Account';

export const MyPage = () => {
  const [tab, setTab] = useState(0);
  const [active, setActive] = useState(false);
  const { user, isLoading } = useGetMyInfoQuery();

  useEffect(() => {
    if (!isLoading) {
      updateUser(JSON.stringify(user));
    }
  }, [isLoading, user]);

  const handleToggle = () => {
    setActive(!active);
  };

  return (
    <Container>
      <TabMenu
        tab={tab}
        setTab={setTab}
        active={active}
        handleToggle={handleToggle}
      />
      <Main active={active}>
        <TopBar>
          <Toggle onClick={handleToggle}>
            <IoMenu />
          </Toggle>
        </TopBar>
        <Content>
          {tab === 0 && <Account user={user} />}
          {tab === 1 && <div>contents</div>}
          {tab === 2 && <div>activity</div>}
        </Content>
      </Main>
    </Container>
  );
};

const Container = styled.div`
  min-height: 100vh;
  overflow-x: hidden;
`;

const Main = styled.div`
  position: absolute;
  width: ${props =>
    props.active ? 'calc(100% - 80px)' : 'calc(100% - 300px)'};
  left: ${props => (props.active ? '80px' : '300px')};
  min-height: 100vh;
  transition: 0.5s;
  background: var(--white-color);

  @media (max-width: 480px) {
    width: 100%;
    left: 0;
  }
`;

const TopBar = styled.div`
  position: fixed;
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 10px;
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
  color: var(--purple-color);
`;

const Content = styled.div`
  display: flex;
  justify-content: center;
  padding: 80px 20px 30px;
  width: 100%;
  height: 100vh;
  overflow-x: hidden;
  overflow-y: scroll;

  @media (max-width: 480px) {
    padding: 80px 10px 30px;
  }
`;
