import styled from '@emotion/styled';
import { forwardRef } from 'react';

const CustomInput = forwardRef((props, ref) => {
  return <Input ref={ref} {...props} />;
});

const Input = styled.input`
  width: 100%;
  padding: 15px;
  margin-bottom: 15px;
  font-size: 16px;
  background-color: var(--white-color);
  outline: none;
  border: ${props =>
    props.disabled
      ? '1px solid var(--light-black-color)'
      : '1px solid var(--purple-color)'};
  border-radius: 10px;
`;

export default CustomInput;
