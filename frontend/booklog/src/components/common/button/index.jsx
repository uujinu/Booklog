import styled from '@emotion/styled';

const Button = styled.button`
  border-radius: 10px;
  background-color: ${props =>
    props.color ? props.color : 'var(--purple-color)'};
  width: 100%;
  padding: 15px;
  font-size: 15px;
  color: var(--white-color);
  &:hover {
    background-color: 'pink';
  }
`;

const CustomButton = ({ color, text }) => {
  return <Button color={color}>{text}</Button>;
};

export default CustomButton;
