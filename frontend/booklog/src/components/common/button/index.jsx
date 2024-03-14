import styled from '@emotion/styled';

const CustomButton = ({ color, text, ...props }) => {
  return (
    <Button color={color} {...props}>
      {text}
    </Button>
  );
};

const Button = styled.button`
  border-radius: 10px;
  background-color: ${props =>
    props.disabled === 'disabled'
      ? 'var(--light-black-color)'
      : props.color
        ? props.color
        : 'var(--purple-color)'};
  width: 100%;
  padding: 15px;
  font-size: 15px;
  color: var(--white-color);
  cursor: ${props => (props.disabled === 'disabled' ? 'default' : 'pointer')};
`;

export default CustomButton;
