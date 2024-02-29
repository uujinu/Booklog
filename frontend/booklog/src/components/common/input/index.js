import styled from '@emotion/styled';
import { forwardRef } from 'react';

const Input = styled.input`
  @media (min-width: 992px) {
    width: 100%;
  }
  width: 250px;
  padding: 15px;
  margin-bottom: 15px;
  font-size: 16px;
  background-color: var(--white-color);
  outline: none;
  border: 1px solid var(--purple-color);
  border-radius: 10px;
`;

const CustomInput = forwardRef((props, ref) => {
  return <Input ref={ref} {...props} />;
});

export default CustomInput;
